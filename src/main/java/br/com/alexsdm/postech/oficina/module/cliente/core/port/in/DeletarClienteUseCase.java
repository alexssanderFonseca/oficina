package br.com.alexsdm.postech.oficina.module.cliente.core.port.in;

import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.DeletarClienteInput;

public interface DeletarClienteUseCase {
    void executar(DeletarClienteInput input);
}
