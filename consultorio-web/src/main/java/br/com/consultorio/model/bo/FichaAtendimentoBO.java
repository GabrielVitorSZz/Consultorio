package br.com.consultorio.model.bo;

import br.com.consultorio.model.dao.ConsultaDAO;
import br.com.consultorio.model.dao.FichaAtendimentoDAO;
import br.com.consultorio.model.dto.FichaHistoricoDTO;
import br.com.consultorio.model.entity.Consulta;
import br.com.consultorio.model.entity.FichaAtendimento;
import br.com.consultorio.model.util.AuditoriaBO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FichaAtendimentoBO {

    @Inject FichaAtendimentoDAO fichaDAO;
    @Inject ConsultaDAO consultaDAO;
    @Inject AuditoriaBO auditoria;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * RN: Apenas médicos podem registrar/editar fichas de atendimento.
     * A consulta deve existir e pertencer ao médico autenticado.
     */
    public FichaHistoricoDTO salvar(FichaHistoricoDTO dto, Long medicoUsuarioId, String usuarioEmail) {
        if (dto.getConsultaId() == null) throw new IllegalArgumentException("Consulta é obrigatória");

        Consulta consulta = consultaDAO.buscarPorId(dto.getConsultaId())
            .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        // RN: Médico só pode preencher ficha de suas próprias consultas
        if (medicoUsuarioId != null && !consulta.getMedico().getUsuario().getId().equals(medicoUsuarioId))
            throw new SecurityException("Médico não autorizado para esta consulta");

        FichaAtendimento ficha;
        String acao;
        if (dto.getId() != null) {
            ficha = fichaDAO.buscarPorId(dto.getId()).orElseThrow(() -> new IllegalArgumentException("Ficha não encontrada"));
            acao = "Editou ficha de atendimento";
        } else {
            // Verifica se já existe ficha para esta consulta
            if (fichaDAO.buscarPorConsulta(dto.getConsultaId()).isPresent())
                throw new IllegalArgumentException("Já existe ficha para esta consulta");
            ficha = new FichaAtendimento();
            acao = "Registrou ficha de atendimento";
        }

        ficha.setConsulta(consulta);
        ficha.setDiagnostico(dto.getDiagnostico());
        ficha.setPrescricao(dto.getPrescricao());
        ficha.setExamesSolicitados(dto.getExamesSolicitados());
        ficha.setObservacoesMedico(dto.getObservacoesMedico());
        ficha.setDataRegistro(LocalDateTime.now());
        ficha = fichaDAO.salvar(ficha);

        // Atualiza status da consulta para REALIZADA
        consulta.setStatus(Consulta.Status.REALIZADA);
        consultaDAO.salvar(consulta);

        auditoria.registrar(acao + " - Paciente: " + consulta.getPaciente().getNome(),
            null, usuarioEmail, "Ficha ID=" + ficha.getId() + " Consulta ID=" + dto.getConsultaId());
        return toDTO(ficha);
    }

    public List<FichaHistoricoDTO> listarPorPaciente(Long pacienteId) {
        return fichaDAO.listarPorPaciente(pacienteId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public FichaHistoricoDTO buscarPorConsulta(Long consultaId) {
        return fichaDAO.buscarPorConsulta(consultaId).map(this::toDTO)
            .orElseThrow(() -> new IllegalArgumentException("Ficha não encontrada para esta consulta"));
    }

    public FichaHistoricoDTO toDTO(FichaAtendimento f) {
        FichaHistoricoDTO dto = new FichaHistoricoDTO();
        dto.setId(f.getId());
        dto.setConsultaId(f.getConsulta().getId());
        dto.setPacienteNome(f.getConsulta().getPaciente().getNome());
        dto.setMedicoNome(f.getConsulta().getMedico().getNome());
        dto.setDataConsulta(f.getConsulta().getDataHora().format(FMT));
        dto.setDiagnostico(f.getDiagnostico());
        dto.setPrescricao(f.getPrescricao());
        dto.setExamesSolicitados(f.getExamesSolicitados());
        dto.setObservacoesMedico(f.getObservacoesMedico());
        if (f.getDataRegistro() != null) dto.setDataRegistro(f.getDataRegistro().format(FMT));
        return dto;
    }
}
