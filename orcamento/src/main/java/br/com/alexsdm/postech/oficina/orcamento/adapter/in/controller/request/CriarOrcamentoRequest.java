package br.com.alexsdm.postech.oficina.orcamento.adapter.in.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CriarOrcamentoRequest(
        @NotNull String cpfCnpjCliente,
        @NotNull UUID veiculoId,
        @Valid List<PecaOrcamentoRequest> pecas,
        @Valid List<UUID> servicosIds
) {
}
