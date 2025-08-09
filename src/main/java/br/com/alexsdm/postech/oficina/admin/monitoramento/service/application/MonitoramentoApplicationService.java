package br.com.alexsdm.postech.oficina.admin.monitoramento.service.application;


import br.com.alexsdm.postech.oficina.admin.monitoramento.service.application.output.TempoMedioExecucaoOutput;
import br.com.alexsdm.postech.oficina.ordemServico.service.application.OrdemServicoApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoramentoApplicationService {

    private final OrdemServicoApplicationService ordemServicoApplicationService;

    public TempoMedioExecucaoOutput obterTempoMedioExecucacaoServicos() {
        var tempoExecucaoEmSegundos = ordemServicoApplicationService.getTempoMedioExecucaoServicosEmSegundos();
        var tempoExecucaoEmMinutos = (long) (Math.ceil(tempoExecucaoEmSegundos / 60));
        return new TempoMedioExecucaoOutput(tempoExecucaoEmMinutos);
    }
}
