package com.gerenciamento.produtos.service;

import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.repository.ProdutoRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public byte[] gerarRelatorioCSV(Long id, String nome, String sku) {
        List<Produto> produtos = produtoRepository.consultarCamposProduto(id, nome, sku);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(baos), CSVFormat.DEFAULT.withHeader("id", "nome", "sku"))) {

            produtos.stream()
                    .map(produto -> Arrays.asList(
                            String.valueOf(produto.getId()),
                            produto.getNome(),
                            produto.getSku()
                    ))
                    .forEach(linha -> {
                        try {
                            csvPrinter.printRecord(linha);
                        } catch (IOException e) {
                            throw new RuntimeException("Erro ao gerar relatório CSV", e);
                        }
                    });

            csvPrinter.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório CSV", e);
        }
    }

    public byte[] gerarRelatorioXLSX(Long id, String nome, String sku) {
        List<Produto> produtos = produtoRepository.consultarCamposProduto(id, nome, sku);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Relatorio");

            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("id");
            headerRow.createCell(1).setCellValue("nome");
            headerRow.createCell(2).setCellValue("sku");

            int rowNum = 1;
            for (Produto produto : produtos) {
                Row row = sheet.createRow(rowNum++);
                List<String> linha = Arrays.asList(
                        String.valueOf(produto.getId()),
                        produto.getNome(),
                        produto.getSku()
                );

                for (int i = 0; i < linha.size(); i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(linha.get(i));
                }
            }

            workbook.write(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório XLSX", e);
        }
    }

}
