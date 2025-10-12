package br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.peca_insumo.core.port.out.PecaInsumoRepository;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.port.in.DeletarPecaInsumoUseCase;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.exception.PecaInsumoNaoEncontradaException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Named
@RequiredArgsConstructor
public class DeletarPecaInsumoUseCaseImpl implements DeletarPecaInsumoUseCase {

    private final PecaInsumoRepository pecaInsumoRepository;

    @Override
    public void executar(UUID id) {
        var pecaInsumo = pecaInsumoRepository.buscarPorId(id)
                .orElseThrow(()->new PecaInsumoNaoEncontradaException(id));
        pecaInsumo.atualizarStatus(false);
        pecaInsumoRepository.salvar(pecaInsumo);
    }
}
