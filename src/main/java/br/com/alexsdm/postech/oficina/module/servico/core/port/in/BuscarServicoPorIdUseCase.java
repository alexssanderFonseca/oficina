package br.com.alexsdm.postech.oficina.module.servico.core.port.in;

import br.com.alexsdm.postech.oficina.module.servico.core.domain.entity.Servico;

import java.util.UUID;

public interface BuscarServicoPorIdUseCase {
    Servico executar(UUID id);
}
