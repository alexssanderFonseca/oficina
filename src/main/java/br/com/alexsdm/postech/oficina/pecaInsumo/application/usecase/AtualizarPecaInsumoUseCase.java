package br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase;

import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.dto.AtualizarPecaInsumoDTO;
import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.PecaInsumo;

public interface AtualizarPecaInsumoUseCase {
    PecaInsumo executar(AtualizarPecaInsumoDTO dto);
}
