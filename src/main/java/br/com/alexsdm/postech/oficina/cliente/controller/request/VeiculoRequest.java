package br.com.alexsdm.postech.oficina.cliente.controller.request;

public record VeiculoRequest(
        String veiculoModeloId,
        String placa,
        String ano,
        String cor
) {
}