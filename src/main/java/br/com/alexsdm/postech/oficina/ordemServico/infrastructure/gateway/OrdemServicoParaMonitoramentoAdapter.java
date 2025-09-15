package br.com.alexsdm.postech.oficina.ordemServico.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.monitoramento.application.gateway.MonitoramentoOrdemServicoGateway;
import br.com.alexsdm.postech.oficina.monitoramento.domain.entity.OrdemServicoConcluida;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.ListarOrdensServicoConcluidasUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrdemServicoParaMonitoramentoAdapter implements MonitoramentoOrdemServicoGateway {

    private final ListarOrdensServicoConcluidasUseCase listarOrdensServicoConcluidasUseCase;

    @Override
    public List<OrdemServicoConcluida> buscarConcluidas() {
        return listarOrdensServicoConcluidasUseCase.executar().stream()
                .map(os -> new OrdemServicoConcluida(
                        os.getDataInicioDaExecucao(),
                        os.getDataFinalizacao()
                ))
                .collect(Collectors.toList());
    }
}
