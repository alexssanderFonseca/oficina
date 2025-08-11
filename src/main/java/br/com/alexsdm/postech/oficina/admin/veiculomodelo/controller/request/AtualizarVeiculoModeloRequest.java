package br.com.alexsdm.postech.oficina.admin.veiculomodelo.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarVeiculoModeloRequest(
        @NotBlank String marca,
        @NotBlank String modelo,
        @NotNull Integer anoInicio,
        @NotNull Integer anoFim,
        @NotBlank String tipo
) {
}
