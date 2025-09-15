package br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.impl;

import br.com.alexsdm.postech.oficina.pecaInsumo.application.gateway.PecaInsumoGateway;
import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.BuscarPecaInsumoPorIdUseCase;
import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.pecaInsumo.domain.exception.PecaInsumoNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarPecaInsumoPorIdUseCaseImpl implements BuscarPecaInsumoPorIdUseCase {

    private final PecaInsumoGateway pecaInsumoGateway;

    @Override
    public PecaInsumo executar(Long id) {
        return pecaInsumoGateway.buscarPorId(id)
                .orElseThrow(PecaInsumoNaoEncontradaException::new);
    }
}
