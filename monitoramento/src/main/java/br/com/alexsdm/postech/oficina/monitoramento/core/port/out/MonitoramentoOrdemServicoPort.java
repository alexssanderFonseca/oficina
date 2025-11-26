package br.com.alexsdm.postech.oficina.monitoramento.core.port.out;

import br.com.alexsdm.postech.oficina.monitoramento.core.domain.entity.OrdemServicoConcluida;
import java.util.List;

public interface MonitoramentoOrdemServicoPort {
    List<OrdemServicoConcluida> buscarConcluidas();
}
