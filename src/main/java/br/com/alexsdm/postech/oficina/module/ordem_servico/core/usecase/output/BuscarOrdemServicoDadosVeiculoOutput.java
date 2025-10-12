package br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.output;

public record BuscarOrdemServicoDadosVeiculoOutput(
        String placa,
        String marca,
        String ano,
        String cor
) {
}