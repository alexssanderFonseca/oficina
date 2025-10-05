package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordemServico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.port.in.ListarOrdensServicoConcluidasUseCase;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.entity.OrdemServico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarOrdensServicoConcluidasUseCaseImpl implements ListarOrdensServicoConcluidasUseCase {

    private final OrdemServicoRepository ordemServicoRepository;

    @Override
    public List<OrdemServico> executar() {
        return ordemServicoRepository.buscarFinalizadas();
    }
}
