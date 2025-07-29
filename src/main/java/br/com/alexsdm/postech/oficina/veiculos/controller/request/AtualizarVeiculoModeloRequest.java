package br.com.alexsdm.postech.oficina.veiculos.controller.request;

public record AtualizarVeiculoModeloRequest(
    String marca,
    String modelo,
    Integer anoInicio,
    Integer anoFim,
    String tipo
) {}
