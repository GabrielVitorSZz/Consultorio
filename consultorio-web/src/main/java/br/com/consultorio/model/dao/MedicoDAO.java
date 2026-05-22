package br.com.consultorio.model.dao;
import br.com.consultorio.model.entity.Medico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MedicoDAO {
    @Inject EntityManager em;

    @Transactional
    public Medico salvar(Medico m) {
        if (m.getId() == null) { em.persist(m); return m; }
        return em.merge(m);
    }

    public Optional<Medico> buscarPorId(Long id) { return Optional.ofNullable(em.find(Medico.class, id)); }

    public Optional<Medico> buscarPorUsuarioId(Long uid) {
        try {
            return Optional.of(em.createQuery("SELECT m FROM Medico m JOIN FETCH m.usuario u WHERE u.id=:uid", Medico.class)
                .setParameter("uid", uid).getSingleResult());
        } catch (Exception e) { return Optional.empty(); }
    }

    public List<Medico> listarTodos() {
        return em.createQuery("SELECT m FROM Medico m JOIN FETCH m.usuario u JOIN FETCH u.perfil ORDER BY u.nome", Medico.class).getResultList();
    }

    public boolean existeCrm(String crm, Long idExcluir) {
        return em.createQuery("SELECT COUNT(m) FROM Medico m WHERE m.crm=:c AND (:id IS NULL OR m.id<>:id)", Long.class)
            .setParameter("c", crm).setParameter("id", idExcluir).getSingleResult() > 0;
    }
}
