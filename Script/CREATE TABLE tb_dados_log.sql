CREATE TABLE tb_dados_log (
	id_dados_log bigserial NOT NULL,
	dt_insercao timestamp NOT NULL,
	ip_insercao varchar(255) NOT NULL,
	user_agent varchar(255) NOT NULL,
	CONSTRAINT tb_dados_log_pkey PRIMARY KEY (id_dados_log)
);