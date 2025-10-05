package br.com.alexsdm.postech.oficina.module.orcamento.core.port.in;

import java.util.UUID;

public interface AprovarOrcamentoUseCase {
    void executar(UUID id);
}
