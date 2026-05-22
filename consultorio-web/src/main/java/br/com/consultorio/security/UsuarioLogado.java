package br.com.consultorio.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * Bean de escopo de request para acesso aos dados do usuário autenticado via JWT.
 */
@RequestScoped
public class UsuarioLogado {

    @Inject
    JsonWebToken jwt;

    public String getEmail() {
        return jwt != null ? jwt.getSubject() : null;
    }

    public Long getUsuarioId() {
        if (jwt == null) return null;
        Object id = jwt.getClaim("usuarioId");
        if (id instanceof Number) return ((Number) id).longValue();
        if (id instanceof String) return Long.parseLong((String) id);
        return null;
    }

    public String getPerfil() {
        return jwt != null ? jwt.getClaim("perfil") : null;
    }

    public boolean isAdmin() { return "ADMIN".equals(getPerfil()); }
    public boolean isMedico() { return "MEDICO".equals(getPerfil()); }
    public boolean isRecepcionista() { return "RECEPCIONISTA".equals(getPerfil()); }
}
