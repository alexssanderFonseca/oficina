package br.com.alexsdm.postech.oficina.orcamento.core.port.out;

import br.com.alexsdm.postech.oficina.orcamento.core.entity.PecaInsumo;

import java.util.Optional;
import java.util.UUID;

public interface OrcamentoPecaInsumoPort {
    Optional<PecaInsumo> buscarPecaParaOrcamentoPorId(UUID id);
}
