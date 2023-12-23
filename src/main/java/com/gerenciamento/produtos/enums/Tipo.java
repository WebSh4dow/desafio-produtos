package com.gerenciamento.produtos.enums;

public enum Tipo {

    NORMAL("Normal"), ESPECIAL("Especial"), PERSONALIZADO("Personalizado");

    private String descricao;

    Tipo (String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
