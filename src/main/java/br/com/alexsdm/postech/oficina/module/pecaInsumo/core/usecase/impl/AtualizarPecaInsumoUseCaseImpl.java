package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.out.PecaInsumoRepository;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.in.AtualizarPecaInsumoUseCase;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.input.AtualizarPecaInsumoInput;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.domain.exception.PecaInsumoNaoEncontradaException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class AtualizarPecaInsumoUseCaseImpl implements AtualizarPecaInsumoUseCase {

    private final PecaInsumoRepository pecaInsumoRepository;

    @Override
    public void executar(AtualizarPecaInsumoInput input) {
        var pecaInsumo = pecaInsumoRepository.buscarPorId(input.id())
                .orElseThrow(PecaInsumoNaoEncontradaException::new);

        pecaInsumo.atualizarQuantidadeEstoque(input.quantidadeEstoque());
        pecaInsumo.atualizarPrecos(input.precoCusto(), input.precoVenda());
        pecaInsumo.atualizarStatus(input.ativo());
        pecaInsumoRepository.salvar(pecaInsumo);
    }
}
