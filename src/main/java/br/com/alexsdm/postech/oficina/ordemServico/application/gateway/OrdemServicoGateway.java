package br.com.alexsdm.postech.oficina.ordemServico.application.gateway;

import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.OrdemServico;
import java.util.List;
import java.util.Optional;

public interface OrdemServicoGateway {
    OrdemServico salvar(OrdemServico ordemServico);
    Optional<OrdemServico> buscarPorId(Long id);
    List<OrdemServico> buscarFinalizadas();
}
