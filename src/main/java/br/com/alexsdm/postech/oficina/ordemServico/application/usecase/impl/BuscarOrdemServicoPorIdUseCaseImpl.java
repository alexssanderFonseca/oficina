package br.com.alexsdm.postech.oficina.ordemServico.application.usecase.impl;
import br.com.alexsdm.postech.oficina.ordemServico.application.gateway.OrdemServicoGateway;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.BuscarOrdemServicoPorIdUseCase;
import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.OrdemServico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class BuscarOrdemServicoPorIdUseCaseImpl implements BuscarOrdemServicoPorIdUseCase {
    private final OrdemServicoGateway gateway;
    @Override public OrdemServico executar(Long id) { return gateway.buscarPorId(id).orElseThrow(() -> new RuntimeException("OS não encontrada")); }
}