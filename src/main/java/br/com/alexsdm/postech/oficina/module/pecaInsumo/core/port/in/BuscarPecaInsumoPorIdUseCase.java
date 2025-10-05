package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.in;

import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.output.BuscarPecaInsumoPorIdUseCaseOutput;

import java.util.UUID;

public interface BuscarPecaInsumoPorIdUseCase {
    BuscarPecaInsumoPorIdUseCaseOutput executar(UUID id);
}
