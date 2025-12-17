package br.com.alexsdm.postech.oficina.cliente.core.usecase.input;

import lombok.Builder;

@Builder
public record EnderecoOutput(String rua,
                             String numero,
                             String bairro,
                             String cep,
                             String cidade,
                             String uf) {
}
