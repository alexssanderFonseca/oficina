package br.com.alexsdm.postech.oficina.veiculos.controller.request;

public record CadastrarVeiculoModeloRequest(
    String marca,
    String modelo,
    Integer anoInicio,
    Integer anoFim,
    String tipo
) {}
