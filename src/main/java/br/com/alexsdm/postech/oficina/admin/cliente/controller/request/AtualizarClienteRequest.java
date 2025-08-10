package br.com.alexsdm.postech.oficina.admin.cliente.controller.request;

public record AtualizarClienteRequest(String email,
                                      String telefone,
                                      EnderecoAtualizarRequest endereco) {
}

