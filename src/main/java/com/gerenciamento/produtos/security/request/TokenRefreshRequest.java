package com.gerenciamento.produtos.security.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;

@ApiModel(description = "Modelo para enviar uma simples requisição para efetuar o refresh token")
public class TokenRefreshRequest {

    @NotBlank
    @ApiModelProperty(
            notes = "refreshToken",
            example = "fkk32fds-23424-fsdfs-4332"
    )
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}