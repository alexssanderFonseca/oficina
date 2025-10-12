package br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.in;

import java.util.UUID;

public interface FinalizarOrdemServicoUseCase {
    void executar(UUID id);
}