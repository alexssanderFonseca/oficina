package br.com.alexsdm.postech.oficina.admin.servico.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServicoAtualizarRequest(@NotNull @Positive BigDecimal preco,
                                      @NotNull boolean ativo) {
}
