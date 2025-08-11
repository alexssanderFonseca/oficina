insert into usuarios(id,username, password)
values ('6d0ff74a-d338-4880-9fdf-94a55881e6c9', 'admin', '$2a$10$r.i31qRacNVGkDfdZ31l6ejI8Po3QuXwrZJC1FT2kqYLGIQJcVS16');


-- Inserir endereço
INSERT INTO endereco (id, rua, numero, bairro, cep, cidade, uf)
VALUES ('550e8400-e29b-41d4-a716-446655440010', 'Rua Teste', '123', 'Centro', '12345-678', 'São Paulo', 'SP');

-- Inserir cliente
INSERT INTO cliente (id, nome, sobrenome, cpf_cnpj, email, telefone, endereco_id)
VALUES ('550e8400-e29b-41d4-a716-446655440011', 'Cliente', 'Teste', '12345678901', 'cliente@teste.com', '11999999999', '550e8400-e29b-41d4-a716-446655440010');

-- Inserir modelo de veículo com ID 100
INSERT INTO veiculo_modelo (id, marca, modelo, ano_inicio, ano_fim, tipo)
VALUES (100, 'Toyota', 'Corolla', 2020, 2024, 'Sedan');


-- Inserir veículos
INSERT INTO veiculo (id, placa, cor, ano, cliente_id, veiculo_modelo_id)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'ABC1234', 'Branco', '2022', '550e8400-e29b-41d4-a716-446655440011', 100),
    ('550e8400-e29b-41d4-a716-446655440002', 'DEF5678', 'Preto', '2023', '550e8400-e29b-41d4-a716-446655440011', 100);

-- Inserir peça com ID 100
INSERT INTO peca_insumo (id, nome, descricao, codigo_fabricante, sku, marca, modelos_compativeis, quantidade_estoque, preco_custo, preco_venda, categoria, ativo, data_cadastro, data_atualizacao)
VALUES (100, 'Pastilha de Freio', 'Pastilha de freio dianteira', 'PF001', 'SKU001', 'Bosch', 'Corolla 2020-2024', 50, 80.00, 120.00, 'Freios', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserir serviço com ID 100
INSERT INTO servico (id, nome, descricao, preco, duracao_estimada, categoria, ativo, data_cadastro, data_atualizacao)
VALUES (100, 'Troca de Pastilha de Freio', 'Serviço de troca das pastilhas de freio dianteiras', 150.00, 120, 'Freios', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Relacionar peça com modelo de veículo
INSERT INTO peca_modelo_veiculo (peca_id, modelo_id)
VALUES (100, 100);

-- Inserir orçamento com ID 100
INSERT INTO orcamento (id, cliente_id, veiculo_id, status, valor_total, valor_total_em_servicos, valor_total_em_pecas)
VALUES (100, '550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440001', '2', 270.00, 150.00, 120.00);

-- Inserir item de peça no orçamento com ID 100
INSERT INTO item_peca_orcamento (id, orcamento_id, peca_id, quantidade)
VALUES (100, 100, 100, 1);

-- Inserir serviço no orçamento
INSERT INTO orcamento_servico (orcamento_id, servico_id)
VALUES (100, 100);
