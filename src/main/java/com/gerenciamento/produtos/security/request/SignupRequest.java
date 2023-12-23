package com.gerenciamento.produtos.security.request;

import com.gerenciamento.produtos.model.Acesso;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String login;

    private Set<Acesso> acessos;

    @NotBlank
    @Size(min = 6, max = 40)
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
