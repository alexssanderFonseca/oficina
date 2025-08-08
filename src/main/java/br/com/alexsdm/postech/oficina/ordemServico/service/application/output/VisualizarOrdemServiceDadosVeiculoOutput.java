package br.com.alexsdm.postech.oficina.ordemServico.service.application.output;

public record VisualizarOrdemServiceDadosVeiculoOutput(
        String placa,
        String marca,
        String ano,
        String cor
) {
}
