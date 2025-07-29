package br.com.alexsdm.postech.oficina.pecas.controller.input;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CadastrarPecaRequest(
        UUID id,
        String nome,
        String descricao,
        String codigoFabricante,
        String sku,
        String marca,
        List<UUID> idsModelosCompativeis,
        Integer quantidadeEstoque,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        String categoria,
        Boolean ativo,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao
) {}