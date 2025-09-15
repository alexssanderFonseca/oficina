package br.com.alexsdm.postech.oficina.servico.application.usecase;

import br.com.alexsdm.postech.oficina.servico.domain.entity.Servico;

public interface BuscarServicoPorIdUseCase {
    Servico executar(Long id);
}
