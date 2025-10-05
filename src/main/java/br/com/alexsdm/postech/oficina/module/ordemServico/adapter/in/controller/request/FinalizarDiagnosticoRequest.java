package br.com.alexsdm.postech.oficina.module.ordemServico.adapter.in.controller.request;

import java.util.List;
import java.util.UUID;

public record FinalizarDiagnosticoRequest(List<ItemPeca> idPecasNecessarias,
                                          List<UUID> idServicosNecessarios) {
}