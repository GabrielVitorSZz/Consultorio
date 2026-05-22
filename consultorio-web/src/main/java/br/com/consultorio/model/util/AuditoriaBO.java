package br.com.consultorio.model.util;

import br.com.consultorio.model.dao.LogAcaoDAO;
import br.com.consultorio.model.entity.LogAcao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Utilitário de auditoria. Registra todas as ações do sistema.
 * Deve ser chamado em todos os BOs após operações bem-sucedidas.
 */
@ApplicationScoped
public class AuditoriaBO {

    @Inject
    LogAcaoDAO logDAO;

    public void registrar(String acao, Long usuarioId, String usuarioEmail, String detalhes) {
        LogAcao log = new LogAcao(acao, usuarioId, usuarioEmail, detalhes);
        logDAO.registrar(log);
    }

    public void registrar(String acao, String usuarioEmail, String detalhes) {
        registrar(acao, null, usuarioEmail, detalhes);
    }
}
