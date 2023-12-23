package com.gerenciamento.produtos.repository;

import com.gerenciamento.produtos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
}
