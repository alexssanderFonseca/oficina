package br.com.alexsdm.postech.oficina.orcamento.application.gateway;

import br.com.alexsdm.postech.oficina.orcamento.domain.entity.Servico;

import java.util.Optional;

public interface OrcamentoServicoGateway {
    Optional<Servico> buscarServicoParaOrcamentoPorId(Long id);
}
