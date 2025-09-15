package br.com.alexsdm.postech.oficina.ordemServico.application.usecase.impl;

import br.com.alexsdm.postech.oficina.ordemServico.application.gateway.OrdemServicoOrcamentoGateway;
import br.com.alexsdm.postech.oficina.ordemServico.application.gateway.OrdemServicoGateway;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.CriarOrdemServicoUseCase;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.dto.CriarOrdemServicoDTO;
import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.OrdemServico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarOrdemServicoUseCaseImpl implements CriarOrdemServicoUseCase {

    private final OrdemServicoOrcamentoGateway orcamentoGateway;
    private final OrdemServicoGateway ordemServicoGateway;

    @Override
    public OrdemServico executar(CriarOrdemServicoDTO dto) {
        var orcamento = orcamentoGateway.buscarPorId(dto.orcamentoId())
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado")); // TODO: Exceção específica

        var os = new OrdemServico(orcamento.getClienteId(), orcamento.getVeiculoId());
        // TODO: Mapear itens do orçamento para a OS
        return ordemServicoGateway.salvar(os);
    }
}
