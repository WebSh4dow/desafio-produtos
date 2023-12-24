package com.gerenciamento.produtos.repository.query;

public interface ProdutoRepositoryQuery {


    public static String FILTRAR_POR_NOME_PRODUTO =
            "SELECT pr FROM Produto pr " +
            "WHERE UPPER(TRIM(pr.nome)) " +
            "LIKE %?1%";

    public static String FILTRAR_POR_NOME_AND_CATEGORIA =
            "SELECT pr FROM Produto pr " +
            "INNER JOIN pr.categoria cat " +
            "WHERE UPPER(TRIM(pr.nome)) LIKE %?1% " +
            "OR UPPER(TRIM(cat.nome)) LIKE %?2%";

    public static String FILTRAR_POR_NOME_AND_CATEGORIA_AND_DATA_CADASTRO =
            "SELECT pr FROM Produto pr " +
            "INNER JOIN pr.categoria cat " +
            "WHERE UPPER(TRIM(pr.nome)) LIKE %?1% " +
            "OR UPPER(TRIM(cat.nome)) LIKE %?2%" +
            "OR pr.dataCadastro = ?3";
}
