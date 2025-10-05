package br.com.alexsdm.postech.oficina.module.ordemServico.core.port.in;

import br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.entity.OrdemServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarOrdensServicoUseCase {
    Page<OrdemServico> executar(Pageable pageable);
}