package br.com.alexsdm.postech.oficina.orcamento.application.usecase;

import br.com.alexsdm.postech.oficina.orcamento.domain.entity.Orcamento;

public interface BuscarOrcamentoPorIdUseCase {
    Orcamento executar(Long id);
}
