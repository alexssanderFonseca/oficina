package br.com.alexsdm.postech.oficina.ordemServico.application.gateway;

import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.Orcamento;
import java.util.Optional;

public interface OrdemServicoOrcamentoGateway {
    Optional<Orcamento> buscarPorId(Long id);
}
