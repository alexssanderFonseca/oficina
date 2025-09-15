package br.com.alexsdm.postech.oficina.ordemServico.application.usecase;

import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.OrdemServico;
import java.util.List;

public interface ListarOrdensServicoConcluidasUseCase {
    List<OrdemServico> executar();
}
