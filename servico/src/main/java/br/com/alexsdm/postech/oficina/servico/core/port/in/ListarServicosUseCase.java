package br.com.alexsdm.postech.oficina.servico.core.port.in;

import br.com.alexsdm.postech.oficina.servico.core.domain.entity.Servico;

import java.util.List;

public interface ListarServicosUseCase {
    List<Servico> executar();
}
