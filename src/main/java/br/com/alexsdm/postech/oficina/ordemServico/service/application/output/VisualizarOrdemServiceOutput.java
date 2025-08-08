package br.com.alexsdm.postech.oficina.ordemServico.service.application.output;

import java.time.LocalDateTime;
import java.util.List;

public record VisualizarOrdemServiceOutput(
        String id,
        LocalDateTime dataHoraCriacao,
        LocalDateTime dataInicioDaExecucao,
        LocalDateTime dataFinalizacao,
        LocalDateTime dataEntrega,
        String status,
        VisualizarOrdemServiceDadosClientOutput dadosCliente,
        VisualizarOrdemServiceDadosVeiculoOutput dadosVeiculo,
        List<VisualizarOrdemServicePecasInsumosOutput> pecasInsumos,
        List<VisualizarOrdemServicosOutput> servicos
        ) {
}
