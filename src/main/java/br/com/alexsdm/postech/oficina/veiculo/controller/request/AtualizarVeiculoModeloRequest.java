package br.com.alexsdm.postech.oficina.veiculo.controller.request;

public record AtualizarVeiculoModeloRequest(
    String marca,
    String modelo,
    Integer anoInicio,
    Integer anoFim,
    String tipo
) {}
