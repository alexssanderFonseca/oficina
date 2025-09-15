package br.com.alexsdm.postech.oficina.orcamento.application.usecase.impl;

import br.com.alexsdm.postech.oficina.orcamento.application.gateway.OrcamentoGateway;
import br.com.alexsdm.postech.oficina.orcamento.application.usecase.BuscarOrcamentoPorIdUseCase;
import br.com.alexsdm.postech.oficina.orcamento.application.usecase.RecusarOrcamentoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecusarOrcamentoUseCaseImpl implements RecusarOrcamentoUseCase {

    private final BuscarOrcamentoPorIdUseCase buscarOrcamentoPorIdUseCase;
    private final OrcamentoGateway orcamentoGateway;

    @Override
    public void executar(Long id) {
        var orcamento = buscarOrcamentoPorIdUseCase.executar(id);
        orcamento.recusar();
        orcamentoGateway.salvar(orcamento);
    }
}
