package br.com.alexsdm.postech.oficina.orcamento.infrastructure.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record CriacaoOrcamentoRequest(
        @NotNull UUID clienteId,
        @NotNull UUID veiculoId,
        List<PecaOrcamentoRequest> pecas,
        List<Long> servicos
) {}
