package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.output;

import lombok.Builder;

@Builder
public record BuscarClientePorDocumentoEnderecoOutput(
        String rua,
        String numero,
        String bairro,
        String cep,
        String cidade,
        String uf
) {
}
