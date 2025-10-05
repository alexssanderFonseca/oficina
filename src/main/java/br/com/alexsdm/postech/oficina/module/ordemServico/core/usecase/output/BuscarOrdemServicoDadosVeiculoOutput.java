package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.output;

public record BuscarOrdemServicoDadosVeiculoOutput(
        String placa,
        String marca,
        String ano,
        String cor
) {
}