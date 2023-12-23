package com.gerenciamento.produtos.security.response;

import com.gerenciamento.produtos.model.Acesso;

import java.util.List;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String login;
    private List<Acesso> acessos;

    public JwtResponse(String accessToken, String refreshToken, Long id, String username, String login, List<Acesso> acessos) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.login = username;
        this.acessos = acessos;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Acesso> getAcessos() {
        return acessos;
    }

    public void setAcessos(List<Acesso> acessos) {
        this.acessos = acessos;
    }
}