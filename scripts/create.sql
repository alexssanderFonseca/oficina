CREATE TABLE endereco
(
    id     UUID PRIMARY KEY,
    rua    VARCHAR(255) NOT NULL,
    bairro VARCHAR(255) NOT NULL,
    cep    VARCHAR(20)  NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    uf     VARCHAR(2)   NOT NULL
);

CREATE TABLE cliente
(
    id          UUID PRIMARY KEY,
    nome        VARCHAR(255)       NOT NULL,
    sobrenome   VARCHAR(255)       NOT NULL,
    cpfCnpj     VARCHAR(14) UNIQUE NOT NULL,
    email       VARCHAR(255),
    telefone    VARCHAR(20),
    endereco_id UUID UNIQUE,
    CONSTRAINT fk_endereco
        FOREIGN KEY (endereco_id)
            REFERENCES endereco (id)
            ON DELETE CASCADE
);


CREATE TABLE veiculo
(
    id         UUID PRIMARY KEY,
    placa      VARCHAR(7) NOT NULL,
    marca      VARCHAR(30) NOT NULL,
    modelo     VARCHAR(30) NOT NULL,
    cor        varchar(30) NOT NULL,
    ano        VARCHAR(4)  NOT NULL,
    cliente_id UUID UNIQUE,
    CONSTRAINT fk_cliente
        FOREIGN KEY (cliente_id)
            REFERENCES cliente (id)
            ON DELETE CASCADE
);


CREATE TABLE peca (
                      id UUID PRIMARY KEY,
                      nome VARCHAR(100) NOT NULL,
                      descricao TEXT,
                      codigo_fabricante VARCHAR(100),
                      sku VARCHAR(100) UNIQUE,
                      marca VARCHAR(100),
                      modelos_compativeis TEXT,
                      quantidade_estoque INTEGER,
                      preco_custo NUMERIC(12, 2),
                      preco_venda NUMERIC(12, 2),
                      categoria VARCHAR(100),
                      ativo BOOLEAN DEFAULT TRUE,
                      data_cadastro TIMESTAMP,
                      data_atualizacao TIMESTAMP
);

CREATE TABLE servico (
                         id UUID PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         descricao TEXT,
                         preco NUMERIC(10,2) NOT NULL,
                         duracao_estimada INTEGER,
                         categoria VARCHAR(50),
                         ativo BOOLEAN DEFAULT TRUE,
                         data_cadastro TIMESTAMP NOT NULL,
                         data_atualizacao TIMESTAMP
);


CREATE TABLE veiculo_modelo (
                                id UUID PRIMARY KEY,
                                marca VARCHAR(50) NOT NULL,
                                modelo VARCHAR(50) NOT NULL,
                                ano_inicio INTEGER,
                                ano_fim INTEGER,
                                tipo VARCHAR(30)
);


CREATE TABLE peca_modelo_veiculo (
                                     peca_id UUID NOT NULL,
                                     modelo_id UUID NOT NULL,
                                     PRIMARY KEY (peca_id, modelo_id),
                                     CONSTRAINT fk_peca FOREIGN KEY (peca_id) REFERENCES peca(id),
                                     CONSTRAINT fk_modelo FOREIGN KEY (modelo_id) REFERENCES veiculo_modelo(id)
);
