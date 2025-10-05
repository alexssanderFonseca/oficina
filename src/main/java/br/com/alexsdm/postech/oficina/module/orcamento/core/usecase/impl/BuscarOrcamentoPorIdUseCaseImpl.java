package br.com.alexsdm.postech.oficina.module.orcamento.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.orcamento.core.port.out.ClienteOrcamentoPort;
import br.com.alexsdm.postech.oficina.module.orcamento.core.port.out.OrcamentoRepository;
import br.com.alexsdm.postech.oficina.module.orcamento.core.port.in.BuscarOrcamentoPorIdUseCase;
import br.com.alexsdm.postech.oficina.module.orcamento.core.usecase.output.BuscarOrcamentoPorIdOutput;
import br.com.alexsdm.postech.oficina.module.orcamento.core.exception.OrcamentoException;
import br.com.alexsdm.postech.oficina.module.orcamento.core.exception.OrcamentoNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarOrcamentoPorIdUseCaseImpl implements BuscarOrcamentoPorIdUseCase {

    private final OrcamentoRepository orcamentoRepository;
    private final ClienteOrcamentoPort clienteOrcamentoPort;

    @Override
    public BuscarOrcamentoPorIdOutput executar(UUID id) {
        var orcamento = orcamentoRepository.buscarPorId(id)
                .orElseThrow(OrcamentoNaoEncontradaException::new);

        var cliente = clienteOrcamentoPort.buscarClienteComVeiculo(orcamento.getClienteId(),
                        orcamento.getVeiculoId())
                .orElseThrow(() -> new OrcamentoException("Cliente relacionado ao orcamento não encontrado"));
        return BuscarOrcamentoPorIdOutput.toOutput(orcamento, cliente);

    }
}
