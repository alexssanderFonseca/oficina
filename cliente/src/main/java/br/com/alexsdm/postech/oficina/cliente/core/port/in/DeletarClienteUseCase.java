package br.com.alexsdm.postech.oficina.cliente.core.port.in;

import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.DeletarClienteInput;

public interface DeletarClienteUseCase {
    void executar(DeletarClienteInput input);
}
