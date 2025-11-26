package br.com.alexsdm.postech.oficina.orcamento.core.port.in;

import java.util.UUID;

public interface EnviarOrcamentoPdfUseCase {
    byte[] executar(UUID id);
}
