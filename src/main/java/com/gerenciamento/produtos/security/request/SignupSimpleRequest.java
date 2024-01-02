package com.gerenciamento.produtos.security.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "Modelo para enviar uma simples requisição para efetuar o cadastro inicial no sistema")
public class SignupSimpleRequest  {

    @NotBlank
    @Size(min = 3, max = 20)
    @ApiModelProperty(
            notes = "nome do usuario",
            example = "Francisco Jarmison de Sousa Paiva"
    )
    private String login;

    @NotBlank
    @Size(min = 6, max = 40)
    @ApiModelProperty(
            notes = "senha do usuário",
            example = "qualquerSenha123"
    )
    private String senha;

}
