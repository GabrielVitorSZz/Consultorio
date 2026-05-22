package br.com.consultorio.model.dao;
import br.com.consultorio.model.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioDAO {
    @Inject EntityManager em;

    @Transactional
    public Usuario salvar(Usuario u) {
        if (u.getId() == null) { em.persist(u); return u; }
        return em.merge(u);
    }

    @Transactional
    public void inativar(Long id) {
        Usuario u = em.find(Usuario.class, id);
        if (u != null) { u.setAtivo(false); em.merge(u); }
    }

    public Optional<Usuario> buscarPorId(Long id) { return Optional.ofNullable(em.find(Usuario.class, id)); }

    public Optional<Usuario> buscarPorEmail(String email) {
        try {
            return Optional.of(em.createQuery("SELECT u FROM Usuario u JOIN FETCH u.perfil WHERE u.email=:e", Usuario.class)
                .setParameter("e", email).getSingleResult());
        } catch (NoResultException ex) { return Optional.empty(); }
    }

    public List<Usuario> listarTodos() {
        return em.createQuery("SELECT u FROM Usuario u JOIN FETCH u.perfil ORDER BY u.nome", Usuario.class).getResultList();
    }

    public boolean existeEmail(String email, Long idExcluir) {
        return em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.email=:e AND (:id IS NULL OR u.id<>:id)", Long.class)
            .setParameter("e", email).setParameter("id", idExcluir).getSingleResult() > 0;
    }
}
