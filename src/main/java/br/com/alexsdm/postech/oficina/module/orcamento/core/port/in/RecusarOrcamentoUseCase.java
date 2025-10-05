package br.com.alexsdm.postech.oficina.module.orcamento.core.port.in;

import java.util.UUID;

public interface RecusarOrcamentoUseCase {
    void executar(UUID id);
}
