package br.com.alexsdm.postech.oficina.cliente.application.usecase.dto;

import lombok.Builder;

@Builder
public record CadastrarClienteInput(String nome,
                                    String sobrenome,
                                    String cpfCnpj,
                                    String email,
                                    String telefone,
                                    EnderecoInput endereco) {
}
