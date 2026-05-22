package br.com.consultorio.model.bo;

import br.com.consultorio.model.dao.ConsultaDAO;
import br.com.consultorio.model.dao.MedicoDAO;
import br.com.consultorio.model.dao.PacienteDAO;
import br.com.consultorio.model.dto.AgendamentoConsultaDTO;
import br.com.consultorio.model.entity.Consulta;
import br.com.consultorio.model.entity.Medico;
import br.com.consultorio.model.entity.Paciente;
import br.com.consultorio.model.util.AuditoriaBO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ConsultaBO {

    @Inject ConsultaDAO consultaDAO;
    @Inject PacienteDAO pacienteDAO;
    @Inject MedicoDAO medicoDAO;
    @Inject AuditoriaBO auditoria;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final DateTimeFormatter FMT_OUT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public AgendamentoConsultaDTO agendar(AgendamentoConsultaDTO dto, Long usuarioId, String usuarioEmail) {
        if (dto.getPacienteId() == null) throw new IllegalArgumentException("Paciente é obrigatório");
        if (dto.getMedicoId() == null) throw new IllegalArgumentException("Médico é obrigatório");
        if (dto.getDataHora() == null || dto.getDataHora().isBlank()) throw new IllegalArgumentException("Data/hora é obrigatória");

        LocalDateTime dataHora = LocalDateTime.parse(dto.getDataHora(), FMT);
        if (dataHora.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("A data/hora deve ser futura");

        Paciente paciente = pacienteDAO.buscarPorId(dto.getPacienteId())
            .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
        Medico medico = medicoDAO.buscarPorId(dto.getMedicoId())
            .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));

        // RN: Verifica conflito de horário
        if (consultaDAO.existeConflito(medico.getId(), dataHora, dto.getId()))
            throw new IllegalArgumentException("Médico já possui consulta neste horário");

        Consulta c;
        String acao;
        if (dto.getId() != null) {
            c = consultaDAO.buscarPorId(dto.getId()).orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
            acao = "Reagendou consulta";
        } else {
            c = new Consulta();
            acao = "Agendou consulta";
        }

        c.setPaciente(paciente);
        c.setMedico(medico);
        c.setDataHora(dataHora);
        c.setMotivo(dto.getMotivo());
        c.setObservacoes(dto.getObservacoes());
        c.setStatus(Consulta.Status.AGENDADA);
        c = consultaDAO.salvar(c);

        auditoria.registrar(acao + " para " + paciente.getNome(), usuarioId, usuarioEmail,
            "Consulta ID=" + c.getId() + " Médico=" + medico.getNome());
        return toDTO(c, false);
    }

    public AgendamentoConsultaDTO atualizarStatus(Long id, String novoStatus, Long usuarioId, String usuarioEmail) {
        Consulta c = consultaDAO.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
        Consulta.Status status;
        try { status = Consulta.Status.valueOf(novoStatus); }
        catch (Exception e) { throw new IllegalArgumentException("Status inválido: " + novoStatus); }
        c.setStatus(status);
        c = consultaDAO.salvar(c);
        auditoria.registrar("Atualizou status da consulta para " + novoStatus, usuarioId, usuarioEmail, "Consulta ID=" + id);
        return toDTO(c, false);
    }

    public List<AgendamentoConsultaDTO> listarTodas() {
        return consultaDAO.listarTodas().stream().map(c -> toDTO(c, false)).collect(Collectors.toList());
    }

    public List<AgendamentoConsultaDTO> listarPorPaciente(Long pacienteId) {
        return consultaDAO.listarPorPaciente(pacienteId).stream().map(c -> toDTO(c, false)).collect(Collectors.toList());
    }

    public List<AgendamentoConsultaDTO> listarPorMedico(Long medicoId) {
        return consultaDAO.listarPorMedico(medicoId).stream().map(c -> toDTO(c, false)).collect(Collectors.toList());
    }

    public AgendamentoConsultaDTO buscarPorId(Long id) {
        return consultaDAO.buscarPorId(id).map(c -> toDTO(c, false))
            .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
    }

    public AgendamentoConsultaDTO toDTO(Consulta c, boolean temFicha) {
        AgendamentoConsultaDTO dto = new AgendamentoConsultaDTO();
        dto.setId(c.getId());
        dto.setPacienteId(c.getPaciente().getId());
        dto.setPacienteNome(c.getPaciente().getNome());
        dto.setMedicoId(c.getMedico().getId());
        dto.setMedicoNome(c.getMedico().getNome());
        dto.setMedicoEspecialidade(c.getMedico().getEspecialidade());
        dto.setDataHora(c.getDataHora().format(FMT_OUT));
        dto.setStatus(c.getStatus().name());
        dto.setMotivo(c.getMotivo());
        dto.setObservacoes(c.getObservacoes());
        dto.setTemFicha(temFicha);
        return dto;
    }
}
