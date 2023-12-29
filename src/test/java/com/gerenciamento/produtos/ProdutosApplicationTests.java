package com.gerenciamento.produtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciamento.produtos.controller.ProdutoController;
import com.gerenciamento.produtos.enums.Tipo;
import com.gerenciamento.produtos.model.Categoria;
import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.model.assembler.ProdutoAssembler;
import com.gerenciamento.produtos.model.representation.ProdutoRepresentationModel;
import com.gerenciamento.produtos.repository.CategoriaRepository;
import com.gerenciamento.produtos.repository.ProdutoRepository;
import com.gerenciamento.produtos.service.AuditoriaService;
import com.gerenciamento.produtos.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
class ProdutosApplicationTests {

    @Test
    void contextLoads() throws Exception {

    }

    @Autowired
    private ProdutoController produtoController;

    @Autowired
    @MockBean
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    @MockBean
    private CategoriaRepository categoriaRepository;

    @Autowired
    @MockBean
    private ProdutoAssembler produtoAssembler;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String URL = "/api/produto";

    private final Logger logger = LoggerFactory.getLogger(ProdutosApplicationTests.class);

    @Test
    public void testRestApiBuscarProdutoPorId_200() throws JsonProcessingException, Exception {
        DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        MockMvc mockMvc = defaultMockMvcBuilder.build();

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setTipo(Tipo.NORMAL);
        categoria.setNome("Acessorios");

        Produto produto = new Produto();
        produto.setNome("Produto Teste Mockado");
        produto.setSku("SKU123KLDDAA123");
        produto.setCategoria(categoria);
        produto.setId(11L);
        produto.setValorCusto(BigDecimal.valueOf(50.0));
        produto.setValorVenda(BigDecimal.valueOf(100.0));
        produto.setQuantidadeEstoque(100);
        produto.setIcms(BigDecimal.valueOf(10.0));
        produto.setDataCadastro(new Date());
        produto.setAtivo(true);

        when(produtoRepository.findById(11L)).thenReturn(Optional.of(produto));

        ResultActions retornoAPI = mockMvc.perform(get(URL + "/pesquisar/por-id/11")
                .contentType(MediaType.APPLICATION_JSON));

        retornoAPI.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(11))
                .andExpect(jsonPath("$.nome").value("Produto Teste Mockado"))
                .andExpect(jsonPath("$.sku", is("SKU123KLDDAA123")))
                .andExpect(jsonPath("$.categoria.nome", is("Acessorios")))
                .andExpect(jsonPath("$.valorCusto", is(50.0)))
                .andExpect(jsonPath("$.valorVenda", is(100.0)))
                .andExpect(jsonPath("$.quantidadeEstoque", is(100)))
                .andExpect(jsonPath("$.icms", is(10.0)))
                .andExpect(jsonPath("$.dataCadastro").exists())
                .andExpect(jsonPath("$.ativo", is(true)));

        logger.info("Logando testes da API de produtos consulta de produto por id: {}", retornoAPI.andReturn().getResponse().getContentAsString());
        verify(produtoRepository, times(1)).findById(11L);
    }

    @Test
    public void testBuscarProdutosAtivos_200() throws Exception {
        DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        MockMvc mockMvc = defaultMockMvcBuilder.build();

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setTipo(Tipo.NORMAL);
        categoria.setNome("Acessorios");

        Produto produtoAtivo = new Produto();
        produtoAtivo.setNome("Produto Ativo");
        produtoAtivo.setSku("SKU123");
        produtoAtivo.setCategoria(categoria);
        produtoAtivo.setId(1L);
        produtoAtivo.setValorCusto(BigDecimal.valueOf(50.0));
        produtoAtivo.setValorVenda(BigDecimal.valueOf(100.0));
        produtoAtivo.setQuantidadeEstoque(100);
        produtoAtivo.setIcms(BigDecimal.valueOf(10.0));
        produtoAtivo.setDataCadastro(new Date());
        produtoAtivo.setAtivo(true);

        when(produtoRepository.buscarProdutosAtivos(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(produtoAtivo)));

        ResultActions retornoAPI = mockMvc.perform(get(URL + "/ativos")
                .contentType(MediaType.APPLICATION_JSON));

        retornoAPI.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].nome").value("Produto Ativo"))
                .andExpect(jsonPath("$.content[0].sku", is("SKU123")))
                .andExpect(jsonPath("$.content[0].categoria.nome", is("Acessorios")))
                .andExpect(jsonPath("$.content[0].valorCusto", is(50.0)))
                .andExpect(jsonPath("$.content[0].valorVenda", is(100.0)))
                .andExpect(jsonPath("$.content[0].quantidadeEstoque", is(100)))
                .andExpect(jsonPath("$.content[0].icms", is(10.0)))
                .andExpect(jsonPath("$.content[0].dataCadastro").exists())
                .andExpect(jsonPath("$.content[0].ativo", is(true)));

        logger.info("Logando testes da API de produtos consulta de produto produtos ativos: {}", retornoAPI.andReturn().getResponse().getContentAsString());
        verify(produtoRepository, times(1)).buscarProdutosAtivos(any(Pageable.class));
    }


    @Test
    public void testBuscarProdutosInativos() throws Exception {
        DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        MockMvc mockMvc = defaultMockMvcBuilder.build();

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setTipo(Tipo.NORMAL);
        categoria.setNome("Acessorios");

        Produto produtoInativo = new Produto();
        produtoInativo.setNome("Produto Inativo");
        produtoInativo.setSku("SKU456");
        produtoInativo.setCategoria(categoria);
        produtoInativo.setId(2L);
        produtoInativo.setValorCusto(BigDecimal.valueOf(40.0));
        produtoInativo.setValorVenda(BigDecimal.valueOf(80.0));
        produtoInativo.setQuantidadeEstoque(50);
        produtoInativo.setIcms(BigDecimal.valueOf(8.0));
        produtoInativo.setDataCadastro(new Date());
        produtoInativo.setAtivo(false);

        when(produtoRepository.buscarProdutosInativos(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(produtoInativo)));

        ResultActions retornoAPI = mockMvc.perform(get(URL + "/inativos")
                .contentType(MediaType.APPLICATION_JSON));

        retornoAPI.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(2))
                .andExpect(jsonPath("$.content[0].nome").value("Produto Inativo"))
                .andExpect(jsonPath("$.content[0].sku", is("SKU456")))
                .andExpect(jsonPath("$.content[0].categoria.nome", is("Acessorios")))
                .andExpect(jsonPath("$.content[0].valorCusto", is(40.0)))
                .andExpect(jsonPath("$.content[0].valorVenda", is(80.0)))
                .andExpect(jsonPath("$.content[0].quantidadeEstoque", is(50)))
                .andExpect(jsonPath("$.content[0].icms", is(8.0)))
                .andExpect(jsonPath("$.content[0].dataCadastro").exists())
                .andExpect(jsonPath("$.content[0].ativo", is(false)));


        logger.info("Logando testes da API de produtos consulta de produto produtos inativos: {}", retornoAPI.andReturn().getResponse().getContentAsString());
        verify(produtoRepository, times(1)).buscarProdutosInativos(any(Pageable.class));
    }

    @Test
    public void testListarTodosProdutos_200() throws Exception {
        DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        MockMvc mockMvc = defaultMockMvcBuilder.build();

        Produto produto1 = criarProduto(1L, "Produto 1", "SKU001", BigDecimal.valueOf(20.0), 100, true);


        when(produtoRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"))))
                .thenReturn(new PageImpl<>(Arrays.asList(produto1)));

        ResultActions retornoAPI = mockMvc.perform(get(URL + "/listar/todos")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id")
                .contentType(MediaType.APPLICATION_JSON));

        retornoAPI.andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.produtos").isArray())
                .andExpect(jsonPath("$._embedded.produtos", hasSize(1)))
                .andExpect(jsonPath("$._embedded.produtos[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.produtos[0].nome", is("Produto 1")))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/produto/listar/todos?page=0&size=10&sort=id")));

        logger.info("Logando testes da API de produtos listagem de todos os produtos com HALL LINKS: {}", retornoAPI.andReturn().getResponse().getContentAsString());
        verify(produtoRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void consultarProdutoPorNome() throws Exception {
        DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        MockMvc mockMvc = defaultMockMvcBuilder.build();

        String nome = "Camisa";
        int page = 0;
        int size = 10;
        String sort = "id";

        Pageable pageable = PageRequest.of(page, size);
        Page<Produto> produtoPage = new PageImpl<>(Collections.emptyList(), pageable, 0);


        given(produtoRepository.filtrarProdutoPorNomePaginado(any(), any())).willReturn(produtoPage);
        given(produtoAssembler.toModel(any())).willReturn(new ProdutoRepresentationModel());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/produto/filtrar/por-nome")
                .queryParam("nome", nome)
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(size))
                .queryParam("sort", sort)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        System.out.println("Resultado:" + resultActions.andReturn().getResponse().getContentAsString());
    }

    private Produto criarProduto(Long id, String nome, String sku, BigDecimal valorCusto, int quantidadeEstoque, boolean ativo) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setSku(sku);
        produto.setValorCusto(valorCusto);
        produto.setQuantidadeEstoque(quantidadeEstoque);
        produto.setAtivo(ativo);
        return produto;
    }
}
