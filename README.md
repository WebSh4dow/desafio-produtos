# API de Gerenciamento de Produtos [Desafio Tecnico]

## Estrutura do Produto

O produto contém os seguintes dados:
- **ID**: Identificador único do produto.
- **Nome**: Nome descritivo do produto.
- **Ativo**: Indica se o produto está ativo.
- **SKU**: Código de identificação do produto.
- **Categoria**: Categoria à qual o produto pertence.
- **Valor de Custo**: Custo de aquisição do produto.
- **ICMS**: Imposto sobre Circulação de Mercadorias e Serviços.
- **Valor de Venda**: Preço de venda do produto.
- **Quantidade em Estoque**: Quantidade disponível em estoque.
- **Data de Cadastro**: Data em que o produto foi cadastrado.

### Estrutura da Categoria

A categoria contém os seguintes dados:
- **ID**: Identificador único da categoria.
- **Nome**: Nome descritivo da categoria.
- **Ativo**: Indica se a categoria está ativa.
- **Tipo**: Tipo da categoria (Normal, Especial, Personalizado).

## Funcionalidades

### 1. Operações CRUD em Produto

Realizar as operações básicas de criação, leitura, atualização e exclusão de produtos.


1. Deve ser possível realizar as operações CRUD em um produto (Os cadastros de categoria e usuário podem ser feitas diretamente no banco, sem necessidade de CRUD específico). - ☑

2. O usuário pode inativar um produto ou excluir permanentemente. - ☑

3. Os produtos devem ser listados com paginação, com possibilidade de escolher a quantidade de itens por página e ordenação de todos os campos. - ☑

4. A listagem deve permitir filtrar pelo usuário que cadastrou o produto. - ☑

5. A listagem deve permitir filtrar por múltiplos campos. Exemplo: filtrar por Nome, Nome e Categoria, Nome e Categoria e Data de cadastro. Deve ser possível filtrar por qualquer um dos campos. - ☑

6. O sistema deve permitir que o administrador crie uma regra para quais campos não serão exibidos para o estoquista. Exemplo: o administrador não quer exibir ICMS e Custo. O administrador pode alterar essa regra para os campos exibidos a qualquer momento. - ☑

7. O sistema deve ter um endpoint que liste os valores agregados dos produtos. Exemplo: Produto A - Custo: R$ 1,00 - Custo Total: R$ 10,00 - Quantidade: 10 - Valor Total Previsto: R$ 20,00. O endpoint deve filtrar da mesma forma que os itens 4 e 5. - ☑

8. O sistema deve emitir um relatório dos produtos em formato CSV ou XLSX (o usuário deve escolher qual formato), podendo filtrar os dados da mesma forma que os itens 4 e 5. O usuário, no momento da geração do relatório, poderá escolher quais campos do produto deseja exibir no relatório. Exemplo: todos, apenas ID e nome e SKU e etc. - ☑

9. O sistema deve registrar auditoria de todos os eventos realizados (criação, atualização, exclusão e etc.), registrando as seguintes informações: objeto alterado, ação realizada (inclusão, alteração, exclusão), data/hora e usuário que realizou a alteração. Deve ser possível detalhar a auditoria, mostrando o campo alterado, valor anterior e valor atual. Exemplo: Auditoria: ID 39 - Produto - Inclusão - 10/10/2023 12:25 - Jose Carlos ID 40 - Produto - Alteração - 10/10/2023 12:30 - João da Silva Detalhamento auditoria ID 40: Campo: Nome - Valor anterior: Caixa SP - Valor atual: Caixa GR SP - ☑

10. O usuário estoquista, na atualização de um produto, não deve alterar o Valor de Custo e ICMS. - ☑

11. Documente a API com Swagger. -☑

# Autenticação e Autorização

- A API deve utilizar a estratégia de autenticação JWT. - ☑

- Deve utilizar o conceito de refresh token, com um tempo de 5 minutos. - ☑

- O sistema deve ter dois níveis de acesso: administrador e estoquista. - ☑

- O sistema deve permitir que o administrador crie uma regra para quais campos não serão exibidos para o estoquista. Exemplo: o administrador não quer exibir ICMS e Custo. O administrador pode alterar essa regra para os campos exibidos a qualquer momento. - ☑

- OBS:
- para a ultima regra da visibilidade de campos para o estoquista, foi realizado uma implementação para ofuscar o valor dos respectivos campos então quando o estoquista tentar acessar ou atualizar não será possivel atualizar esses campos o usuário do tipo estoquista que não possuia a devida permissão de visibilidade, os valores serão marcados como zerado por questões de implementação e devido ao tempo / viabilidade do desafio. Então foi implementado dessa forma para evitar possiveis problemas de null e etc. 

7. Swagger da API. - ☑

### Resource de Produtos

**Exemplo de Requisição (POST):**
```json
POST /api/produto/cadastrar/novo-produto

{
  "nome": "Camiseta Gola Alta",
  "sku": "SKU12dasd213",
  "categoria": {
    "id": 1,
    "nome": "Artigos e coleções",
    "ativo": true,
    "tipo":"PERSONALIZADO"
  },
  "valorCusto": 50.00,
  "valorVenda": 100.00,
  "quantidadeEstoque": 10,
  "icms": 10.00,
  "dataCadastro": "2023-12-25",
  "ativo": true
}
```

**Exemplo de Requisição (PUT):**
```json
PUT /api/produto/atualizar/produto-existente/1

{
  "nome": "Camiseta Gola Alta",
  "sku": "SKU12dasd213",
  "categoria": {
    "id": 1,
    "nome": "Artigos e coleções",
    "ativo": true,
    "tipo":"PERSONALIZADO"
  },
  "valorCusto": 50.00,
  "valorVenda": 100.00,
  "quantidadeEstoque": 10,
  "icms": 10.00,
  "dataCadastro": "2023-12-25",
  "ativo": true
}
```


**Exemplo de Requisição Filtrar por Nome Paginado e usando Hateos HiperMedia (GET):**
```json

GET /api/produto/filtrar/por-nome?nome=C

{
    "_embedded": {
        "produtos": [
            {
                "id": 1,
                "nome": "Camiseta Gola Alta",
                "sku": "SKU12dasd213",
                "categoria": {
                    "id": 1,
                    "nome": "Acessorios",
                    "ativo": true,
                    "tipo": "PERSONALIZADO"
                },
                "valorCusto": 50.00,
                "valorVenda": 500.00,
                "quantidadeEstoque": 10,
                "icms": 10.00,
                "dataCadastro": "2023-12-24",
                "ativo": true,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/produto/1"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/produto/filtrar/por-nome?nome=C&page=0&size=10&sort=id"
        }
    }
}
```


**Exemplo de Requisição de buscar produtos ativos e usando Hateos HiperMedia (GET):**
```json

GET /api/produto/ativos

{
    "_embedded": {
        "produtos": [
            {
                "id": 1,
                "nome": "Camiseta Gola Alta",
                "sku": "SKU12dasd213",
                "categoria": {
                    "id": 1,
                    "nome": "Acessorios",
                    "ativo": true,
                    "tipo": "PERSONALIZADO"
                },
                "valorCusto": 50.00,
                "valorVenda": 500.00,
                "quantidadeEstoque": 10,
                "icms": 10.00,
                "dataCadastro": "2023-12-24",
                "ativo": true,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/produto/1"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/produto/ativos"
        }
    }
}
```




**Exemplo de Requisição de buscar produtos inativos e usando Hateos HiperMedia (GET):**
```json

GET /api/produto/inativos

{
    "_embedded": {
        "produtos": [
            {
                "id": 1,
                "nome": "Camiseta Gola Alta",
                "sku": "SKU12dasd213",
                "categoria": {
                    "id": 1,
                    "nome": "Acessorios",
                    "ativo": true,
                    "tipo": "PERSONALIZADO"
                },
                "valorCusto": 50.00,
                "valorVenda": 500.00,
                "quantidadeEstoque": 10,
                "icms": 10.00,
                "dataCadastro": "2023-12-24",
                "ativo": false,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/produto/1"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/produto/ativos"
        }
    }
}
```





**Exemplo de Requisição de listar todos os produtos e usando Hateos HiperMedia (GET):**
```json

GET /api/produto/listar/todos?asc=1

{
    "_embedded": {
        "produtos": [
            {
                "id": 1,
                "nome": "Camiseta Gola Alta",
                "sku": "SKU12dasd213",
                "categoria": {
                    "id": 1,
                    "nome": "Acessorios",
                    "ativo": true,
                    "tipo": "PERSONALIZADO"
                },
                "valorCusto": 50.00,
                "valorVenda": 500.00,
                "quantidadeEstoque": 10,
                "icms": 10.00,
                "dataCadastro": "2023-12-24",
                "ativo": true,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/produto/1"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/produto/listar/todos?page=0&size=10&sort=id"
        }
    }
}
```


**Exemplo de Requisição de valores agregados aos produtos e usando Hateos HiperMedia (GET):**
```json

GET /api/produto/valores-agregados

{
    "_embedded": {
        "produtosAgragados": [
            {
                "id": 1,
                "nome": "Camiseta Gola Alta",
                "custoTotal": 50.00,
                "quantidade": 10,
                "valorTotalPrevisto": 500.00
            }
        ]
    }
}
```




**Exemplo de Requisição de produtos por nome ou categoria e usando Hateos HiperMedia (GET):**
```json

GET /api/produto/filtrar/por-nome-e-categoria?nome=CA&categoria=PERSONALIZADO

{
    "_embedded": {
        "produtos": [
            {
                "id": 1,
                "nome": "Camiseta Gola Alta",
                "sku": "SKU12dasd213",
                "categoria": {
                    "id": 1,
                    "nome": "Acessorios",
                    "ativo": true,
                    "tipo": "PERSONALIZADO"
                },
                "valorCusto": 50.00,
                "valorVenda": 500.00,
                "quantidadeEstoque": 10,
                "icms": 10.00,
                "dataCadastro": "2023-12-24",
                "ativo": true,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/produto/1"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/produto/filtrar/por-nome-e-categoria?nome=CA&categoria=PERSONALIZADO&page=0&size=10&sort=id"
        }
    }
}
```


**Exemplo de Requisição de produtos por id e usando Hateos HiperMedia (GET):**
```json

GET /api/produto/1

{
    "_embedded": {
        "produtos": [
            {
                "id": 1,
                "nome": "Camiseta Gola Alta",
                "sku": "SKU12dasd213",
                "categoria": {
                    "id": 1,
                    "nome": "Acessorios",
                    "ativo": true,
                    "tipo": "PERSONALIZADO"
                },
                "valorCusto": 50.00,
                "valorVenda": 500.00,
                "quantidadeEstoque": 10,
                "icms": 10.00,
                "dataCadastro": "2023-12-24",
                "ativo": true,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/produto/1"
                    }
                }
            }
        ]
    }
}
```


**Exemplo de Requisição de produtos por multiplos campos usando Hateos HiperMedia (GET):**
```json

GET /api/produto/filtrar/por-multiplos-atributos?nome=CA&categoria=PERSO&dataCadastro=2023-12-12

{
    "_embedded": {
        "produtos": [
            {
                "id": 1,
                "nome": "Camiseta Gola Alta",
                "sku": "SKU12dasd213",
                "categoria": {
                    "id": 1,
                    "nome": "Acessorios",
                    "ativo": true,
                    "tipo": "PERSONALIZADO"
                },
                "valorCusto": 50.00,
                "valorVenda": 500.00,
                "quantidadeEstoque": 10,
                "icms": 10.00,
                "dataCadastro": "2023-12-24",
                "ativo": true,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/produto/1"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/produto/filtrar/por-multiplos-atributos?nome=CA&categoria=PERSO&dataCadastro=2023-12-12&page=0&size=10&sort=id"
        }
    }
}
```


**Exemplo de Requisição de ativação / inativação de produtos bem como outros campos individuais do produto usando também Hateos HiperMedia (PATCH):**
```json

PATCH  /api/produto/editar-campos/1
{
    "ativo": false
}

```


### Resource de Autenticação:

- Obs: Para facilitar ja existe um exemplo de autenticação no swagger implementado basta fazer a requisição com o exemplo já pronto, caso tenha registrado o acesso previamente via sql basta usar o exemplo dentro do swagger e copiar o token de autorização,
para o postman ou apenas autenticar no botão de autenticação informando o prefixo Bearer <token> facilitando ainda mais a autenticação, pois foi criado um endpoint para criação deste usuário bem como seu respectivo token, refresh token e etc.


**Exemplo de Requisição de signup (POST):**
```json

POST /api/auth/signup

{
  "login": "test@gmail.com",
  "senha": "12345678jarmisonpaiva"
}

Resposta: Usuario com o nome de test@gmail.com Foi Cadastrado com sucesso!

```


**Exemplo de Requisição de signup (POST):**
```json

POST /api/auth/signup

{
  "login": "test@gmail.com",
  "senha": "12345678jarmisonpaiva"
}

Resposta: Usuario com o nome de test@gmail.com Foi Cadastrado com sucesso!

```


**Exemplo de Requisição de signin Autenticação do usuário na API (POST):**
```json

POST /api/auth/signin

{
  "login": "test@gmail.com",
  "senha": "12345678jarmisonpaiva"
}

Resposta:

{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXJtaXNvbkBnbWFpbC5jb20iLCJpYXQiOjE3MDQyODUzNjMsImV4cCI6MTcwNTI0NTM1M30.YKcJnfm3GxVnNqnj04nBRZf8ymXbX2MvcQMiPhNsdDDqGnFEvHSyqivRwlu65mXrMLvuI0PpUqJAJKH0Y2nFCQ",
    "type": "Bearer",
    "refreshToken": "d8014447-c326-4556-a0d4-da2b2e5e6a91",
    "id": 2,
    "login": "test@gmail.com",
    "acessos": [
        {
            "id": 3,
            "descricao": "NORMAL"
        }
    ]
}

```

**Exemplo de Requisição de atualização de refresh token caso expire Autenticação do usuário na API (POST):**
```json

POST /api/auth/refreshtoken

{
  "refreshToken": "d8014447-c326-4556-a0d4-da2b2e5e6a91"
}

=
```


### Relatório de Produtos

- Por padrão será gera o relatório de produtos com o tipo de arquivo CSV, podendendo assim pesquisar por multiplos campos como id, nome do produto ou o sku deve ser informado via query parameter o id, nome, sku e formato CSV ou XLSX.
- No postman basta enviar a requisição e fazer o download do arquivo ao efetuar o download via postman clicando na seta ao lado do botão de send e escolher send and download irá efetuar o download do arquivo também.

**Exemplo de Requisição de Relatório de produtos na API (GET):**
```json

GET  /api/relatorio/gerar-relatorio?id=1

id,nome,sku
1,Camiseta Gola Alta,SKU12dasd213

```


### Auditoria de Produtos

- Endpoint responsavel por registrar qualquer anteração feita pelo usuário cadastrando o id da auditoria, objeto aletrado caso for consultas por padrão sera setado a mensagem Nenhum Objeto a ser alterado porém
- para endpoints que efetuam transações como POST, PUT, DELETE e PATCH serão registrado com o respectivo objeto que foi alterado pelo usuário.

**Exemplo de Requisição de Auditoria de produtos na API Paginação Normal do Spring (GET):**
```json

GET /api/auditoria/listar

{
    "content": [
        {
            "id": 1,
            "objetoAlterado": "Nenhum Objeto a ser alterado",
            "acaoRealizada": "CONSULTAR PRODUTOS POR MULTIPLOS CAMPOS",
            "dataHora": "2024-01-03T12:04:59.204+00:00",
            "usuario": "Francisco Jarmison"
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageSize": 10,
        "pageNumber": 0,
        "paged": true,
        "unpaged": false
    },
    "totalElements": 1,
    "totalPages": 1,
    "last": true,
    "size": 10,
    "number": 0,
    "first": true,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 1,
    "empty": false
}

```


**Exemplo de Requisição de Auditoria de produtos detalhada na API (GET):**
```json

GET /api/auditoria/detalhar/1

{
    "id": 1,
    "objetoAlterado": "Nenhum Objeto a ser alterado",
    "acaoRealizada": "CONSULTAR PRODUTOS POR MULTIPLOS CAMPOS",
    "dataHora": "2024-01-03T12:04:59.204+00:00",
    "usuario": "Francisco Jarmison"
}
```






