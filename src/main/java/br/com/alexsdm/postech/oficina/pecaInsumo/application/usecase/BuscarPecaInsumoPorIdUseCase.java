package br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase;

import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.PecaInsumo;

public interface BuscarPecaInsumoPorIdUseCase {
    PecaInsumo executar(Long id);
}
