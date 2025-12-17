package br.com.alexsdm.postech.oficina.cliente.adapters.in.controller.request;


public record AtualizarClienteRequest(String email,
                                      String telefone,
                                      EnderecoAtualizarRequest endereco) {
}

