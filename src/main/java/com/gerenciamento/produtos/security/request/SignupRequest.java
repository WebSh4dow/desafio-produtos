package com.gerenciamento.produtos.security.request;

import com.gerenciamento.produtos.model.Acesso;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@ApiModel(description = "Modelo para enviar uma  requisição para efetuar o cadastro inicial no sistema")
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    @ApiModelProperty(
            notes = "nome do usuario",
            example = "Francisco Jarmison de Sousa Paiva"
    )
    private String login;

    @ApiModelProperty(
            notes = "Acessos do usuário",
            example = "[{\"id\": 1, \"descricao\": \"NORMAL\"}]"
    )
    private Set<Acesso> acessos;

    @NotBlank
    @Size(min = 6, max = 40)
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

    public Set<Acesso> getAcessos() {
        return acessos;
    }

    public void setAcessos(Set<Acesso> acessos) {
        this.acessos = acessos;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
