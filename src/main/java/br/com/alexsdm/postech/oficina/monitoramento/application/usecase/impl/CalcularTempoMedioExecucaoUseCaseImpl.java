package br.com.alexsdm.postech.oficina.monitoramento.application.usecase.impl;

import br.com.alexsdm.postech.oficina.monitoramento.application.gateway.MonitoramentoOrdemServicoGateway;
import br.com.alexsdm.postech.oficina.monitoramento.application.usecase.CalcularTempoMedioExecucaoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CalcularTempoMedioExecucaoUseCaseImpl implements CalcularTempoMedioExecucaoUseCase {

    private final MonitoramentoOrdemServicoGateway ordemServicoGateway;

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
        return (long) Math.ceil(mediaEmSegundos / 60.0); // Convertendo para minutos e arredondando para cima
    }
}
