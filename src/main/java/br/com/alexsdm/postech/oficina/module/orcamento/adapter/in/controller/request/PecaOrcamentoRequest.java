package br.com.alexsdm.postech.oficina.module.orcamento.adapter.in.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PecaOrcamentoRequest(@NotNull UUID pecaId,
                                   @Min(1) int quantidade) {
}
