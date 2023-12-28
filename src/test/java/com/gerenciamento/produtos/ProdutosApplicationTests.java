package com.gerenciamento.produtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciamento.produtos.controller.ProdutoController;
import com.gerenciamento.produtos.enums.Tipo;
import com.gerenciamento.produtos.model.Categoria;
import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.repository.CategoriaRepository;
import com.gerenciamento.produtos.repository.ProdutoRepository;
import com.gerenciamento.produtos.service.AuditoriaService;
import com.gerenciamento.produtos.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
class ProdutosApplicationTests {

	@Test
	void contextLoads() {
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
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String URL = "/api/produto";

	private final Logger logger = LoggerFactory.getLogger(ProdutosApplicationTests.class);

	@Test
	public void testRestApiBuscarProdutoPorId() throws JsonProcessingException, Exception {
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


}
