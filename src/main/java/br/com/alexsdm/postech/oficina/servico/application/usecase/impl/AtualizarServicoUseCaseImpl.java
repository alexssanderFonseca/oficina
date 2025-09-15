package br.com.alexsdm.postech.oficina.servico.application.usecase.impl;

import br.com.alexsdm.postech.oficina.servico.application.gateway.ServicoGateway;
import br.com.alexsdm.postech.oficina.servico.application.usecase.AtualizarServicoUseCase;
import br.com.alexsdm.postech.oficina.servico.application.usecase.BuscarServicoPorIdUseCase;
import br.com.alexsdm.postech.oficina.servico.application.usecase.input.AtualizarServicoInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarServicoUseCaseImpl implements AtualizarServicoUseCase {

    private final BuscarServicoPorIdUseCase buscarServicoPorIdUseCase;
    private final ServicoGateway servicoGateway;

    @Override
    public void executar(AtualizarServicoInput input) {
        var servico = buscarServicoPorIdUseCase.executar(input.id());
        servico.atualizar(input.preco(), input.ativo());
        servicoGateway.salvar(servico);
    }
}
