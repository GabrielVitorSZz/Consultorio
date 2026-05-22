package br.com.consultorio.model.bo;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.consultorio.model.dao.PerfilUsuarioDAO;
import br.com.consultorio.model.dao.UsuarioDAO;
import br.com.consultorio.model.dto.UsuarioDTO;
import br.com.consultorio.model.entity.PerfilUsuario;
import br.com.consultorio.model.entity.Usuario;
import br.com.consultorio.model.util.AuditoriaBO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioBO {

    @Inject UsuarioDAO usuarioDAO;
    @Inject PerfilUsuarioDAO perfilDAO;
    @Inject AuditoriaBO auditoria;

    public UsuarioDTO salvar(UsuarioDTO dto, String emailExecutor) {
        if (dto.getNome() == null || dto.getNome().isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
        if (dto.getEmail() == null || dto.getEmail().isBlank())
            throw new IllegalArgumentException("E-mail é obrigatório");
        if (dto.getPerfilId() == null)
            throw new IllegalArgumentException("Perfil é obrigatório");
        if (dto.getId() == null && (dto.getSenha() == null || dto.getSenha().isBlank()))
            throw new IllegalArgumentException("Senha é obrigatória para novos usuários");
        if (usuarioDAO.existeEmail(dto.getEmail(), dto.getId()))
            throw new IllegalArgumentException("E-mail já cadastrado");

        PerfilUsuario perfil = perfilDAO.buscarPorId(dto.getPerfilId())
            .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado"));

        Usuario u;
        String acao;
        if (dto.getId() != null) {
            u = usuarioDAO.buscarPorId(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            acao = "Editou usuário";
        } else {
            u = new Usuario();
            acao = "Criou usuário";
        }

        u.setNome(dto.getNome());
        u.setEmail(dto.getEmail());
        u.setPerfil(perfil);
        u.setAtivo(dto.isAtivo());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            String hash = BCrypt.withDefaults().hashToString(12, dto.getSenha().toCharArray());
            u.setSenha(hash);
        }

        u = usuarioDAO.salvar(u);
        auditoria.registrar(acao + ": " + dto.getEmail(), null, emailExecutor, "ID=" + u.getId());
        return toDTO(u);
    }

    public void inativar(Long id, String emailExecutor) {
        Usuario u = usuarioDAO.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        usuarioDAO.inativar(id);
        auditoria.registrar("Inativou usuário: " + u.getEmail(), null, emailExecutor, "ID=" + id);
    }

    public List<UsuarioDTO> listarTodos() {
        return usuarioDAO.listarTodos().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UsuarioDTO buscarPorId(Long id) {
        return usuarioDAO.buscarPorId(id).map(this::toDTO)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    public UsuarioDTO toDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setNome(u.getNome());
        dto.setEmail(u.getEmail());
        dto.setAtivo(u.isAtivo());
        if (u.getPerfil() != null) {
            dto.setPerfilId(u.getPerfil().getId());
            dto.setPerfilNome(u.getPerfil().getNome());
        }
        return dto;
    }
}
