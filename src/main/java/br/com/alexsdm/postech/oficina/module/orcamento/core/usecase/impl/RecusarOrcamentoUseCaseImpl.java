package br.com.alexsdm.postech.oficina.module.orcamento.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.orcamento.core.port.out.OrcamentoRepository;
import br.com.alexsdm.postech.oficina.module.orcamento.core.port.in.BuscarOrcamentoPorIdUseCase;
import br.com.alexsdm.postech.oficina.module.orcamento.core.port.in.RecusarOrcamentoUseCase;
import br.com.alexsdm.postech.oficina.module.orcamento.core.exception.OrcamentoNaoEncontradaException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Named
@RequiredArgsConstructor
public class RecusarOrcamentoUseCaseImpl implements RecusarOrcamentoUseCase {

    private final BuscarOrcamentoPorIdUseCase buscarOrcamentoPorIdUseCase;
    private final OrcamentoRepository orcamentoRepository;

    @Override
    public void executar(UUID id) {
        var orcamento = orcamentoRepository.buscarPorId(id)
                .orElseThrow(OrcamentoNaoEncontradaException::new);
        orcamento.recusar();
        orcamentoRepository.salvar(orcamento);
    }
}
