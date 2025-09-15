package br.com.alexsdm.postech.oficina.ordemServico.application.usecase.impl;
import br.com.alexsdm.postech.oficina.ordemServico.application.gateway.OrdemServicoGateway;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.ListarOrdensServicoUseCase;
import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.OrdemServico;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class ListarOrdensServicoUseCaseImpl implements ListarOrdensServicoUseCase {
    private final OrdemServicoGateway gateway;
    @Override public Page<OrdemServico> executar(Pageable pageable) { /* TODO: Implementar paginação no gateway */ return Page.empty(); }
}