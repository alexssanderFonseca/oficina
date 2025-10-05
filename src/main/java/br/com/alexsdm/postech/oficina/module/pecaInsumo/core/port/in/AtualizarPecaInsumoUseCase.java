package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.in;

import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.input.AtualizarPecaInsumoInput;

public interface AtualizarPecaInsumoUseCase {
    void executar(AtualizarPecaInsumoInput input);
}
