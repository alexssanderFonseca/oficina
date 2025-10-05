package br.com.alexsdm.postech.oficina.module.pecaInsumo.adapter.in.controller.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CadastrarPecaInsumoRequest(
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotBlank String codigoFabricante,
        @NotBlank String marca,
        @Positive Integer quantidadeEstoque,
        @NotNull @Positive BigDecimal precoCusto,
        @NotNull @Positive BigDecimal precoVenda,
        @NotBlank String categoria,
        @NotNull Boolean ativo) {
}

