package br.com.alexsdm.postech.oficina.admin.cliente.controller.request;

public record AtualizarClienteRequest(String email,
                                      String telefone,
                                      String sobrenome,
                                      EnderecoAtualizarRequest endereco) {
}

record EnderecoAtualizarRequest(String rua,
                                String bairro,
                                String cep,
                                String cidade,
                                String uf) {
}