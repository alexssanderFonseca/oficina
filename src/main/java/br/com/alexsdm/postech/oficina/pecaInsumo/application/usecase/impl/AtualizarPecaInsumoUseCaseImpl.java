package br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.impl;

import br.com.alexsdm.postech.oficina.pecaInsumo.application.gateway.PecaInsumoGateway;
import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.AtualizarPecaInsumoUseCase;
import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.BuscarPecaInsumoPorIdUseCase;
import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.dto.AtualizarPecaInsumoDTO;
import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.PecaInsumo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarPecaInsumoUseCaseImpl implements AtualizarPecaInsumoUseCase {

    private final BuscarPecaInsumoPorIdUseCase buscarPecaInsumoPorIdUseCase;
    private final PecaInsumoGateway pecaInsumoGateway;

    @Override
    public PecaInsumo executar(AtualizarPecaInsumoDTO dto) {
        var peca = buscarPecaInsumoPorIdUseCase.executar(dto.id());
        peca.atualizarQuantidadeEstoque(dto.quantidadeEstoque());
        peca.atualizarPrecos(dto.precoCusto(), dto.precoVenda());
        peca.atualizarStatus(dto.ativo());
        return pecaInsumoGateway.salvar(peca);
    }
}
