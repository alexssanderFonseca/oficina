package br.com.alexsdm.postech.oficina.servico.application.usecase.impl;

import br.com.alexsdm.postech.oficina.servico.application.gateway.ServicoGateway;
import br.com.alexsdm.postech.oficina.servico.application.usecase.BuscarServicoPorIdUseCase;
import br.com.alexsdm.postech.oficina.servico.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.servico.domain.exception.ServicoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarServicoPorIdUseCaseImpl implements BuscarServicoPorIdUseCase {

    private final ServicoGateway servicoGateway;

    @Override
    public Servico executar(Long id) {
        return servicoGateway.buscarPorId(id)
                .orElseThrow(ServicoNaoEncontradoException::new);
    }
}
