package com.gerenciamento.produtos.controller;

import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

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
}
