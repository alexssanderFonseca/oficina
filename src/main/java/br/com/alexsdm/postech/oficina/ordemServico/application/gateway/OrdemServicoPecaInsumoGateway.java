package br.com.alexsdm.postech.oficina.ordemServico.application.gateway;

import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.PecaInsumo;
import java.util.Optional;

public interface OrdemServicoPecaInsumoGateway {
    Optional<PecaInsumo> buscarPecaParaOsPorId(Long id);
}
