package br.com.alexsdm.postech.oficina.ordemServico.controller.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record FinalizarDiagnosticoRequest(
        @NotEmpty List<ItemPeca> idPecasNecessarias,
        @NotEmpty List<Long> idServicosNecessarios
) {
}


