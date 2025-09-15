package br.com.alexsdm.postech.oficina.servico.application.usecase;

import br.com.alexsdm.postech.oficina.servico.domain.entity.Servico;

import java.util.List;

public interface ListarServicosUseCase {
    List<Servico> executar();
}
