package br.com.alexsdm.postech.oficina.ordemServico.application.usecase.impl;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.ExecutarOrdemServicoUseCase;
import br.com.alexsdm.postech.oficina.ordemServico.application.gateway.OrdemServicoGateway;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.BuscarOrdemServicoPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class ExecutarOrdemServicoUseCaseImpl implements ExecutarOrdemServicoUseCase {
    private final BuscarOrdemServicoPorIdUseCase buscarUseCase;
    private final OrdemServicoGateway gateway;
    @Override public void executar(Long osId, Long orcamentoId) { var os = buscarUseCase.executar(osId); /* TODO: Lógica de negócio com orçamento */ gateway.salvar(os); }
}