package br.com.alexsdm.postech.oficina.module.peca_insumo.core.port.in;

import java.util.UUID;

public interface DeletarPecaInsumoUseCase {
    void executar(UUID id);
}
