package br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.impl;

import br.com.alexsdm.postech.oficina.pecaInsumo.application.gateway.PecaInsumoGateway;
import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.ListarPecasInsumoUseCase;
import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.PecaInsumo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarPecasInsumoUseCaseImpl implements ListarPecasInsumoUseCase {

    private final PecaInsumoGateway pecaInsumoGateway;

    @Override
    public List<PecaInsumo> executar() {
        // O gateway precisa do método findAll
        return pecaInsumoGateway.listarTodos();
    }
}
