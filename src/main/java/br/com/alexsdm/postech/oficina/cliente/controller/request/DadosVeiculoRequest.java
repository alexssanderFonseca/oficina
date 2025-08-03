package br.com.alexsdm.postech.oficina.cliente.controller.request;

public record   DadosVeiculoRequest(
        Long veiculoModeloId,
        String placa,
        String cor,
        String ano){

}
