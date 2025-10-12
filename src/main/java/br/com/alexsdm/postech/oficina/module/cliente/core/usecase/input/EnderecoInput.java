package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input;

import lombok.Builder;

@Builder
public record EnderecoInput(String rua,
                            String numero,
                            String bairro,
                            String cep,
                            String cidade,
                            String uf) {
}
