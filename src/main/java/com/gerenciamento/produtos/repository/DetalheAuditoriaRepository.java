package com.gerenciamento.produtos.repository;

import com.gerenciamento.produtos.model.DetalheAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalheAuditoriaRepository extends JpaRepository<DetalheAuditoria, Long> {
}
