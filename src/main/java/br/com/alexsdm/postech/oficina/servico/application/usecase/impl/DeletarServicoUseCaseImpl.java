package br.com.alexsdm.postech.oficina.servico.application.usecase.impl;

import br.com.alexsdm.postech.oficina.servico.application.gateway.ServicoGateway;
import br.com.alexsdm.postech.oficina.servico.application.usecase.BuscarServicoPorIdUseCase;
import br.com.alexsdm.postech.oficina.servico.application.usecase.DeletarServicoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarServicoUseCaseImpl implements DeletarServicoUseCase {

    private final BuscarServicoPorIdUseCase buscarServicoPorIdUseCase;
    private final ServicoGateway servicoGateway;

    @Override
    public void executar(Long id) {
        var servico = buscarServicoPorIdUseCase.executar(id);
        servico.inativar();
        servicoGateway.salvar(servico);
    }
}
