package br.com.alexsdm.postech.oficina.ordemServico.controller.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CriarOrdemDeServicoRequest(
        @NotBlank String cpfCnpj,
        @NotNull UUID veiculoId,
        Long orcamentoId
) {
}
