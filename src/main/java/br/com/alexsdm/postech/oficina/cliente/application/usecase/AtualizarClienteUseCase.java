package br.com.alexsdm.postech.oficina.cliente.application.usecase;

import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.AtualizarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.BuscarClientePorIdOutput;

public interface AtualizarClienteUseCase {
    BuscarClientePorIdOutput executar(AtualizarClienteInput input);
}
