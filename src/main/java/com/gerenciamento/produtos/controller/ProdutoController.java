package com.gerenciamento.produtos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciamento.produtos.exception.BussinesException;
import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.model.assembler.ProdutoAssembler;
import com.gerenciamento.produtos.model.representation.ProdutoRepresentationModel;
import com.gerenciamento.produtos.openApiController.ProdutoControllerOpenApi;
import com.gerenciamento.produtos.repository.ProdutoRepository;
import com.gerenciamento.produtos.service.AuditoriaService;
import com.gerenciamento.produtos.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController implements ProdutoControllerOpenApi {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoAssembler produtoAssembler;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    private ObjectMapper objectMapper;


    Logger logger = LoggerFactory.getLogger(ProdutoController.class);


    @GetMapping("/ativos")
    public ResponseEntity<CollectionModel<ProdutoRepresentationModel>> buscarAtivos(@PageableDefault(size = 10) Pageable pageable) {
        Page<Produto> produtosAtivos = produtoRepository.buscarProdutosAtivos(pageable);
        List<ProdutoRepresentationModel> produtoModels = produtosAtivos.stream()
                .map(produtoAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<ProdutoRepresentationModel> response = CollectionModel.of(produtoModels,
                linkTo(methodOn(ProdutoController.class).buscarAtivos(pageable)).withSelfRel());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/inativos")
    public ResponseEntity<CollectionModel<ProdutoRepresentationModel>> buscarInativos(@PageableDefault(size = 10) Pageable pageable) {
        Page<Produto> produtosInativos = produtoRepository.buscarProdutosInativos(pageable);
        List<ProdutoRepresentationModel> produtoModels = produtosInativos.stream()
                .map(produtoAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<ProdutoRepresentationModel> response = CollectionModel.of(produtoModels,
                linkTo(methodOn(ProdutoController.class).buscarInativos(pageable)).withSelfRel());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<CollectionModel<ProdutoRepresentationModel>> buscarProdutoPorIdPaginado(@PathVariable Long id) {
        Produto produto = produtoRepository.findById(id).orElse(null);

        if (produto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProdutoRepresentationModel produtoModel = produtoAssembler.toModel(produto);
        CollectionModel<ProdutoRepresentationModel> response = CollectionModel.of(Collections.singletonList(produtoModel));

        return new ResponseEntity<>(response, HttpStatus.OK);
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

        for (Produto produto : produtoPage) {
            auditoriaService.registrarAuditoria("Nenhum Objeto a ser alterado", "CONSULTAR PRODUTOS POR NOME", new Date(), produto.getId());

            auditoriaService.registrarAuditoriaDetalhada("Nenhum Objeto a ser alterado", "CONSULTAR PRODUTOS POR NOME", new Date(), produto.getId(),
                    objectMapper.convertValue(produto, Map.class));
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

        for (Produto produto : produtoPage) {
            auditoriaService.registrarAuditoria("Nenhum Objeto a ser alterado", "CONSULTAR PRODUTOS POR NOME E CATEGORIA", new Date(), produto.getId());

            auditoriaService.registrarAuditoriaDetalhada("Nenhum Objeto a ser alterado", "CONSULTAR PRODUTOS POR NOME E CATEGORIA", new Date(), produto.getId(),
                    objectMapper.convertValue(produto, Map.class));
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

        for (Produto produto : produtoPage) {
            auditoriaService.registrarAuditoria("Nenhum Objeto a ser alterado", "CONSULTAR PRODUTOS POR MULTIPLOS CAMPOS", new Date(), produto.getId());

            auditoriaService.registrarAuditoriaDetalhada("Nenhum Objeto a ser alterado", "CONSULTAR PRODUTOS POR MULTIPLOS CAMPOS", new Date(), produto.getId(),
                    objectMapper.convertValue(produto, Map.class));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/cadastrar/novo-produto")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Produto produto) {
        try {

            if (produto.getId() == null && produtoRepository.existeProdutoNomeCadastrado(produto.getNome()) != null) {
                return ResponseEntity.badRequest().body("Já existe um produto cadastrado com nome: " + produto.getNome());
            }

            if (produto.getId() == null && produtoRepository.existeSkuProdutoCadastrado(produto.getSku()) != null) {
                return ResponseEntity.badRequest().body("Já existe um produto cadastrado com sku: " + produto.getSku());
            }

            Produto produtoCadastrado = produtoService.cadastrar(produto);

            auditoriaService.registrarAuditoria("Produto", "CADASTRO DE PRODUTOS", new Date(), produto.getId());

            auditoriaService.registrarAuditoriaDetalhada("Produto", "CADASTRO DE PRODUTOS", new Date(), produtoCadastrado.getId(),
                    objectMapper.convertValue(produtoCadastrado, Map.class));

            return ResponseEntity.status(HttpStatus.CREATED).body(produtoCadastrado);

        } catch (BussinesException | IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno." + e.getStackTrace());
        }
    }

    @PutMapping("/atualizar/produto-existente/{produtoId}")
    public ResponseEntity<?> atualizar(@RequestBody Produto produto, @PathVariable Long produtoId) {
        try {
            Produto produtoAtual = produtoService.buscarPor(produtoId);
            if (produtoAtual != null) {

                if (produto.getId() == null && produtoRepository.existeProdutoNomeCadastrado(produto.getNome()) != null) {
                    return ResponseEntity.badRequest().body("Já existe um produto cadastrado com nome: " + produto.getNome());
                }

                if (produto.getId() == null && produtoRepository.existeSkuProdutoCadastrado(produto.getSku()) != null) {
                    return ResponseEntity.badRequest().body("Já existe um produto cadastrado com sku: " + produto.getSku());
                }

                BeanUtils.copyProperties(produto, produtoAtual, "id");

                auditoriaService.registrarAuditoria("Produto", "EDIÇÃO DE PRODUTOS", new Date(), produtoAtual.getId());

                auditoriaService.registrarAuditoriaDetalhada("Produto", "EDIÇÃO DE PRODUTOS", new Date(), produtoAtual.getId(),
                        getDiffFieldsProduto(produto, produtoAtual));

                produtoAtual = produtoService.cadastrar(produtoAtual);

                ProdutoRepresentationModel produtoModel = produtoAssembler.toModel(produtoAtual);

                return ResponseEntity.ok().body(produtoModel);
            }

            return ResponseEntity.notFound().build();

        } catch (BussinesException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (NoSuchElementException | EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/editar-campos/{produtoId}")
    public ResponseEntity<?> editarCamposProdutos(@PathVariable Long produtoId,
                                                  @RequestBody Map<String, Object> campos) {
        Produto produtoAtual = produtoService.buscarPor(produtoId);

        if (produtoAtual == null) {
            return ResponseEntity.notFound().build();
        }

        Produto produtoAntesEdicaoParcial = new Produto();
        BeanUtils.copyProperties(produtoAtual, produtoAntesEdicaoParcial);

        produtoService.mergeProduto(campos, produtoAtual);

        auditoriaService.registrarAuditoria("Produto", "ATUALIZAR CAMPOS PARCIALMENTE", new Date(), produtoAtual.getId());
        auditoriaService.registrarAuditoriaDetalhada("Produto", "ATUALIZAR CAMPOS PARCIALMENTE", new Date(), produtoAtual.getId(),
                getDiffFieldsProduto(produtoAntesEdicaoParcial, produtoAtual));

        return atualizar(produtoAtual, produtoId);
    }


    @DeleteMapping("/remover-produto/{produtoId}")
    public ResponseEntity<?> remover(@PathVariable Long produtoId) {
        try {
            Produto produtoAtual = produtoService.buscarPor(produtoId);

            auditoriaService.registrarAuditoria("Produto", "EXCLUSÃO DE PRODUTOS", new Date(), produtoAtual.getId());

            auditoriaService.registrarAuditoriaDetalhada("Produto", "EXCLUSÃO DE PRODUTOS", new Date(), produtoAtual.getId(),
                    objectMapper.convertValue(produtoAtual, Map.class));

            produtoService.remover(produtoAtual);

            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException | NoSuchElementException exception) {
            return ResponseEntity.badRequest().body("O produto não existe ou já foi realizado a exclusão do produto atual com id: " + produtoId);
        }
    }

    private Map<String, Object> getDiffFieldsProduto(Object origem, Object destino) {
        Map<String, Object> diffFields = new HashMap<>();
        Field[] fields = origem.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object valorOrigemProduto = field.get(origem);
                Object valorDestinoProduto = field.get(destino);
                if (!Objects.equals(valorOrigemProduto, valorDestinoProduto)) {
                    diffFields.put(field.getName(), valorDestinoProduto);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return diffFields;
    }

}
