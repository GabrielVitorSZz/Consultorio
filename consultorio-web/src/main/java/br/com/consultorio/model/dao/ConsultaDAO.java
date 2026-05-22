package br.com.consultorio.model.dao;
import br.com.consultorio.model.entity.Consulta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ConsultaDAO {
    @Inject EntityManager em;
    private static final String FETCH = "SELECT c FROM Consulta c JOIN FETCH c.paciente JOIN FETCH c.medico m JOIN FETCH m.usuario ";

    @Transactional
    public Consulta salvar(Consulta c) {
        if (c.getId() == null) { em.persist(c); return c; }
        return em.merge(c);
    }

    public Optional<Consulta> buscarPorId(Long id) {
        try {
            return Optional.of(em.createQuery(FETCH + "WHERE c.id=:id", Consulta.class).setParameter("id", id).getSingleResult());
        } catch (Exception e) { return Optional.empty(); }
    }

    public List<Consulta> listarTodas() {
        return em.createQuery(FETCH + "ORDER BY c.dataHora DESC", Consulta.class).getResultList();
    }

    public List<Consulta> listarPorPaciente(Long pacienteId) {
        return em.createQuery(FETCH + "WHERE c.paciente.id=:pid ORDER BY c.dataHora DESC", Consulta.class)
            .setParameter("pid", pacienteId).getResultList();
    }

    public List<Consulta> listarPorMedico(Long medicoId) {
        return em.createQuery(FETCH + "WHERE c.medico.id=:mid ORDER BY c.dataHora DESC", Consulta.class)
            .setParameter("mid", medicoId).getResultList();
    }

    public boolean existeConflito(Long medicoId, LocalDateTime dataHora, Long idExcluir) {
        LocalDateTime ini = dataHora.minusMinutes(29), fim = dataHora.plusMinutes(29);
        return em.createQuery(
            "SELECT COUNT(c) FROM Consulta c WHERE c.medico.id=:mid AND c.dataHora BETWEEN :ini AND :fim " +
            "AND c.status<>'CANCELADA' AND (:id IS NULL OR c.id<>:id)", Long.class)
            .setParameter("mid", medicoId).setParameter("ini", ini).setParameter("fim", fim)
            .setParameter("id", idExcluir).getSingleResult() > 0;
    }
}
