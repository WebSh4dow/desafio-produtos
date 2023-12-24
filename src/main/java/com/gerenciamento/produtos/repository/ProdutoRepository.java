package com.gerenciamento.produtos.repository;

import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.repository.query.ProdutoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    @Query(value = ProdutoRepositoryQuery.FILTRAR_POR_NOME_PRODUTO)
    List<Produto> filtrarProdutoPorNome(String nome);

    @Query(value = ProdutoRepositoryQuery.FILTRAR_POR_NOME_AND_CATEGORIA)
    List<Produto> filtrarProdutoPorNomeAndCategoria(String nome, String categoria_id);

    @Query(value = ProdutoRepositoryQuery.FILTRAR_POR_NOME_AND_CATEGORIA_AND_DATA_CADASTRO)
    List<Produto> filtrarProdutoPorNomeAndCategoriaAndDataCadastro(String nome, String categoria_id, Date dataCadastro);
}
