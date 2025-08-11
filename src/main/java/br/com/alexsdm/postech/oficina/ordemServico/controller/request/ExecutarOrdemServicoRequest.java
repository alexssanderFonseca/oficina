package br.com.alexsdm.postech.oficina.ordemServico.controller.request;

import jakarta.validation.constraints.NotNull;

public record ExecutarOrdemServicoRequest(@NotNull Long orcamentoId) {
}
