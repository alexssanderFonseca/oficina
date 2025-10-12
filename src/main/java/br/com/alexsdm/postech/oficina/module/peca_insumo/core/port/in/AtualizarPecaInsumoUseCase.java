package br.com.alexsdm.postech.oficina.module.peca_insumo.core.port.in;

import br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.input.AtualizarPecaInsumoInput;

public interface AtualizarPecaInsumoUseCase {
    void executar(AtualizarPecaInsumoInput input);
}
