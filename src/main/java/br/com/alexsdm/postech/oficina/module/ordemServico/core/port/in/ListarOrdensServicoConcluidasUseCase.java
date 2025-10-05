package br.com.alexsdm.postech.oficina.module.ordemServico.core.port.in;

import br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.entity.OrdemServico;

import java.util.List;

public interface ListarOrdensServicoConcluidasUseCase {
    List<OrdemServico> executar();
}
