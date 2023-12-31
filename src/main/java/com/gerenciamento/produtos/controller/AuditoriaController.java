package com.gerenciamento.produtos.controller;

import com.gerenciamento.produtos.model.Auditoria;
import com.gerenciamento.produtos.openApiController.AuditoriaControllerOpenApi;
import com.gerenciamento.produtos.service.AuditoriaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auditoria")
@ApiOperation("Listar auditorias")
@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Token JWT Bearer", required = true, dataType = "string", paramType = "header")
})
public class AuditoriaController implements AuditoriaControllerOpenApi {

    @Autowired
    private AuditoriaService auditoriaService;

    @GetMapping("/listar")
    public ResponseEntity<Page<Auditoria>> listarAuditorias(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Auditoria> auditorias = auditoriaService.listarAuditorias(pageable);
        return new ResponseEntity<>(auditorias, HttpStatus.OK);
    }

    @GetMapping("/detalhar/{auditoriaId}")
    public ResponseEntity<Auditoria> detalharAuditoria(@PathVariable Long auditoriaId) {
        Auditoria auditoria = auditoriaService.detalharAuditoria(auditoriaId);
        if (auditoria != null) {
            return new ResponseEntity<>(auditoria, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}