package br.com.alexsdm.postech.oficina.orcamento.application.usecase.impl;

import br.com.alexsdm.postech.oficina.orcamento.application.gateway.OrcamentoGateway;
import br.com.alexsdm.postech.oficina.orcamento.application.usecase.BuscarOrcamentoPorIdUseCase;
import br.com.alexsdm.postech.oficina.orcamento.domain.entity.Orcamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarOrcamentoPorIdUseCaseImpl implements BuscarOrcamentoPorIdUseCase {

    private final OrcamentoGateway orcamentoGateway;

    @Override
    public Orcamento executar(Long id) {
        // O gateway de orçamento precisa de um método findById
        // Vou adicioná-lo
        return orcamentoGateway.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado")); // TODO: Exceção específica
    }
}
