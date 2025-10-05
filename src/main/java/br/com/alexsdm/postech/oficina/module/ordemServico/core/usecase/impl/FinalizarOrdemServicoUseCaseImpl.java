package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordemServico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.port.in.FinalizarOrdemServicoUseCase;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.exception.OrdemServicoNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinalizarOrdemServicoUseCaseImpl implements FinalizarOrdemServicoUseCase {
    private final OrdemServicoRepository gateway;

    @Override
    public void executar(UUID id) {
        var os = gateway.buscarPorId(id)
                .orElseThrow(OrdemServicoNaoEncontradaException::new);
        os.finalizar();
        gateway.salvar(os);
    }
}