package br.com.alexsdm.postech.oficina.ordemServico.infrastructure.controller.request;
import java.util.List;
public record FinalizarDiagnosticoRequest(List<ItemPeca> idPecasNecessarias, List<Long> idServicosNecessarios) {}