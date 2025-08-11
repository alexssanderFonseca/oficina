package br.com.alexsdm.postech.oficina.admin.cliente.controller.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdicionarDadosVeiculoRequest(
        @NotNull Long veiculoModeloId,
        @NotBlank String placa,
        @NotBlank String cor,
        @NotBlank String ano) {

}
