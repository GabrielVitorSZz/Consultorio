package br.com.consultorio.model.dao;
import br.com.consultorio.model.entity.PerfilUsuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PerfilUsuarioDAO {
    @Inject EntityManager em;

    @Transactional
    public PerfilUsuario salvar(PerfilUsuario p) {
        if (p.getId() == null) { em.persist(p); return p; }
        return em.merge(p);
    }

    public Optional<PerfilUsuario> buscarPorId(Long id) { return Optional.ofNullable(em.find(PerfilUsuario.class, id)); }

    public Optional<PerfilUsuario> buscarPorNome(String nome) {
        try {
            return Optional.of(em.createQuery("SELECT p FROM PerfilUsuario p WHERE p.nome=:n", PerfilUsuario.class)
                .setParameter("n", nome).getSingleResult());
        } catch (Exception e) { return Optional.empty(); }
    }

    public List<PerfilUsuario> listarTodos() {
        return em.createQuery("SELECT p FROM PerfilUsuario p ORDER BY p.nome", PerfilUsuario.class).getResultList();
    }
}
