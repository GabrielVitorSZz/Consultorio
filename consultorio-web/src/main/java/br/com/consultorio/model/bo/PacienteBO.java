package br.com.consultorio.model.bo;

import br.com.consultorio.model.dao.PacienteDAO;
import br.com.consultorio.model.dto.PacienteDTO;
import br.com.consultorio.model.entity.Paciente;
import br.com.consultorio.model.util.AuditoriaBO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PacienteBO {

    @Inject PacienteDAO pacienteDAO;
    @Inject AuditoriaBO auditoria;

    public PacienteDTO salvar(PacienteDTO dto, Long usuarioId, String usuarioEmail) {
        if (dto.getNome() == null || dto.getNome().isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        if (dto.getCpf() == null || dto.getCpf().isBlank()) throw new IllegalArgumentException("CPF é obrigatório");
        if (pacienteDAO.buscarPorCpf(dto.getCpf(), dto.getId()).isPresent())
            throw new IllegalArgumentException("CPF já cadastrado");

        Paciente p;
        String acao;
        if (dto.getId() != null) {
            p = pacienteDAO.buscarPorId(dto.getId()).orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
            acao = "Editou paciente";
        } else {
            p = new Paciente();
            acao = "Cadastrou paciente";
        }

        p.setNome(dto.getNome());
        p.setCpf(dto.getCpf());
        p.setTelefone(dto.getTelefone());
        p.setEmail(dto.getEmail());
        p.setSexo(dto.getSexo());
        p.setObservacoes(dto.getObservacoes());
        p.setAtivo(true);
        if (dto.getDataNascimento() != null && !dto.getDataNascimento().isBlank())
            p.setDataNascimento(LocalDate.parse(dto.getDataNascimento()));

        p = pacienteDAO.salvar(p);
        auditoria.registrar(acao + ": " + dto.getNome(), usuarioId, usuarioEmail, "ID=" + p.getId());
        return toDTO(p);
    }

    public void inativar(Long id, Long usuarioId, String usuarioEmail) {
        Paciente p = pacienteDAO.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
        pacienteDAO.inativar(id);
        auditoria.registrar("Inativou paciente: " + p.getNome(), usuarioId, usuarioEmail, "ID=" + id);
    }

    public List<PacienteDTO> listarAtivos() {
        return pacienteDAO.listarAtivos().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<PacienteDTO> buscarPorNome(String nome) {
        return pacienteDAO.buscarPorNome(nome).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PacienteDTO buscarPorId(Long id) {
        return pacienteDAO.buscarPorId(id).map(this::toDTO).orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
    }

    public PacienteDTO toDTO(Paciente p) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(p.getId());
        dto.setNome(p.getNome());
        dto.setCpf(p.getCpf());
        dto.setTelefone(p.getTelefone());
        dto.setEmail(p.getEmail());
        dto.setSexo(p.getSexo());
        dto.setObservacoes(p.getObservacoes());
        dto.setAtivo(p.isAtivo());
        if (p.getDataNascimento() != null) dto.setDataNascimento(p.getDataNascimento().toString());
        return dto;
    }
}
