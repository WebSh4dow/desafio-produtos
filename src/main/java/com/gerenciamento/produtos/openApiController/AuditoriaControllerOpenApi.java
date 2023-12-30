package com.gerenciamento.produtos.openApiController;

import com.gerenciamento.produtos.model.Auditoria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;


@Api(tags = "Auditoria")
public interface AuditoriaControllerOpenApi {
    @ApiOperation(value = "Listar Recurso de Auditoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca por recursos de Auditoria foi retornada com sucesso!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })

    ResponseEntity<Page<Auditoria>> listarAuditorias(int page, int size);

    @ApiOperation(value = "Listar Recurso de Auditoria Detalhadamente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca por recursos de Auditoria detalhada foi retornada com sucesso!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<Auditoria> detalharAuditoria(Long auditoriaId);
}
