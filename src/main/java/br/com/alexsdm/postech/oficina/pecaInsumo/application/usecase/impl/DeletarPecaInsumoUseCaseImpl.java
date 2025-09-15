package br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.impl;

import br.com.alexsdm.postech.oficina.pecaInsumo.application.gateway.PecaInsumoGateway;
import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.BuscarPecaInsumoPorIdUseCase;
import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.DeletarPecaInsumoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarPecaInsumoUseCaseImpl implements DeletarPecaInsumoUseCase {

    private final BuscarPecaInsumoPorIdUseCase buscarPecaInsumoPorIdUseCase;
    private final PecaInsumoGateway pecaInsumoGateway;

    @Override
    public void executar(Long id) {
        var peca = buscarPecaInsumoPorIdUseCase.executar(id);
        peca.setAtivo(false); // Inativação lógica
        pecaInsumoGateway.salvar(peca);
    }
}
