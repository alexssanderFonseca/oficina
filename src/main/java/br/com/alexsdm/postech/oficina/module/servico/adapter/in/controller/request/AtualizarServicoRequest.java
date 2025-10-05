package br.com.alexsdm.postech.oficina.module.servico.adapter.in.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AtualizarServicoRequest(@NotNull @Positive BigDecimal preco,
                                      @NotNull boolean ativo) {
}
