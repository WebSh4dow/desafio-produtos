package com.gerenciamento.produtos.openApiController;

import com.gerenciamento.produtos.security.request.LoginRequest;
import com.gerenciamento.produtos.security.request.SignupSimpleRequest;
import com.gerenciamento.produtos.security.request.TokenRefreshRequest;
import com.gerenciamento.produtos.security.response.JwtResponse;
import com.gerenciamento.produtos.security.response.TokenRefreshResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;

@Validated
@Api(tags = "Autenticação")
public interface AutentecacaoControllerOpenApi {

    @ApiOperation(value = "Autentica um usuário que foi cadastrado e retorna o token JWT")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Auenticação bem-sucedida",response = JwtResponse.class),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<JwtResponse> autenticarUsuario(@Valid @RequestBody LoginRequest loginRequest);

    @ApiOperation(value = "Registra um novo usuário na api")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Token JWT atualizado com sucesso"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<String> registrarUsuario(@Valid @RequestBody SignupSimpleRequest signUpRequest);

    @ApiOperation(value = "Atualiza o token JWT usando o token de atualização")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Token JWT atualizado com sucesso", response = TokenRefreshResponse.class),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<TokenRefreshResponse> refreshtoken(@Valid @RequestBody TokenRefreshRequest request);

    @ApiOperation(value = "Efetua logout do usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Logout realizado com sucesso"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<String> logoutUser();
}
