package br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase;

import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.dto.CadastrarPecaInsumoDTO;
import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.PecaInsumo;

public interface CadastrarPecaInsumoUseCase {
    PecaInsumo executar(CadastrarPecaInsumoDTO dto);
}
