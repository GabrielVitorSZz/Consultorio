package br.com.consultorio.model.dao;
import br.com.consultorio.model.entity.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PacienteDAO {
    @Inject EntityManager em;

    @Transactional
    public Paciente salvar(Paciente p) {
        if (p.getId() == null) { em.persist(p); return p; }
        return em.merge(p);
    }

    @Transactional
    public void inativar(Long id) {
        Paciente p = em.find(Paciente.class, id);
        if (p != null) { p.setAtivo(false); em.merge(p); }
    }

    public Optional<Paciente> buscarPorId(Long id) { return Optional.ofNullable(em.find(Paciente.class, id)); }

    public Optional<Paciente> buscarPorCpf(String cpf, Long idExcluir) {
        try {
            return Optional.of(em.createQuery("SELECT p FROM Paciente p WHERE p.cpf=:c AND (:id IS NULL OR p.id<>:id)", Paciente.class)
                .setParameter("c", cpf).setParameter("id", idExcluir).getSingleResult());
        } catch (Exception e) { return Optional.empty(); }
    }

    public List<Paciente> listarAtivos() {
        return em.createQuery("SELECT p FROM Paciente p WHERE p.ativo=true ORDER BY p.nome", Paciente.class).getResultList();
    }

    public List<Paciente> buscarPorNome(String nome) {
        return em.createQuery("SELECT p FROM Paciente p WHERE p.ativo=true AND LOWER(p.nome) LIKE LOWER(:n) ORDER BY p.nome", Paciente.class)
            .setParameter("n", "%" + nome + "%").getResultList();
    }
}
