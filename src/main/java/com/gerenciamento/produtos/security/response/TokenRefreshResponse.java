package com.gerenciamento.produtos.security.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Modelo de representação refresh token")
public class TokenRefreshResponse {
    @ApiModelProperty(notes = "Access token do jwt gerado", example = "342432423-fdsfsf332-432423ref")
    private String accessToken;

    @ApiModelProperty(notes = "Refresh token do jwt gerado", example = "342432423-fdsfsf332-432423ref")
    private String refreshToken;

    @ApiModelProperty(notes = "Tipo do jwt", example = "Bearer")
    private String tokenType = "Bearer";

    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
