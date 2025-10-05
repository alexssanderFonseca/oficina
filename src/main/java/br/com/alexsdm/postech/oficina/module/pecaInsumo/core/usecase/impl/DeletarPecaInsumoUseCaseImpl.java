package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.out.PecaInsumoRepository;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.in.DeletarPecaInsumoUseCase;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.domain.exception.PecaInsumoNaoEncontradaException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Named
@RequiredArgsConstructor
public class DeletarPecaInsumoUseCaseImpl implements DeletarPecaInsumoUseCase {

    private final PecaInsumoRepository pecaInsumoRepository;

    @Override
    public void executar(UUID id) {
        var pecaInsumo = pecaInsumoRepository.buscarPorId(id)
                .orElseThrow(PecaInsumoNaoEncontradaException::new);
        pecaInsumo.atualizarStatus(false);
        pecaInsumoRepository.salvar(pecaInsumo);
    }
}
