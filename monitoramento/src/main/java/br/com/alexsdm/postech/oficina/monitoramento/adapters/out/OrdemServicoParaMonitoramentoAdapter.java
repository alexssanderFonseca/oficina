package br.com.alexsdm.postech.oficina.monitoramento.adapters.out;

import br.com.alexsdm.postech.oficina.monitoramento.core.domain.entity.OrdemServicoConcluida;
import br.com.alexsdm.postech.oficina.monitoramento.core.port.out.MonitoramentoOrdemServicoPort;
import br.com.alexsdm.postech.oficina.ordem_servico.core.port.in.ListarOrdensServicoConcluidasUseCase;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Named
@RequiredArgsConstructor
public class OrdemServicoParaMonitoramentoAdapter implements MonitoramentoOrdemServicoPort {

    private final ListarOrdensServicoConcluidasUseCase listarOrdensServicoConcluidasUseCase;

    @Override
    public List<OrdemServicoConcluida> buscarConcluidas() {
        return listarOrdensServicoConcluidasUseCase.executar().stream()
                .map(os -> new OrdemServicoConcluida(
                        os.dataInicioExecucao(),
                        os.dataFinalizacao()
                ))
                .toList();
    }
}
