package br.com.alexsdm.postech.oficina.orcamento.controller.request;

import br.com.alexsdm.postech.oficina.orcamento.service.input.PecaOrcamentoInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CriacaoOrcamentoRequest(
        @NotBlank String cpfCnpjCliente,
        @NotNull UUID veiculoId,
        @NotEmpty List<PecaOrcamentoInput> pecas,
        @NotEmpty List<Long> servicos
) {
}
