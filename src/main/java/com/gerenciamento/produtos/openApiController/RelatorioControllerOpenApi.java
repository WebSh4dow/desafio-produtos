package com.gerenciamento.produtos.openApiController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(tags = "Relatório de produtos")
public interface RelatorioControllerOpenApi {

    @ApiOperation(value = "Gera o relatório de produtos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gerou produtos com sucesso para exportação!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<?> gerarRelatorio(String formato, String campos, Long id, String nome, String sku);
}
