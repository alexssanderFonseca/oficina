package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordemServico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.port.in.EntregarOrdemServicoUseCase;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.exception.OrdemServicoNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EntregarOrdemServicoUseCaseImpl implements EntregarOrdemServicoUseCase {

    private final OrdemServicoRepository gateway;

    @Override
    public void executar(UUID id) {
        var os = gateway.buscarPorId(id)
                .orElseThrow(OrdemServicoNaoEncontradaException::new);
        os.entregar();
        gateway.salvar(os);
    }
}