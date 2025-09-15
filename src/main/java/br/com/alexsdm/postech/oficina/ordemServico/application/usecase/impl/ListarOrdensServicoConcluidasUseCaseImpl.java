package br.com.alexsdm.postech.oficina.ordemServico.application.usecase.impl;

import br.com.alexsdm.postech.oficina.ordemServico.application.gateway.OrdemServicoGateway;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.ListarOrdensServicoConcluidasUseCase;
import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.OrdemServico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarOrdensServicoConcluidasUseCaseImpl implements ListarOrdensServicoConcluidasUseCase {

    private final OrdemServicoGateway ordemServicoGateway;

    @Override
    public List<OrdemServico> executar() {
        return ordemServicoGateway.buscarFinalizadas();
    }
}
