package br.com.consultorio.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.consultorio.model.dao.UsuarioDAO;
import br.com.consultorio.model.dto.LoginDTO;
import br.com.consultorio.model.dto.TokenDTO;
import br.com.consultorio.model.entity.Usuario;
import br.com.consultorio.model.util.AuditoriaBO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {

    @Inject UsuarioDAO usuarioDAO;
    @Inject JwtUtil jwtUtil;
    @Inject AuditoriaBO auditoria;

    public TokenDTO autenticar(LoginDTO dto) {
        if (dto.getEmail() == null || dto.getSenha() == null)
            throw new IllegalArgumentException("E-mail e senha são obrigatórios");

        Usuario usuario = usuarioDAO.buscarPorEmail(dto.getEmail())
            .orElseThrow(() -> new SecurityException("Credenciais inválidas"));

        if (!usuario.isAtivo())
            throw new SecurityException("Usuário inativo");

        BCrypt.Result result = BCrypt.verifyer().verify(
            dto.getSenha().toCharArray(), usuario.getSenha()
        );
        if (!result.verified)
            throw new SecurityException("Credenciais inválidas");

        String token = jwtUtil.gerarToken(usuario);
        auditoria.registrar("Login realizado", usuario.getId(), usuario.getEmail(), "Acesso ao sistema");

        return new TokenDTO(token, usuario.getNome(), usuario.getEmail(),
            usuario.getPerfil().getNome(), usuario.getId());
    }
}
