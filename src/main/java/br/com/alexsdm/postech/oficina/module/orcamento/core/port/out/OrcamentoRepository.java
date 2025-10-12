package br.com.alexsdm.postech.oficina.module.orcamento.core.port.out;

import br.com.alexsdm.postech.oficina.module.orcamento.core.entity.Orcamento;

import java.util.Optional;
import java.util.UUID;

public interface OrcamentoRepository {
    Orcamento salvar(Orcamento orcamento);

    Optional<Orcamento> buscarPorId(UUID id);
}
