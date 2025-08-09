package br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller.input;

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
        List<Long> idsModelosCompativeis,
        Integer quantidadeEstoque,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        String categoria,
        Boolean ativo,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao
) {}