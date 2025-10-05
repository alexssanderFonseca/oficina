package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input;

import lombok.Builder;

@Builder
public record CadastrarClienteInput(String nome,
                                    String sobrenome,
                                    String cpfCnpj,
                                    String email,
                                    String telefone,
                                    EnderecoInput endereco) {
}
