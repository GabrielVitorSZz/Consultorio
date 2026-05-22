package br.com.consultorio.security;

import br.com.consultorio.model.entity.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtUtil {

    public String gerarToken(Usuario usuario) {
        return Jwt.issuer("consultorio-app")
            .subject(usuario.getEmail())
            .groups(Set.of(usuario.getPerfil().getNome()))
            .claim("usuarioId", usuario.getId())
            .claim("nome", usuario.getNome())
            .claim("perfil", usuario.getPerfil().getNome())
            .expiresIn(Duration.ofHours(8))
            .sign();
    }
}
