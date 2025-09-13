package br.com.alexsdm.postech.oficina.cliente.application.usecase.dto;

import lombok.Builder;

@Builder
public record AtualizarClienteInput(
        java.util.UUID clienteId,
        String email,
        String telefone,
        EnderecoInput endereco
) {
}
