package com.gerenciamento.produtos.enums;

import lombok.Getter;

@Getter
public enum TiposUsuarios {
    ESTOQUISTA("ESTOQUISTA"),

    ADMINISTRADOR("ADMINISTRADOR"),

    NORMAL("NORMAL");

    private String tipos;

    TiposUsuarios(String tipos) {
        this.tipos = tipos;
    }
}
