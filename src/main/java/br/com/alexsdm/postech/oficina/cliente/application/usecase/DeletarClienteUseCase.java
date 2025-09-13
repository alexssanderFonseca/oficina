package br.com.alexsdm.postech.oficina.cliente.application.usecase;

import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.DeletarClienteInput;

public interface DeletarClienteUseCase {
    void executar(DeletarClienteInput input);
}
