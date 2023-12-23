package com.gerenciamento.produtos.enums;

public enum ProblemType {
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    ACESSO_NEGADO_USUARIO("/acesso-negado", "Acesso negado"),

    REFRESH_TOKEN_EXPIRADO("/refresh-token", "Refresh Token Expirado"),
    ERRO_INTERNO("/erro-interno", "Erro interno no servidor"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    MENSAGEM_NAO_COMPREENSIVEL("/mensagem-nao-compreensivel", "Mensagem incompreensível"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_DE_NEGOCIO("/erro-negocio", "Violação de erro de negócio");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "http://localhost:8080" + path;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }
}