package br.com.alexsdm.postech.oficina.ordemServico.application.usecase.impl;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.EntregarOrdemServicoUseCase;
import br.com.alexsdm.postech.oficina.ordemServico.application.gateway.OrdemServicoGateway;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.BuscarOrdemServicoPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class EntregarOrdemServicoUseCaseImpl implements EntregarOrdemServicoUseCase {
    private final BuscarOrdemServicoPorIdUseCase buscarUseCase;
    private final OrdemServicoGateway gateway;
    @Override public void executar(Long id) { var os = buscarUseCase.executar(id); os.entregar(); gateway.salvar(os); }
}