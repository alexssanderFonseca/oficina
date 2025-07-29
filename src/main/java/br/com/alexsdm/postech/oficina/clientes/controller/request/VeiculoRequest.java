package br.com.alexsdm.postech.oficina.clientes.controller.request;

public record VeiculoRequest(
        String veiculoModeloId,
        String placa,
        String ano,
        String cor
) {
}