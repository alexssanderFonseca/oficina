package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.in;

import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.input.CadastrarPecaInsumoInput;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.domain.entity.PecaInsumo;

public interface CadastrarPecaInsumoUseCase {
    PecaInsumo executar(CadastrarPecaInsumoInput dto);
}
