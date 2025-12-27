-- Inserir usuários
INSERT INTO usuarios(id, username, password, role)
VALUES ('6d0ff74a-d338-4880-9fdf-94a55881e6c9', 'admin', '$2a$10$r.i31qRacNVGkDfdZ31l6ejI8Po3QuXwrZJC1FT2kqYLGIQJcVS16', 'FUNCIONARIO');

-- Inserir endereço
INSERT INTO endereco (id, rua, numero, bairro, cep, cidade, uf)
VALUES ('550e8400-e29b-41d4-a716-446655440010', 'Rua Teste', '123', 'Centro', '12345-678', 'São Paulo', 'SP');

-- Inserir cliente
INSERT INTO cliente (id, nome, sobrenome, cpf_cnpj, email, telefone, endereco_id)
VALUES ('550e8400-e29b-41d4-a716-446655440011', 'Cliente', 'Teste', '24906627080', 'cliente@teste.com', '11999999999', '550e8400-e29b-41d4-a716-446655440010');

-- Inserir veículos
INSERT INTO veiculo (id, placa, cor, ano, marca, modelo, cliente_id)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'ABC1234', 'Branco', 2023, 'Chevrolet', 'Corsa', '550e8400-e29b-41d4-a716-446655440011'),
    ('550e8400-e29b-41d4-a716-446655440002', 'DEF5678', 'Preto', 2023, 'Chevrolet', 'Corsa', '550e8400-e29b-41d4-a716-446655440011');

-- Inserir peça
INSERT INTO peca_insumo (id, nome, descricao, codigo_fabricante, sku, marca, modelos_compativeis, quantidade_estoque, preco_custo, preco_venda, categoria, ativo, data_cadastro, data_atualizacao)
VALUES ('a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d', 'Pastilha de Freio', 'Pastilha de freio dianteira', 'PF001', 'SKU001', 'Bosch', 'Corolla 2020-2024', 50, 80.00, 120.00, 'Freios', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserir serviço
INSERT INTO servico (id, nome, descricao, preco, duracao_estimada, categoria, ativo, data_cadastro, data_atualizacao)
VALUES ('b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e', 'Troca de Pastilha de Freio', 'Serviço de troca das pastilhas de freio dianteiras', 150.00, 120, 'Freios', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserir orçamento
INSERT INTO orcamento (id, cliente_id, veiculo_id, status, valor_total, valor_total_em_servicos, valor_total_em_pecas)
VALUES ('c3d4e5f6-a7b8-4c5d-0e1f-2a3b4c5d6e7f', '550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440001', '2', 270.00, 150.00, 120.00);

-- Inserir item de peça no orçamento
INSERT INTO item_peca_orcamento (id, orcamento_id, peca_id, quantidade, preco, nome, descricao)
VALUES ('d4e5f6a7-b8c9-4d5e-1f2a-3b4c5d6e7f8a', 'c3d4e5f6-a7b8-4c5d-0e1f-2a3b4c5d6e7f', 'a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d', 1, 100.0, 'Filtro de oleo', 'Filtro de oleo WD40 viscosidade leve para motores 1.0');

-- Inserir serviço no orçamento
INSERT INTO orcamento_servico (id, orcamento_id, servico_id, nome, preco, descricao)
VALUES ('e5f6a7b8-c9d0-4e5f-2a3b-4c5d6e7f8a9b', 'c3d4e5f6-a7b8-4c5d-0e1f-2a3b4c5d6e7f', 'b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e', 'Aperto na suspensao', 500.0, 'O aperto na suspensão envolve alinhar a supensão do carro devolvendo o eixo natural evitando desgastes prematuros');