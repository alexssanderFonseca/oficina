package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.in;

import java.util.UUID;

public interface DeletarPecaInsumoUseCase {
    void executar(UUID id);
}
