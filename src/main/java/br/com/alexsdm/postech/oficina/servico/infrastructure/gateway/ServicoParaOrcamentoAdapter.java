package br.com.alexsdm.postech.oficina.servico.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.orcamento.application.gateway.OrcamentoServicoGateway;
import br.com.alexsdm.postech.oficina.orcamento.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.servico.application.usecase.BuscarServicoPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ServicoParaOrcamentoAdapter implements OrcamentoServicoGateway {

    private final BuscarServicoPorIdUseCase buscarServicoPorIdUseCase;

    @Override
    public Optional<Servico> buscarServicoParaOrcamentoPorId(Long id) {
        try {
            var servico = buscarServicoPorIdUseCase.executar(id);
            return Optional.of(new Servico(servico.getId(), servico.getNome(), servico.getPreco()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
