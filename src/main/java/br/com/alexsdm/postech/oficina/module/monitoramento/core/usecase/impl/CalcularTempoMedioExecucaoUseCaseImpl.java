package br.com.alexsdm.postech.oficina.module.monitoramento.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.monitoramento.core.port.out.MonitoramentoOrdemServicoPort;
import br.com.alexsdm.postech.oficina.module.monitoramento.core.port.in.CalcularTempoMedioExecucaoUseCase;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Named
@RequiredArgsConstructor
public class CalcularTempoMedioExecucaoUseCaseImpl implements CalcularTempoMedioExecucaoUseCase {

    private final MonitoramentoOrdemServicoPort ordemServicoGateway;

    @Override
    public Long executar() {
        var ordensConcluidas = ordemServicoGateway.buscarConcluidas();

        if (ordensConcluidas.isEmpty()) {
            return 0L;
        }

        long totalSegundos = ordensConcluidas.stream()
                .filter(os -> os.dataInicioDaExecucao() != null && os.dataFinalizacao() != null)
                .mapToLong(os -> Duration.between(os.dataInicioDaExecucao(), os.dataFinalizacao()).toSeconds())
                .sum();

        long mediaEmSegundos = totalSegundos / ordensConcluidas.size();
        return (long) Math.ceil(mediaEmSegundos / 60.0);
    }
}
