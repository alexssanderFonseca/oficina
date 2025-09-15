package br.com.alexsdm.postech.oficina.orcamento.application.gateway;

import br.com.alexsdm.postech.oficina.orcamento.domain.entity.Orcamento;

import java.util.Optional;

public interface OrcamentoGateway {
    Orcamento salvar(Orcamento orcamento);

    Optional<Orcamento> buscarPorId(Long id);
}
