package br.com.alexsdm.postech.oficina.module.cliente.core.port.in;

import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.AtualizarClienteInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.output.BuscarClientePorIdOutput;

public interface AtualizarClienteUseCase {
    BuscarClientePorIdOutput executar(AtualizarClienteInput input);
}
