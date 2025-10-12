package br.com.alexsdm.postech.oficina.module.monitoramento.adapters.out;

import br.com.alexsdm.postech.oficina.module.monitoramento.core.domain.entity.OrdemServicoConcluida;
import br.com.alexsdm.postech.oficina.module.monitoramento.core.port.out.MonitoramentoOrdemServicoPort;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.in.ListarOrdensServicoConcluidasUseCase;
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
                .collect(Collectors.toList());
    }
}
