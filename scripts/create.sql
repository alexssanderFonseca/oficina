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
    cpf_cnpj     VARCHAR(14) UNIQUE NOT NULL,
    email       VARCHAR(255),
    telefone    VARCHAR(20),
    endereco_id UUID UNIQUE,
    CONSTRAINT fk_endereco
        FOREIGN KEY (endereco_id)
            REFERENCES endereco (id)
            ON DELETE CASCADE
);


CREATE TABLE veiculo_modelo (
                                id BIGSERIAL PRIMARY KEY,
                                marca VARCHAR(50) NOT NULL,
                                modelo VARCHAR(50) NOT NULL,
                                ano_inicio INTEGER,
                                ano_fim INTEGER,
                                tipo VARCHAR(30)
);


CREATE TABLE veiculo
(
    id         UUID PRIMARY KEY,
    placa      VARCHAR(7) UNIQUE NOT NULL,
    cor        VARCHAR(30) NOT NULL,
    ano        VARCHAR(4)  NOT NULL,
    cliente_id UUID,
    veiculo_modelo_id BIGINT NOT NULL,
    CONSTRAINT fk_cliente
        FOREIGN KEY (cliente_id)
            REFERENCES cliente (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_veiculo_modelo
        FOREIGN KEY (veiculo_modelo_id)
            REFERENCES veiculo_modelo (id)
            ON DELETE CASCADE
);



CREATE TABLE peca (
                      id BIGSERIAL PRIMARY KEY,
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
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         descricao TEXT,
                         preco NUMERIC(10,2) NOT NULL,
                         duracao_estimada INTEGER,
                         categoria VARCHAR(50),
                         ativo BOOLEAN DEFAULT TRUE,
                         data_cadastro TIMESTAMP NOT NULL,
                         data_atualizacao TIMESTAMP
);




CREATE TABLE peca_modelo_veiculo (
                                     peca_id BIGINT NOT NULL,
                                     modelo_id BIGINT NOT NULL,
                                     PRIMARY KEY (peca_id, modelo_id),
                                     CONSTRAINT fk_peca FOREIGN KEY (peca_id) REFERENCES peca(id),
                                     CONSTRAINT fk_modelo FOREIGN KEY (modelo_id) REFERENCES veiculo_modelo(id)
);


CREATE TABLE orcamento (
                           id BIGSERIAL PRIMARY KEY,
                           cliente_id UUID NOT NULL,
                           veiculo_id UUID NOT NULL,
                           status VARCHAR(50),
                           valor_total NUMERIC(12, 2) NOT NULL,
                           valor_total_em_servicos NUMERIC(12, 2) NOT NULL,
                           valor_total_em_pecas NUMERIC(12, 2) NOT NULL,
                           CONSTRAINT fk_orcamento_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id),
                           CONSTRAINT fk_veiculo_id FOREIGN KEY (veiculo_id) REFERENCES veiculo(id)
);


CREATE TABLE item_peca_orcamento (
                                     id BIGSERIAL PRIMARY KEY,
                                     orcamento_id BIGINT NOT NULL,
                                     peca_id BIGINT NOT NULL,
                                     quantidade INTEGER NOT NULL CHECK (quantidade > 0),

                                     CONSTRAINT fk_item_orcamento
                                         FOREIGN KEY (orcamento_id)
                                             REFERENCES orcamento (id)
                                             ON DELETE CASCADE,

                                     CONSTRAINT fk_item_peca
                                         FOREIGN KEY (peca_id)
                                             REFERENCES peca (id)
                                             ON DELETE CASCADE,

                                     CONSTRAINT uk_orcamento_peca UNIQUE (orcamento_id, peca_id)
);



CREATE TABLE orcamento_servico (
                                   orcamento_id BIGINT NOT NULL,
                                   servico_id BIGINT NOT NULL,
                                   PRIMARY KEY (orcamento_id, servico_id),
                                   CONSTRAINT fk_os_orcamento FOREIGN KEY (orcamento_id) REFERENCES orcamento(id) ON DELETE CASCADE,
                                   CONSTRAINT fk_os_servico FOREIGN KEY (servico_id) REFERENCES servico(id) ON DELETE CASCADE
);


CREATE TABLE ordem_servico (
                               id BIGSERIAL PRIMARY KEY,
                               cliente_id UUID NOT NULL,
                               veiculo_id UUID NOT NULL,
                               status VARCHAR(50),
                               data_criacao TIMESTAMP WITHOUT TIME ZONE,
                               data_inicio_da_execucao TIMESTAMP WITHOUT TIME ZONE,
                               data_finalizacao TIMESTAMP WITHOUT TIME ZONE,
                               data_entrega TIMESTAMP WITHOUT TIME ZONE,
                               CONSTRAINT fk_os_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id),
                               CONSTRAINT fk_os_veiculo FOREIGN KEY (veiculo_id) REFERENCES veiculo(id)
);

CREATE TABLE item_peca_ordem_servico (
                                         id BIGSERIAL PRIMARY KEY,
                                         ordem_servico_id BIGINT NOT NULL,
                                         peca_id BIGINT NOT NULL,
                                         quantidade INTEGER NOT NULL,
                                         preco_unitario NUMERIC(12, 2) NOT NULL,
                                         CONSTRAINT fk_ipos_ordem_servico FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico(id) ON DELETE CASCADE,
                                         CONSTRAINT fk_ipos_peca FOREIGN KEY (peca_id) REFERENCES peca(id)
);

CREATE TABLE ordem_servico_servico (
                                       ordem_servico_id BIGINT NOT NULL,
                                       servico_id BIGINT NOT NULL,
                                       PRIMARY KEY (ordem_servico_id, servico_id),
                                       FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico(id) ON DELETE CASCADE,
                                       FOREIGN KEY (servico_id) REFERENCES servico(id) ON DELETE CASCADE
);



