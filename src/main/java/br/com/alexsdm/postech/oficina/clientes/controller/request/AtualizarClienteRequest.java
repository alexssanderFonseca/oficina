package br.com.alexsdm.postech.oficina.clientes.controller.request;

public record ClienteAtualizarRequest(String email,
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