package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input;

import lombok.Builder;

@Builder
public record AtualizarClienteInput(
        java.util.UUID clienteId,
        String email,
        String telefone,
        EnderecoInput endereco
) {
}
