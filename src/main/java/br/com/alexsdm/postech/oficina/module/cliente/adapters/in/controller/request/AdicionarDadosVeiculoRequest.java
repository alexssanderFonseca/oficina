package br.com.alexsdm.postech.oficina.module.cliente.adapters.in.controller.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdicionarDadosVeiculoRequest(
        @NotBlank String placa,
        @NotBlank String cor,
        @NotBlank String marca,
        @NotBlank String modelo,
        @NotBlank String ano) {

}
