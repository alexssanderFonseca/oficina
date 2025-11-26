package br.com.alexsdm.postech.oficina.orcamento.core.usecase.impl;

import br.com.alexsdm.postech.oficina.orcamento.core.port.out.ClienteOrcamentoPort;
import br.com.alexsdm.postech.oficina.orcamento.core.port.out.OrcamentoRepository;
import br.com.alexsdm.postech.oficina.orcamento.core.service.PdfGenerator;
import br.com.alexsdm.postech.oficina.orcamento.core.port.in.EnviarOrcamentoPdfUseCase;
import br.com.alexsdm.postech.oficina.orcamento.core.exception.OrcamentoException;
import br.com.alexsdm.postech.oficina.orcamento.core.exception.OrcamentoNaoEncontradaException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Named
@RequiredArgsConstructor
public class EnviarOrcamentoPdfUseCaseImpl implements EnviarOrcamentoPdfUseCase {

    private final OrcamentoRepository orcamentoRepository;
    private final ClienteOrcamentoPort clienteOrcamentoPort;
    private final PdfGenerator pdfGenerator;

    @Override
    public byte[] executar(UUID id) {
        var orcamento = orcamentoRepository.buscarPorId(id)
                .orElseThrow(OrcamentoNaoEncontradaException::new);
        var cliente = clienteOrcamentoPort.buscarClienteComVeiculo(orcamento.getClienteId(),
                        orcamento.getVeiculoId())
                .orElseThrow(() -> new OrcamentoException("Veiculo informado no orcamento inexistente"));

        return pdfGenerator.generate(orcamento, cliente);
    }
}
