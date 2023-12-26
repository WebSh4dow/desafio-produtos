package com.gerenciamento.produtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciamento.produtos.controller.ProdutoController;
import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.repository.CategoriaRepository;
import com.gerenciamento.produtos.repository.ProdutoRepository;
import com.gerenciamento.produtos.service.AuditoriaService;
import com.gerenciamento.produtos.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class ProdutosApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private ProdutoController produtoController;

	@Autowired
	private ProdutoRepository produtoRepository;


	@Autowired
	private ProdutoService produtoService;


	@Autowired
	private AuditoriaService auditoriaService;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;


	@Autowired
	private ObjectMapper objectMapper;


	private static final String URL = "/api/produto";


	@Test
	public void testRestApiBuscarProdutoPorId() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = defaultMockMvcBuilder.build();

		Produto produto = produtoRepository.findById(11L).get();

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoAPI = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "/pesquisar/por-id/" + produto.getId())
						.content(objectMapper.writeValueAsString(produto))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200,retornoAPI.andReturn().getResponse().getStatus());

		Produto retorno = objectMapper.readValue(retornoAPI.andReturn().getResponse().getContentAsString(), Produto.class);


		System.out.println("Retorno da api na chamada da controller de [testRestApiBuscarProdutoPorId]: " + retornoAPI.andReturn().getResponse().getContentAsString());
		System.out.println("Status de retorno: " + retornoAPI.andReturn().getResponse().getStatus());

		assertEquals(produto.getNome(),retorno.getNome());

		assertEquals(produto.getId(),retorno.getId());
	}

}
