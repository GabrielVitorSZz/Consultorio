package br.com.consultorio.model.dao;
import br.com.consultorio.model.entity.LogAcao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class LogAcaoDAO {
    @Inject EntityManager em;

    @Transactional
    public LogAcao registrar(LogAcao log) { em.persist(log); return log; }

    public List<LogAcao> listarTodos() {
        return em.createQuery("SELECT l FROM LogAcao l ORDER BY l.dataHora DESC", LogAcao.class).getResultList();
    }

    public List<LogAcao> listarUltimos(int n) {
        return em.createQuery("SELECT l FROM LogAcao l ORDER BY l.dataHora DESC", LogAcao.class).setMaxResults(n).getResultList();
    }
}
