package br.com.alexsdm.postech.oficina.servico.application.usecase.impl;

import br.com.alexsdm.postech.oficina.servico.application.gateway.ServicoGateway;
import br.com.alexsdm.postech.oficina.servico.application.usecase.ListarServicosUseCase;
import br.com.alexsdm.postech.oficina.servico.domain.entity.Servico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarServicosUseCaseImpl implements ListarServicosUseCase {

    private final ServicoGateway servicoGateway;

    @Override
    public List<Servico> executar() {
        return servicoGateway.listarTodos();
    }
}
