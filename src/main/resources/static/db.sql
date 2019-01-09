CREATE TABLE public.membro (
	usuario varchar NOT NULL,
	senha varchar NOT NULL,
	nome varchar NOT NULL,
	habilitado bool,
	tipo varchar,
	CONSTRAINT membro_pkey PRIMARY KEY (usuario)
);

CREATE TABLE public.ponto (
	id SERIAL PRIMARY KEY,
	usuario varchar,
	dia varchar,
	inicio varchar,
	fim varchar,
	CONSTRAINT ponto_usuario_fkey FOREIGN KEY (usuario) REFERENCES membro (usuario)
);