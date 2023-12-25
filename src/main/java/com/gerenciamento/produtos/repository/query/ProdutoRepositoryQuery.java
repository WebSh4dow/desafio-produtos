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


    public static String EXISTE_PRODUTO_COM_SKU_DUPLICADO =
            "SELECT pr " +
                    "FROM Produto " +
                    "pr WHERE pr.sku = ?1";

    public static String EXISTE_PRODUTO_COM_MESMO_NOME =
            "SELECT pr " +
                    "FROM Produto " +
                    "pr WHERE pr.nome = ?1";

    public static String NEXT_VALUE_SEQUENCE_PRODUTO =
            "SELECT setval(:sequenceName, COALESCE((SELECT MAX(id) + 1 FROM produto), 1), false)";

    public static String SEQUENCE_PRODUTO =
            "seq_produto";

    public static String PARAMETER_SEQUENCE =
            "sequenceName";

    public static String CONSULTAR_PRODUTOS_ATIVOS =
            "SELECT * FROM Produto " +
                    "WHERE ativo = true";

    public static String CONSULTAR_PRODUTOS_INATIVOS =
            "SELECT * FROM Produto " +
                    "WHERE ativo = false";



}
