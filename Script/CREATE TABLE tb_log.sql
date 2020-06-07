CREATE TABLE tb_log (
	id_log bigserial NOT NULL,
	texto_log bytea NOT NULL,
	nome_log varchar(255) NOT NULL,
	id_dados_log int8 NULL,
	CONSTRAINT tb_log_pkey PRIMARY KEY (id_log),
	CONSTRAINT fkr0q2uyfru4wwl95u70i561je6 FOREIGN KEY (id_dados_log) REFERENCES tb_dados_log(id_dados_log)
);