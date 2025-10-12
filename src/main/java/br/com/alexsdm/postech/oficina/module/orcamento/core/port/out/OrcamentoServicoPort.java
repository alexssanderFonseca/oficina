package br.com.alexsdm.postech.oficina.module.orcamento.core.port.out;

import br.com.alexsdm.postech.oficina.module.orcamento.core.entity.Servico;

import java.util.Optional;
import java.util.UUID;

public interface OrcamentoServicoPort {
    Optional<Servico> buscarServicoParaOrcamentoPorId(UUID id);
}
