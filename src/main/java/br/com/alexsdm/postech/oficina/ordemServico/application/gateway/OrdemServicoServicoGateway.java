package br.com.alexsdm.postech.oficina.ordemServico.application.gateway;

import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.Servico;
import java.util.Optional;

public interface OrdemServicoServicoGateway {
    Optional<Servico> buscarServicoParaOsPorId(Long id);
}
