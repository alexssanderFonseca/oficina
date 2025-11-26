package br.com.alexsdm.postech.oficina.servico.adapter.in.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CadastrarServicoRequest(
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotNull BigDecimal preco,
        @NotNull Integer duracaoEstimada,
        @NotBlank String categoria
) {
}
