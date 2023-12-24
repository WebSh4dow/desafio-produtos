package com.gerenciamento.produtos.controller;

import com.gerenciamento.produtos.exception.BussinesException;
import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.repository.ProdutoRepository;
import com.gerenciamento.produtos.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    @GetMapping("/filtrar/por-nome")
    public ResponseEntity<List<Produto>> consultarProdutoPorNome(@RequestParam("nome") String nome) {
        List<Produto> produtos = produtoRepository.filtrarProdutoPorNome(nome);
        return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
    }

    @GetMapping("/filtar/por-nome-e-categoria")
    public ResponseEntity<List<Produto>> consultarPorNomeECategoria(@RequestParam("nome") String nome,
                                                                    @RequestParam("categoria") String categoria) {

        List<Produto> produtos = produtoRepository.filtrarProdutoPorNomeAndCategoria(nome,categoria);
        return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
    }

    @GetMapping("/filtar/por-multiplos-atributos")
    public ResponseEntity<List<Produto>> consultarPorMultiplosAtributos(@RequestParam("nome") String nome,
                                                                        @RequestParam("categoria") String categoria,
                                                                        @RequestParam("dataCadastro") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataCadastro){

        List<Produto> produtos = produtoRepository.filtrarProdutoPorNomeAndCategoriaAndDataCadastro(nome,categoria,dataCadastro);
        return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
    }

    @PostMapping("/cadastrar/novo-produto")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Produto produto) {
        try {
            logger.info("Cadastrando novo produto: {}", produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.cadastrar(produto));

        } catch (BussinesException e) {
            logger.warn("Erro ao cadastrar produto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            logger.error("Erro interno ao cadastrar produto", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno.");
        }
    }
}
