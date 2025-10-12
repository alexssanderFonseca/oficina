package br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.in;

import java.util.UUID;

public interface ExecutarOrdemServicoUseCase {
    void executar(UUID osId, UUID orcamentoId);
}