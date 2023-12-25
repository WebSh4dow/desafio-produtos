package com.gerenciamento.produtos.repository;

import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.repository.query.ProdutoRepositoryQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    @Query(value = ProdutoRepositoryQuery.FILTRAR_POR_NOME_PRODUTO)
    List<Produto> filtrarProdutoPorNome(String nome);

    @Query(value = ProdutoRepositoryQuery.FILTRAR_POR_NOME_PRODUTO)
    Page<Produto> filtrarProdutoPorNomePaginado(String nome, Pageable page);

    @Query(value = ProdutoRepositoryQuery.FILTRAR_POR_NOME_AND_CATEGORIA)
    List<Produto> filtrarProdutoPorNomeAndCategoria(String nome, String categoria_id);

    @Query(value = ProdutoRepositoryQuery.FILTRAR_POR_NOME_AND_CATEGORIA)
    Page<Produto> filtrarProdutoPorNomeAndCategoriaPaginado(String nome, String categoria_id, Pageable pageable);

    @Query(value = ProdutoRepositoryQuery.FILTRAR_POR_NOME_AND_CATEGORIA_AND_DATA_CADASTRO)
    List<Produto> filtrarProdutoPorNomeAndCategoriaAndDataCadastro(String nome, String categoria_id, Date dataCadastro);

    @Query(value = ProdutoRepositoryQuery.FILTRAR_POR_NOME_AND_CATEGORIA_AND_DATA_CADASTRO)
    Page<Produto> filtrarProdutoPorNomeAndCategoriaAndDataCadastroPageable(String nome, String categoria_id, Date dataCadastro, Pageable pageable);

    @Query(value = ProdutoRepositoryQuery.EXISTE_PRODUTO_COM_SKU_DUPLICADO)
    List<Produto> existeSkuProdutoCadastrado(String sku);

    @Query(value = ProdutoRepositoryQuery.EXISTE_PRODUTO_COM_MESMO_NOME)
    List<Produto> existeProdutoNomeCadastrado(String nome);
    @Query(value = ProdutoRepositoryQuery.CONSULTAR_PRODUTOS_INATIVOS)
    Page<Produto> buscarProdutosInativos(Pageable pageable);

    @Query(value = ProdutoRepositoryQuery.CONSULTAR_PRODUTOS_ATIVOS)
    Page<Produto> buscarProdutosAtivos(Pageable pageable);

}
