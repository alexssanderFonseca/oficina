package br.com.alexsdm.postech.oficina.ordemServico.controller.request;

import jakarta.validation.constraints.NotNull;

public record ItemPeca(@NotNull Long idPeca, @NotNull Integer qtd) {
}