package br.com.alexsdm.postech.oficina.veiculomodelo.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarVeiculoModeloRequest(
        @NotBlank String marca,
        @NotBlank String modelo,
        @NotNull Integer anoInicio,
        @NotNull Integer anoFim,
        @NotBlank String tipo
) {
}
