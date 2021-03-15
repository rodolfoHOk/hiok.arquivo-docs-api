-- codigo sql do exemplo_jasper para usar o PostgreSQL ao invés do mySQL
-- apaga tudo
DROP SCHEMA arquivojm CASCADE;

-- Cria o schema
CREATE SCHEMA arquivojm;

-- Cria a tabela de cliente
CREATE  TABLE arquivojm.cliente (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(45) NOT NULL,
  cnpj VARCHAR(45),
  endereco VARCHAR(45)
);

-- Cria a tabela de caixa
CREATE  TABLE arquivojm.caixa (
  id SERIAL PRIMARY KEY,
  cliente INT NOT NULL
);
	
ALTER TABLE arquivojm.caixa
	ADD CONSTRAINT fk_cliente
	FOREIGN KEY (cliente) REFERENCES arquivojm.cliente(id);

-- Cria a tabela de tipos de documento:
CREATE  TABLE arquivojm.tipo_documento (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(45) NOT NULL
);

-- Cria a tabela de documento:
CREATE  TABLE arquivojm.documento (
  id SERIAL PRIMARY KEY,
  caixa INT NOT NULL,
  tipo_documento INT NOT NULL,
  cliente INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  dt_entrada DATE,
  observacao VARCHAR(45)
);
	
ALTER TABLE arquivojm.documento
	ADD CONSTRAINT fk_caixa
	FOREIGN KEY (caixa) REFERENCES arquivojm.caixa(id),
	ADD CONSTRAINT fk_tipo_documento
	FOREIGN KEY (tipo_documento) REFERENCES arquivojm.tipo_documento(id),
	ADD CONSTRAINT fk_cliente
	FOREIGN KEY (cliente) REFERENCES arquivojm.cliente(id);
	
-- adicionando dados

INSERT INTO arquivojm.cliente VALUES(DEFAULT, 'Empresa A João', '90405905000134', 'SP');
INSERT INTO arquivojm.cliente VALUES(DEFAULT, 'Empresa B Maria', '90617318000109', 'RJ');
INSERT INTO arquivojm.cliente VALUES(DEFAULT, 'Empresa C José', '62413250000100', 'MG');

INSERT INTO arquivojm.caixa VALUES(DEFAULT, 2);
INSERT INTO arquivojm.caixa VALUES(DEFAULT, 1);
INSERT INTO arquivojm.caixa VALUES(DEFAULT, 3);

INSERT INTO arquivojm.tipo_documento VALUES(DEFAULT, 'nota fiscal');
INSERT INTO arquivojm.tipo_documento VALUES(DEFAULT, 'recibo');

INSERT INTO arquivojm.documento VALUES(DEFAULT, 2, 1, 1, 'nota JaA1', '2020-01-01', 'joao C2A1');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 1, 1, 2, 'nota MrA1', '2020-01-10', 'maria C1A1');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 3, 1, 3, 'nota JsA1', '2020-01-20', 'jose C3A1');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 3, 2, 3, 'recibo JsB1', '2020-02-05', 'jose C3B1');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 1, 2, 2, 'recibo MrB1', '2020-02-15', 'maria C1B1');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 2, 1, 1, 'nota JaA2', '2020-02-15', 'joao C2A2');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 2, 2, 1, 'recibo JaB1', '2020-02-21', 'joao C2B1');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 3, 1, 3, 'nota JsA2', '2020-02-21', 'jose C3A2');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 3, 2, 3, 'recibo JsB2', '2020-02-23', 'jose C3B2');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 3, 1, 3, 'nota JsA3', '2020-02-23', 'jose C3A3');

INSERT INTO arquivojm.cliente VALUES(DEFAULT, 'Empresa D Ana', '44134566000181', 'SP');
INSERT INTO arquivojm.cliente VALUES(DEFAULT, 'Empresa E Beatriz', '52259723000103', 'RJ');

INSERT INTO arquivojm.caixa VALUES(DEFAULT, 3);
INSERT INTO arquivojm.caixa VALUES(DEFAULT, 5);
INSERT INTO arquivojm.caixa VALUES(DEFAULT, 4);

INSERT INTO arquivojm.documento VALUES(DEFAULT, 6, 1, 4, 'nota JaA1', '2020-01-01', 'Ana C6A1');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 5, 1, 5, 'nota MrA1', '2020-01-10', 'Beatriz C5A1');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 4, 1, 3, 'nota JsA1', '2020-01-20', 'jose C4A1');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 4, 2, 3, 'recibo JsB1', '2020-02-05', 'jose C4B1');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 5, 2, 5, 'recibo MrB1', '2020-02-15', 'Beatriz C5B1');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 6, 1, 4, 'nota JaA2', '2020-02-15', 'Ana C6A2');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 6, 2, 4, 'recibo JaB1', '2020-02-21', 'Ana C6B1');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 4, 1, 3, 'nota JsA2', '2020-02-21', 'jose C4A2');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 4, 2, 3, 'recibo JsB2', '2020-02-23', 'jose C4B2');
INSERT INTO arquivojm.documento VALUES(DEFAULT, 4, 1, 3, 'nota JsA3', '2020-02-23', 'jose C4A3');

SELECT * from arquivojm.caixa;
SELECT * from arquivojm.tipo_documento;
SELECT * from arquivojm.cliente;
SELECT * from arquivojm.documento;

-- testando do sql dado no exemplo:

SELECT caixa.id CD_CAIXA,
	cliente.id CD_CLIENTE,
	cliente.nome NM_CLIENTE,
	cliente.cnpj CNPJ,
	tipo.nome TIPO,
	documento.nome NM_DOCUMENTO,
	documento.observacao OBSERVACAO,
	documento.dt_entrada DT_ENTRADA
FROM arquivojm.caixa caixa,
	arquivojm.cliente cliente,
	arquivojm.documento documento,
	arquivojm.tipo_documento tipo
WHERE 
	 caixa.cliente = cliente.id 
	 AND documento.cliente = caixa.cliente 
	 AND documento.caixa = caixa.id
	 AND tipo.id = documento.tipo_documento 
	 AND cliente.id BETWEEN 1 AND 3 
ORDER BY caixa.id DESC;

-- melhorando o sql:

SELECT
	caixa.id AS CD_CAIXA,
	cliente.id AS CD_CLIENTE,
	cliente.nome AS NM_CLIENTE,
	cliente.cnpj AS CNPJ,
	tipo.nome AS TIPO,
	doc.nome AS NM_DOCUMENTO,
	doc.observacao AS OBSERVACAO,
	doc.dt_entrada AS DT_ENTRADA
FROM arquivojm.documento doc
INNER JOIN arquivojm.caixa caixa
ON doc.caixa = caixa.id
INNER JOIN arquivojm.cliente cliente
ON doc.cliente = cliente.id
INNER JOIN arquivojm.tipo_documento tipo
On doc.tipo_documento = tipo.id
WHERE cliente.id BETWEEN 1 AND 5
ORDER BY caixa.id DESC;

-- 	teste sql do grafico:

SELECT c.nome, COUNT(*)
FROM arquivojm.cliente c
INNER JOIN arquivojm.documento d
ON c.id = d.cliente
WHERE c.id BETWEEN 1 AND 5
GROUP BY c.nome;