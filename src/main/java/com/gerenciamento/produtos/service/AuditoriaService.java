package com.gerenciamento.produtos.service;

import com.gerenciamento.produtos.configuration.AuditoriaConfiguration;
import com.gerenciamento.produtos.model.Auditoria;
import com.gerenciamento.produtos.model.DetalheAuditoria;
import com.gerenciamento.produtos.repository.AuditoriaRepository;
import com.gerenciamento.produtos.repository.DetalheAuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Autowired
    private DetalheAuditoriaRepository detalheAuditoriaRepository;

    @Autowired
    private AuditoriaConfiguration auditoriaConfiguration;

    @Autowired
    private AuditorAware<String> auditorAware;

    public Page<Auditoria> listarAuditorias(Pageable pageable) {
        return auditoriaRepository.findAll(pageable);
    }

    public Auditoria detalharAuditoria(Long auditoriaId) {
        return auditoriaRepository.findById(auditoriaId).orElse(null);
    }

    @Transactional
    public void registrarAuditoria(String objetoAlterado, String acaoRealizada, Date dataHora, Long objetoId) {
        Auditoria auditoria = new Auditoria();
        auditoria.setObjetoAlterado(objetoAlterado);
        auditoria.setAcaoRealizada(acaoRealizada);
        auditoria.setDataHora(dataHora);
        auditoria.setId(objetoId);

        String usuario = auditorAware.getCurrentAuditor().orElse("Não foi possivel encontrar esse usuário logado em nosso sistema.");
        auditoria.setUsuario(usuario);

        auditoriaRepository.save(auditoria);
    }

    @Transactional
    public void registrarAuditoriaDetalhada(String objetoAlterado, String acaoRealizada, Date dataHora, Long objetoId, Map<String, Object> detalhes) {
        registrarAuditoria(objetoAlterado, acaoRealizada, dataHora, objetoId);

        for (Map.Entry<String, Object> entry : detalhes.entrySet()) {
            registrarDetalheAuditoria(objetoAlterado, objetoId, entry.getKey(), entry.getValue().toString(), null);
        }
    }

    @Transactional
    private void registrarDetalheAuditoria(String objetoAlterado, Long objetoId, String campo, String valorAnterior, String valorAtual) {
        DetalheAuditoria detalheAuditoria = new DetalheAuditoria();
        detalheAuditoria.setObjetoAlterado(objetoAlterado);
        detalheAuditoria.setObjetoId(objetoId);
        detalheAuditoria.setCampo(campo);

        if (!Objects.equals(valorAtual, valorAnterior)) {
            detalheAuditoria.setValorAnterior(valorAnterior);
            detalheAuditoria.setValorAtual(valorAtual);
            detalheAuditoriaRepository.save(detalheAuditoria);
        }

        detalheAuditoriaRepository.save(detalheAuditoria);
    }

}
