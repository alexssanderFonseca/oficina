package br.com.alexsdm.postech.oficina.ordemServico.application.usecase.impl;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.FinalizarOrdemServicoUseCase;
import br.com.alexsdm.postech.oficina.ordemServico.application.gateway.OrdemServicoGateway;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.BuscarOrdemServicoPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class FinalizarOrdemServicoUseCaseImpl implements FinalizarOrdemServicoUseCase {
    private final BuscarOrdemServicoPorIdUseCase buscarUseCase;
    private final OrdemServicoGateway gateway;
    @Override public void executar(Long id) { var os = buscarUseCase.executar(id); os.finalizar(); gateway.salvar(os); }
}