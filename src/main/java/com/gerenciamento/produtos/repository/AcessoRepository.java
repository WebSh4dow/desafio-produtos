package com.gerenciamento.produtos.repository;

import com.gerenciamento.produtos.model.Acesso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AcessoRepository extends JpaRepository<Acesso, Long> {
    Optional<Acesso> findByDescricao(String descricao);
}
