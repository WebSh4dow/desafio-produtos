package com.gerenciamento.produtos.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciamento.produtos.exception.BussinesException;
import com.gerenciamento.produtos.model.Categoria;
import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.repository.CategoriaRepository;
import com.gerenciamento.produtos.repository.ProdutoRepository;
import com.gerenciamento.produtos.repository.query.ProdutoRepositoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PersistenceContext
    private EntityManager entityManager;


    private static final String PRODUTO_INEXISTENTE = "Não existe um produto com o código %d.";

    private static final String ERRO_MENSAGEM = "Ocorreu um erro ao tentar cadastrar um novo produto ";

    private static final String CATEGORIA_PRODUTO_NAO_EXISTE = "Não existe cadastro de categoria com código %d";
    private static final String PRODUTO_SEM_CATEGORIA_ASSOCIADA = "Produto sem categoria associada.";

    private static final String NAO_PERMITIDO_SALDO_ESTOQUE_NEGATIVO = "Informe um saldo para quantidade em estoque, pois não pode ser negativo";

    public Produto cadastrar(Produto produto) {
        try {
            Categoria categoria = produto.getCategoria();

            if (categoria == null) {
                throw new BussinesException(PRODUTO_SEM_CATEGORIA_ASSOCIADA);
            }

            Long categoriaId = categoria.getId();

            Optional<Categoria> categoriaOptional = categoriaRepository.findById(categoriaId);

            if (!categoriaOptional.isPresent()) {
                throw new BussinesException(String.format(CATEGORIA_PRODUTO_NAO_EXISTE, categoriaId));
            }

            if (produto.getQuantidadeEstoque() < 0) {
                throw new IllegalArgumentException(NAO_PERMITIDO_SALDO_ESTOQUE_NEGATIVO);
            }

            calcularPrecoVenda(produto.getValorCusto(), produto.getQuantidadeEstoque(), produto);
            calcularIcmsValorSobreProduto(produto.getIcms());

            produtoRepository.save(produto);

        } catch (BussinesException | IllegalArgumentException e) {
            throw new BussinesException(ERRO_MENSAGEM + e.getMessage());
        }

        return produto;
    }

    public void mergeProduto(Map<String, Object> dadosOrigem, Produto produtoDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Produto produtoOrigem = objectMapper.convertValue(dadosOrigem, Produto.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) ->{
            Field field = ReflectionUtils.findField(Produto.class,nomePropriedade);
            field.setAccessible(true);

            Object valorPropriedadeField = ReflectionUtils.getField(field,produtoOrigem);

            ReflectionUtils.setField(field,produtoDestino,valorPropriedadeField);
        });
    }

    public void calcularPrecoVenda(BigDecimal precoCusto, Integer quantidadeProdutos, Produto produto) {
        BigDecimal valorVenda = BigDecimal.valueOf(quantidadeProdutos).multiply(precoCusto);
        produto.setValorVenda(valorVenda);
    }

    public BigDecimal calcularIcmsValorSobreProduto(BigDecimal valorTotalIcms) {
        BigDecimal valor = valorTotalIcms;
        valor = valor.multiply(dividePorCem(aliquotaTotal(valorTotalIcms)));

        return arredondar(valor);
    }

    private BigDecimal dividePorCem(BigDecimal valor) {
        return valor.divide(new BigDecimal("100"), RoundingMode.HALF_UP);
    }

    private BigDecimal arredondar(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_DOWN);
    }

    private BigDecimal aliquotaTotal(BigDecimal aliquata) {
        BigDecimal porcentagemICMS = new BigDecimal("0.10");
        return aliquata.multiply(porcentagemICMS);
    }

    public Produto buscarPor(Long produtoId) {
        Produto restaurante = produtoRepository.findById(produtoId).get();
        return restaurante;
    }

    public void remover(Produto produto) {
        try {
            produtoRepository.delete(produto);

        }catch(EmptyResultDataAccessException e) {
            throw new BussinesException(PRODUTO_INEXISTENTE);
        }
    }

    @Transactional
    public void reiniciarSequenciaProduto() {
        String sequenceName = ProdutoRepositoryQuery.SEQUENCE_PRODUTO;

        entityManager.createNativeQuery(ProdutoRepositoryQuery.NEXT_VALUE_SEQUENCE_PRODUTO)
                .setParameter(ProdutoRepositoryQuery.PARAMETER_SEQUENCE, sequenceName)
                .executeUpdate();
    }
}
