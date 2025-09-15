package br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.dto;

public record AtualizarVeiculoModeloOutput(
        String marca,
        String modelo,
        Integer anoInicio,
        Integer anoFim,
        String tipo
) {
}