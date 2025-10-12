package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input;


public record AtualizarClienteInput(
        java.util.UUID clienteId,
        String email,
        String telefone,
        EnderecoInput endereco
) {
}
