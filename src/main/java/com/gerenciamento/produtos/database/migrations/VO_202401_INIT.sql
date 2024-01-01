-- public.acesso definition

-- Drop table

-- DROP TABLE public.acesso;

CREATE TABLE public.acesso (
	id int8 NOT NULL,
	descricao varchar(255) NOT NULL,
	CONSTRAINT acesso_pkey PRIMARY KEY (id)
);


-- public.auditoria definition

-- Drop table

-- DROP TABLE public.auditoria;

CREATE TABLE public.auditoria (
	id int8 NOT NULL,
	acao_realizada varchar(255) NOT NULL,
	data_hora timestamp NOT NULL,
	objeto_alterado varchar(255) NOT NULL,
	usuario varchar(255) NOT NULL,
	CONSTRAINT auditoria_pkey PRIMARY KEY (id)
);


-- public.categoria definition

-- Drop table

-- DROP TABLE public.categoria;

CREATE TABLE public.categoria (
	id int8 NOT NULL,
	ativo bool NOT NULL,
	nome varchar(255) NULL,
	tipo varchar(255) NULL,
	CONSTRAINT categoria_pkey PRIMARY KEY (id)
);


-- public.detalhe_auditoria definition

-- Drop table

-- DROP TABLE public.detalhe_auditoria;

CREATE TABLE public.detalhe_auditoria (
	id int8 NOT NULL,
	campo varchar(255) NULL,
	objeto_alterado varchar(255) NULL,
	objeto_id int8 NULL,
	valor_anterior varchar(1000) NULL,
	valor_atual varchar(1000) NULL,
	CONSTRAINT detalhe_auditoria_pkey PRIMARY KEY (id)
);


-- public.usuario definition

-- Drop table

-- DROP TABLE public.usuario;

CREATE TABLE public.usuario (
	id int8 NOT NULL,
	login varchar(255) NOT NULL,
	senha varchar(255) NOT NULL,
	CONSTRAINT uk_pm3f4m4fqv89oeeeac4tbe2f4 UNIQUE (login),
	CONSTRAINT usuario_pkey PRIMARY KEY (id)
);


-- public.produto definition

-- Drop table

-- DROP TABLE public.produto;

CREATE TABLE public.produto (
	id int8 NOT NULL,
	ativo bool NOT NULL,
	data_cadastro date NOT NULL,
	icms varchar(255) NULL,
	nome varchar(255) NULL,
	quantidade_estoque int4 NULL,
	sku varchar(255) NULL,
	valor_custo numeric(19, 2) NULL,
	valor_venda numeric(19, 2) NULL,
	categoria_id int8 NULL,
	quantidadeestoque int4 NULL,
	valorcusto numeric(19, 2) NULL,
	valorvenda numeric(19, 2) NULL,
	created_by varchar(255) NULL,
	created_date timestamp NULL,
	last_modified_by varchar(255) NULL,
	last_modified_date timestamp NULL,
	criado_por varchar(255) NULL,
	data_criacao_audit timestamp NULL,
	data_ultima_modificacao timestamp NULL,
	ultimo_usuario_modificacao varchar(255) NULL,
	CONSTRAINT produto_pkey PRIMARY KEY (id),
	CONSTRAINT categoria_fk FOREIGN KEY (categoria_id) REFERENCES public.categoria(id)
);


-- public.refreshtoken definition

-- Drop table

-- DROP TABLE public.refreshtoken;

CREATE TABLE public.refreshtoken (
	id int8 NOT NULL,
	data_expiracao timestamp NOT NULL,
	"token" varchar(255) NOT NULL,
	usuario_id int8 NULL,
	CONSTRAINT refreshtoken_pkey PRIMARY KEY (id),
	CONSTRAINT uk_or156wbneyk8noo4jstv55ii3 UNIQUE (token),
	CONSTRAINT fkirvy67g3yktbria1isj4w17oc FOREIGN KEY (usuario_id) REFERENCES public.usuario(id)
);


-- public.usuarios_acesso definition

-- Drop table

-- DROP TABLE public.usuarios_acesso;

CREATE TABLE public.usuarios_acesso (
	usuario_id int8 NOT NULL,
	acesso_id int8 NOT NULL,
	CONSTRAINT acesso_fk FOREIGN KEY (acesso_id) REFERENCES public.acesso(id),
	CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES public.usuario(id)
);


-- public.parametro_configuracao definition

-- Drop table

-- DROP TABLE public.parametro_configuracao;

CREATE TABLE public.parametro_configuracao (
	id int8 NOT NULL,
	campo_nome varchar(255) NULL,
	visivel bool NOT NULL,
	acesso_id int8 NULL,
	produto_id int8 NULL,
	CONSTRAINT parametro_configuracao_pkey PRIMARY KEY (id),
	CONSTRAINT fk4041u3nl3x0jnf6fnb0xwaipg FOREIGN KEY (acesso_id) REFERENCES public.acesso(id),
	CONSTRAINT fko2r7dm6ibrusfwyobbfv8ryj1 FOREIGN KEY (produto_id) REFERENCES public.produto(id)
);