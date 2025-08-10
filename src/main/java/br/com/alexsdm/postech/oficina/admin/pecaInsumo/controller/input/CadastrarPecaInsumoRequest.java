package br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller.input;

import java.math.BigDecimal;
import java.util.List;

public record CadastrarPecaInsumoRequest(
        String nome,
        String descricao,
        String codigoFabricante,
        String marca,
        List<Long> idsModelosCompativeis,
        Integer quantidadeEstoque,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        String categoria,
        Boolean ativo) {
}