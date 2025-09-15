package br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.controller.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record CadastrarPecaInsumoRequest(
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotBlank String codigoFabricante,
        @NotBlank String marca,
        @NotEmpty List<Long> idsModelosCompativeis,
        @Positive Integer quantidadeEstoque,
        @NotNull @Positive BigDecimal precoCusto,
        @NotNull @Positive BigDecimal precoVenda,
        @NotBlank String categoria,
        @NotNull Boolean ativo) {
}

