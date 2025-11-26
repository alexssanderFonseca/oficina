package br.com.alexsdm.postech.oficina.orcamento.core.port.in;

import br.com.alexsdm.postech.oficina.orcamento.core.usecase.output.BuscarOrcamentoPorIdOutput;

import java.util.UUID;

public interface BuscarOrcamentoPorIdUseCase {
    BuscarOrcamentoPorIdOutput executar(UUID id);
}
