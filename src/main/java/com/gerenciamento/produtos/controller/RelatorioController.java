package com.gerenciamento.produtos.controller;

import com.gerenciamento.produtos.openApiController.RelatorioControllerOpenApi;
import com.gerenciamento.produtos.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorio")
public class RelatorioController implements RelatorioControllerOpenApi {

    @Autowired
    private RelatorioService relatorioService;

    private static final String TIPO_CSV = "CSV";

    private static final String TIPO_XLSX = "XLSX";

    private static final String CONTENT_TYPE_CSV = "text/csv";

    private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private static final String CONTENT_DISPOSITION_FORM_DATA = "attachment";

    private static final String CONTENT_DISPOSITION_FORM_DATA_FILE_NAME = "relatorio.";

    @GetMapping("/gerar-relatorio")
    public ResponseEntity<?> gerarRelatorio(
            @RequestParam(defaultValue = "CSV") String formato,
            @RequestParam(defaultValue = "id,nome,sku") String campos,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String sku) {

        try {
            byte[] relatorioBytes;
            String contentType;

            if (TIPO_CSV.equalsIgnoreCase(formato)) {
                relatorioBytes = relatorioService.gerarRelatorioCSV(id, nome, sku);
                contentType = CONTENT_TYPE_CSV;

            } else if (TIPO_XLSX.equalsIgnoreCase(formato)) {
                relatorioBytes = relatorioService.gerarRelatorioXLSX(id, nome, sku);
                contentType = CONTENT_TYPE_XLSX;

            } else {
                return ResponseEntity.badRequest().body("Formato de relatório não suportado");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDispositionFormData(CONTENT_DISPOSITION_FORM_DATA, CONTENT_DISPOSITION_FORM_DATA_FILE_NAME + formato.toLowerCase());

            return new ResponseEntity<>(relatorioBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}
