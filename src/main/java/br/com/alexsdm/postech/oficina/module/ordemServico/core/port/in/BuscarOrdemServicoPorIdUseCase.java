package br.com.alexsdm.postech.oficina.module.ordemServico.core.port.in;

import br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.output.BuscarOrdemServicoOutput;

import java.util.UUID;

public interface BuscarOrdemServicoPorIdUseCase {
    BuscarOrdemServicoOutput executar(UUID id);
}