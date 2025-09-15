package br.com.alexsdm.postech.oficina.orcamento.application.gateway;

import br.com.alexsdm.postech.oficina.orcamento.domain.entity.PecaInsumo;

import java.util.Optional;

public interface OrcamentoPecaInsumoGateway {
    Optional<PecaInsumo> buscarPecaParaOrcamentoPorId(Long id);
}
