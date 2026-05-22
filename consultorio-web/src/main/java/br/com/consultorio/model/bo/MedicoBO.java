package br.com.consultorio.model.bo;

import br.com.consultorio.model.dao.MedicoDAO;
import br.com.consultorio.model.dao.UsuarioDAO;
import br.com.consultorio.model.dto.MedicoDTO;
import br.com.consultorio.model.entity.Medico;
import br.com.consultorio.model.entity.Usuario;
import br.com.consultorio.model.util.AuditoriaBO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MedicoBO {

    @Inject MedicoDAO medicoDAO;
    @Inject UsuarioDAO usuarioDAO;
    @Inject AuditoriaBO auditoria;

    public MedicoDTO salvar(MedicoDTO dto, String usuarioEmail) {
        if (dto.getCrm() == null || dto.getCrm().isBlank()) throw new IllegalArgumentException("CRM é obrigatório");
        if (dto.getEspecialidade() == null || dto.getEspecialidade().isBlank()) throw new IllegalArgumentException("Especialidade é obrigatória");
        if (dto.getUsuarioId() == null) throw new IllegalArgumentException("Usuário vinculado é obrigatório");
        if (medicoDAO.existeCrm(dto.getCrm(), dto.getId())) throw new IllegalArgumentException("CRM já cadastrado");

        Usuario usuario = usuarioDAO.buscarPorId(dto.getUsuarioId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Medico m;
        String acao;
        if (dto.getId() != null) {
            m = medicoDAO.buscarPorId(dto.getId()).orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));
            acao = "Editou médico";
        } else {
            m = new Medico();
            acao = "Cadastrou médico";
        }
        m.setCrm(dto.getCrm());
        m.setEspecialidade(dto.getEspecialidade());
        m.setTelefone(dto.getTelefone());
        m.setUsuario(usuario);
        m = medicoDAO.salvar(m);
        auditoria.registrar(acao + ": " + usuario.getNome(), null, usuarioEmail, "ID=" + m.getId() + " CRM=" + dto.getCrm());
        return toDTO(m);
    }

    public List<MedicoDTO> listarTodos() {
        return medicoDAO.listarTodos().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public MedicoDTO toDTO(Medico m) {
        MedicoDTO dto = new MedicoDTO();
        dto.setId(m.getId());
        dto.setNome(m.getNome());
        dto.setCrm(m.getCrm());
        dto.setEspecialidade(m.getEspecialidade());
        dto.setTelefone(m.getTelefone());
        if (m.getUsuario() != null) dto.setUsuarioId(m.getUsuario().getId());
        return dto;
    }
}
