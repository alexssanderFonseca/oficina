package br.com.alexsdm.postech.oficina.monitoramento.application.gateway;

import br.com.alexsdm.postech.oficina.monitoramento.domain.entity.OrdemServicoConcluida;
import java.util.List;

public interface MonitoramentoOrdemServicoGateway {
    List<OrdemServicoConcluida> buscarConcluidas();
}
