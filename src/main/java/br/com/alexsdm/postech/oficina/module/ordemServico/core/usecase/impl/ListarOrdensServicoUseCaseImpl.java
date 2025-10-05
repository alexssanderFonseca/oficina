package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordemServico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.port.in.ListarOrdensServicoUseCase;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.entity.OrdemServico;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarOrdensServicoUseCaseImpl implements ListarOrdensServicoUseCase {
    private final OrdemServicoRepository gateway;

    @Override
    public Page<OrdemServico> executar(Pageable pageable) { /* TODO: Implementar paginação no gateway */
        return Page.empty();
    }
}