package br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.peca_insumo.core.port.out.PecaInsumoRepository;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.port.in.AtualizarPecaInsumoUseCase;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.input.AtualizarPecaInsumoInput;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.exception.PecaInsumoNaoEncontradaException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class AtualizarPecaInsumoUseCaseImpl implements AtualizarPecaInsumoUseCase {

    private final PecaInsumoRepository pecaInsumoRepository;

    @Override
    public void executar(AtualizarPecaInsumoInput input) {
        var pecaInsumo = pecaInsumoRepository.buscarPorId(input.id())
                .orElseThrow(() -> new PecaInsumoNaoEncontradaException(input.id()));

        pecaInsumo.atualizarQuantidadeEstoque(input.quantidadeEstoque());
        pecaInsumo.atualizarPrecos(input.precoCusto(), input.precoVenda());
        pecaInsumo.atualizarStatus(input.ativo());
        pecaInsumoRepository.salvar(pecaInsumo);
    }
}
