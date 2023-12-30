package com.gerenciamento.produtos.security.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;

@ApiModel(description = "Modelo para enviar uma requisição para efetuar o cadastro inicial no sistema")
public class LoginRequest {

    @NotBlank
    @ApiModelProperty(
            notes = "nome do usuario",
            example = "Francisco Jarmison de Sousa Paiva"
    )
    private String login;

    @NotBlank
    @ApiModelProperty(
            notes = "senha do usuário",
            example = "qualquerSenha123"
    )
    private String senha;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}