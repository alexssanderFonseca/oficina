package br.com.alexsdm.postech.oficina.module.peca_insumo.core.port.in;

import br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.input.CadastrarPecaInsumoInput;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.entity.PecaInsumo;

public interface CadastrarPecaInsumoUseCase {
    PecaInsumo executar(CadastrarPecaInsumoInput dto);
}
