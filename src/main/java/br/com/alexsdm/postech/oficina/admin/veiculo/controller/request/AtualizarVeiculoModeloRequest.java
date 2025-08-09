package br.com.alexsdm.postech.oficina.admin.veiculo.controller.request;

public record AtualizarVeiculoModeloRequest(
    String marca,
    String modelo,
    Integer anoInicio,
    Integer anoFim,
    String tipo
) {}
