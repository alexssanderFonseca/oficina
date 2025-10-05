package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.exception.OrdemServicoOrcamentoNaoEncontradoException;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.port.in.AbrirOrdemServicoUseCase;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.port.out.OrdemServicoOrcamentoPort;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.input.CriarOrdemServicoInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AbrirOrdemServicoUseCaseImpl implements AbrirOrdemServicoUseCase {

    private final OrdemServicoOrcamentoPort orcamentoPort;
    private final OrdemServicoRepository ordemServicoRepository;


    @Override
    public UUID executar(CriarOrdemServicoInput input) {
        var orcamento = orcamentoPort.buscarPorId(input.orcamentoId())
                .orElseThrow((OrdemServicoOrcamentoNaoEncontradoException::new));

        var os = ordemServicoRepository.salvar(new OrdemServico(
                UUID.randomUUID(),
                orcamento.getClienteId(),
                orcamento.getVeiculoId()));
        return os.getId();
    }

}
