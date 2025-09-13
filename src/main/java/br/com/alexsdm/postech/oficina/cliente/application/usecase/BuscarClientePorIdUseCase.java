package br.com.alexsdm.postech.oficina.cliente.application.usecase;

import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.BuscarClientePorIdInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.BuscarClientePorIdOutput;

public interface BuscarClientePorIdUseCase {
    BuscarClientePorIdOutput executar(BuscarClientePorIdInput input);
}