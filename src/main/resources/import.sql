
--- Script de insert inicial da tabela de categoria será executado automaticamente ao inicializar a api

INSERT INTO categoria (id, ativo, nome, tipo) VALUES(1, true, 'Acessorios', 'PERSONALIZADO');

INSERT INTO categoria (id, ativo, nome, tipo) VALUES(2, true, 'Diversos', 'ESPECIAL');

INSERT INTO categoria (id, ativo, nome, tipo) VALUES(3, true, 'Artigo de Luxo', 'NORMAL');


--- Script de insert inicial da tabela de acesso será executado automaticamente ao inicializar a api


INSERT INTO acesso (id, descricao) VALUES (1,'NORMAL');

INSERT INTO acesso (id, descricao) VALUES (2,'ESTOQUISTA');

INSERT INTO acesso (id, descricao) VALUES (3,'ADMINISTRADOR');