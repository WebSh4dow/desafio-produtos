package com.gerenciamento.produtos.controller;

import com.gerenciamento.produtos.exception.BussinesException;
import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.model.assembler.ProdutoAssembler;
import com.gerenciamento.produtos.model.representation.ProdutoRepresentationModel;
import com.gerenciamento.produtos.repository.ProdutoRepository;
import com.gerenciamento.produtos.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoAssembler produtoAssembler;

    @Autowired
    private ProdutoService produtoService;

    Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    @GetMapping("/listar/todos")
    public ResponseEntity<CollectionModel<ProdutoRepresentationModel>> listarTodosProdutos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort));

        Page<Produto> produtoPage = produtoRepository.findAll(pageable);

        List<ProdutoRepresentationModel> produtoModels = produtoPage.getContent().stream()
                .map(produtoAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<ProdutoRepresentationModel> response = CollectionModel.of(produtoModels,
                linkTo(methodOn(ProdutoController.class).listarTodosProdutos(page, size, sort)).withSelfRel());

        if (produtoPage.hasPrevious()) {
            response.add(linkTo(methodOn(ProdutoController.class).listarTodosProdutos(page - 1, size, sort)).withRel("prev"));
        }
        if (produtoPage.hasNext()) {
            response.add(linkTo(methodOn(ProdutoController.class).listarTodosProdutos(page + 1, size, sort)).withRel("next"));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pesquisar/por-id/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        Produto produto = produtoRepository.findById(id).get();
        return new ResponseEntity<Produto>(produto, HttpStatus.OK);
    }

    @GetMapping("/filtrar/por-nome")
    public ResponseEntity<CollectionModel<ProdutoRepresentationModel>> consultarProdutoPorNome(
            @RequestParam("nome") String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort));

        Page<Produto> produtoPage = produtoRepository.filtrarProdutoPorNomePaginado(nome, pageable);

        List<ProdutoRepresentationModel> produtoModels = produtoPage.getContent().stream()
                .map(produtoAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<ProdutoRepresentationModel> response = CollectionModel.of(produtoModels,
                linkTo(methodOn(ProdutoController.class).consultarProdutoPorNome(nome, page, size, sort)).withSelfRel());

        if (produtoPage.hasPrevious()) {
            response.add(linkTo(methodOn(ProdutoController.class).consultarProdutoPorNome(nome, page - 1, size, sort)).withRel("prev"));
        }
        if (produtoPage.hasNext()) {
            response.add(linkTo(methodOn(ProdutoController.class).consultarProdutoPorNome(nome, page + 1, size, sort)).withRel("next"));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filtrar/por-nome-e-categoria")
    public ResponseEntity<CollectionModel<ProdutoRepresentationModel>> consultarPorNomeECategoria(
            @RequestParam("nome") String nome,
            @RequestParam("categoria") String categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort));

        Page<Produto> produtoPage = produtoRepository.filtrarProdutoPorNomeAndCategoriaPaginado(nome, categoria, pageable);

        List<ProdutoRepresentationModel> produtoModels = produtoPage.getContent().stream()
                .map(produtoAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<ProdutoRepresentationModel> response = CollectionModel.of(produtoModels,
                linkTo(methodOn(ProdutoController.class).consultarPorNomeECategoria(nome, categoria, page, size, sort)).withSelfRel());

        if (produtoPage.hasPrevious()) {
            response.add(linkTo(methodOn(ProdutoController.class).consultarPorNomeECategoria(nome, categoria, page - 1, size, sort)).withRel("prev"));
        }
        if (produtoPage.hasNext()) {
            response.add(linkTo(methodOn(ProdutoController.class).consultarPorNomeECategoria(nome, categoria, page + 1, size, sort)).withRel("next"));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filtrar/por-multiplos-atributos")
    public ResponseEntity<CollectionModel<ProdutoRepresentationModel>> consultarPorMultiplosAtributos(
            @RequestParam("nome") String nome,
            @RequestParam("categoria") String categoria,
            @RequestParam("dataCadastro") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataCadastro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort));

        Page<Produto> produtoPage = produtoRepository.filtrarProdutoPorNomeAndCategoriaAndDataCadastroPageable(nome, categoria, dataCadastro, pageable);

        List<ProdutoRepresentationModel> produtoModels = produtoPage.getContent().stream()
                .map(produtoAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<ProdutoRepresentationModel> response = CollectionModel.of(produtoModels,
                linkTo(methodOn(ProdutoController.class).consultarPorMultiplosAtributos(nome, categoria, dataCadastro, page, size, sort)).withSelfRel());

        if (produtoPage.hasPrevious()) {
            response.add(linkTo(methodOn(ProdutoController.class).consultarPorMultiplosAtributos(nome, categoria, dataCadastro, page - 1, size, sort)).withRel("prev"));
        }
        if (produtoPage.hasNext()) {
            response.add(linkTo(methodOn(ProdutoController.class).consultarPorMultiplosAtributos(nome, categoria, dataCadastro, page + 1, size, sort)).withRel("next"));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/cadastrar/novo-produto")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Produto produto) {
        try {
            logger.info("Cadastrando novo produto: {}", produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.cadastrar(produto));

        } catch (BussinesException | IllegalArgumentException e) {
            logger.warn("Erro ao cadastrar produto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            logger.error("Erro interno ao cadastrar produto", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno.");
        }
    }

    @PutMapping("/atualizar/produto-existente/{produtoId}")
    public ResponseEntity<?> atualizar(@RequestBody Produto produto, @PathVariable Long produtoId) {
        try {
            Produto produtoAtual = produtoService.buscarPor(produtoId);
            if (produtoAtual != null) {

                if (produtoRepository.existeProdutoNomeCadastrado(produto.getNome()).equals(produtoAtual.getNome())) {
                    return ResponseEntity.badRequest().body("Existe produtos cadastrados com o mesmo nome.");
                }

                if (produtoRepository.existeSkuProdutoCadastrado(produto.getSku()).equals(produtoAtual.getSku())) {
                    return ResponseEntity.badRequest().body("Existe produtos cadastrados com o mesmo sku.");
                }

                BeanUtils.copyProperties(produto, produtoAtual, "id");
                produtoAtual = produtoService.cadastrar(produtoAtual);

                return ResponseEntity.ok().body(produtoAtual);
            }

            return ResponseEntity.notFound().build();

        } catch (BussinesException | IllegalArgumentException e) {
            logger.warn("Erro ao cadastrar produto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            logger.error("Erro interno ao cadastrar produto", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno.");
        }
    }

}
