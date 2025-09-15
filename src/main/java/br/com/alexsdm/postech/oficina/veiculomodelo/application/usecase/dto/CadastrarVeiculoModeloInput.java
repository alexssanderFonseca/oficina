package br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.dto;

public record CadastrarVeiculoModeloInput(
        String marca,
        String modelo,
        Integer anoInicio,
        Integer anoFim,
        String tipo
) {
}