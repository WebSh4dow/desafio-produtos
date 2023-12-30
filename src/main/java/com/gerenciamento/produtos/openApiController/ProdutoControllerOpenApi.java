package com.gerenciamento.produtos.openApiController;


import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.model.representation.ProdutoRepresentationModel;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import java.util.Date;
import java.util.Map;

@Validated
@Api(tags = "Produtos")
public interface ProdutoControllerOpenApi {

    @ApiOperation(value = "Busca por produtos ativos com paginação")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca por produtos ativos foi retornada com sucesso!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<Page<Produto>> buscarAtivos(Pageable pageable);

    @ApiOperation(value = "Busca por produtos inativos com paginação")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca por produtos inativos foi retornada com sucesso!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<Page<Produto>> buscarInativos(Pageable pageable);

    @ApiOperation(value = "Busca todos os produtos usando paginação dinamica com halllinks")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca por produtos foi retornada com sucesso!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<CollectionModel<ProdutoRepresentationModel>> listarTodosProdutos(int page, int size, String sort);

    @ApiOperation(value = "Busca determinados produtos por id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca de produtos por id foi retornada com sucesso!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<Produto> buscarProdutoPorId(Long id);

    @ApiOperation(value = "Busca determinados produtos por nome")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca de produtos por nome foi retornada com sucesso!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<CollectionModel<ProdutoRepresentationModel>> consultarProdutoPorNome(String nome, int page, int size, String sort);

    @ApiOperation(value = "Busca determinados produtos por nome e categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca de produtos por nome e categoria foi retornada com sucesso!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<CollectionModel<ProdutoRepresentationModel>> consultarPorNomeECategoria(String nome, String categoria, int page, int size, String sort);

    @ApiOperation(value = "Busca por multiplos atributos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca de produtos por multiplos atributos foi retornada com sucesso!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<CollectionModel<ProdutoRepresentationModel>> consultarPorMultiplosAtributos(String nome, String categoria, Date dataCadastro, int page, int size, String sort);

    @ApiOperation(value = "Cadastro de produtos")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cadastrado de produtos efetuado com sucesso!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<?> cadastrar(Produto produto);

    @ApiOperation(value = "Edição de produtos")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Edição de produtos efetuada com sucesso!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<?> atualizar(Produto produto, Long produtoId);

    @ApiOperation(value = "Edição de multiplos campos, podendo editar status de ativo para inativo de determinado produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Edição parcial efetuada com sucesso!"),
            @ApiResponse(code = 204, message = "Não possui conteudo"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<?> editarCamposProdutos(Long produtoId, Map<String, Object> campos);

    @ApiOperation(value = "Remoção de produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Remoção de produto efetuada com sucesso!"),
            @ApiResponse(code = 401, message = "Credenciais inválidas"),
            @ApiResponse(code = 403, message = "Não possui Permissão para acesso"),
            @ApiResponse(code = 404, message = "Nenhuma registro foi encontrado"),
            @ApiResponse(code = 204, message = "Não possui conteudo"),
            @ApiResponse(code = 400, message = "Erro de requisição"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    ResponseEntity<?> remover(Long produtoId);

}
