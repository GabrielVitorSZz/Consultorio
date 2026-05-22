package br.com.consultorio.model.dao;
import br.com.consultorio.model.entity.FichaAtendimento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FichaAtendimentoDAO {
    @Inject EntityManager em;

    @Transactional
    public FichaAtendimento salvar(FichaAtendimento f) {
        if (f.getId() == null) { em.persist(f); return f; }
        return em.merge(f);
    }

    public Optional<FichaAtendimento> buscarPorId(Long id) { return Optional.ofNullable(em.find(FichaAtendimento.class, id)); }

    public Optional<FichaAtendimento> buscarPorConsulta(Long consultaId) {
        try {
            return Optional.of(em.createQuery(
                "SELECT f FROM FichaAtendimento f JOIN FETCH f.consulta c JOIN FETCH c.paciente JOIN FETCH c.medico m JOIN FETCH m.usuario WHERE c.id=:cid",
                FichaAtendimento.class).setParameter("cid", consultaId).getSingleResult());
        } catch (Exception e) { return Optional.empty(); }
    }

    public List<FichaAtendimento> listarPorPaciente(Long pacienteId) {
        return em.createQuery(
            "SELECT f FROM FichaAtendimento f JOIN FETCH f.consulta c JOIN FETCH c.paciente p JOIN FETCH c.medico m JOIN FETCH m.usuario " +
            "WHERE p.id=:pid ORDER BY f.dataRegistro DESC", FichaAtendimento.class)
            .setParameter("pid", pacienteId).getResultList();
    }
}
