package com.gerenciamento.produtos.security.response;

import com.gerenciamento.produtos.model.Acesso;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(description = "Modelo de representação do responde do token JWT")
public class JwtResponse {

    @ApiModelProperty(notes = "token jwt", example = "43922h3 4823h42 428g8 2g4h83824g4jj9j492")
    private String token;

    @ApiModelProperty(notes = "Tipo do jwt", example = "Bearer")
    private String type = "Bearer";

    @ApiModelProperty(notes = "Refresh token do jwt gerado", example = "342432423-fdsfsf332-432423ref")
    private String refreshToken;

    @ApiModelProperty(notes = "id do refresh token", example = "1")
    private Long id;

    @ApiModelProperty(notes = "login do usuário do jwt", example = "1")
    private String login;

    @ApiModelProperty(
            notes = "Acessos do usuário",
            example = "[{\"id\": 1, \"descricao\": \"NORMAL\"}]"
    )
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