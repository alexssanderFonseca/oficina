package br.com.alexsdm.postech.oficina.module.servico.core.port.in;

import java.util.UUID;

public interface DeletarServicoUseCase {
    void executar(UUID id);
}
