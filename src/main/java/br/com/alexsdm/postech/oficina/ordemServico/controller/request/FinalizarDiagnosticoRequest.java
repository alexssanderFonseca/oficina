package br.com.alexsdm.postech.oficina.ordemServico.controller.request;

import java.util.List;
import java.util.UUID;

public record FinalizarDiagnosticoRequest(
        List<ItemPeca> idPecasNecessarias,
        List<Long> idServicosNecessarios
) {
}


