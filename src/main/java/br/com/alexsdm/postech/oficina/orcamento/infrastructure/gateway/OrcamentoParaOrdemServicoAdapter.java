package br.com.alexsdm.postech.oficina.orcamento.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.ordemServico.application.gateway.OrdemServicoOrcamentoGateway;
import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.application.usecase.BuscarOrcamentoPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrcamentoParaOrdemServicoAdapter implements OrdemServicoOrcamentoGateway {

    private final BuscarOrcamentoPorIdUseCase buscarOrcamentoPorIdUseCase;

    @Override
    public Optional<Orcamento> buscarPorId(Long id) {
        try {
            var orcamento = buscarOrcamentoPorIdUseCase.executar(id);
            var orcamentoOS = new Orcamento();
            orcamentoOS.setId(orcamento.getId());
            orcamentoOS.setClienteId(orcamento.getClienteId());
            orcamentoOS.setVeiculoId(orcamento.getVeiculoId());
            // TODO: Mapear itens
            return Optional.of(orcamentoOS);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
