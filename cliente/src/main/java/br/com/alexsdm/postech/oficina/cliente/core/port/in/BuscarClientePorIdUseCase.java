package br.com.alexsdm.postech.oficina.cliente.core.port.in;

import br.com.alexsdm.postech.oficina.cliente.core.usecase.output.BuscarClientePorIdOutput;

import java.util.UUID;

public interface BuscarClientePorIdUseCase {
    BuscarClientePorIdOutput executar(UUID id);
}