package br.com.alexsdm.postech.oficina.servico.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.ordemServico.application.gateway.OrdemServicoServicoGateway;
import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.servico.application.usecase.BuscarServicoPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ServicoParaOrdemServicoAdapter implements OrdemServicoServicoGateway {

    private final BuscarServicoPorIdUseCase buscarServicoPorIdUseCase;

    @Override
    public Optional<Servico> buscarServicoParaOsPorId(Long id) {
        try {
            var servico = buscarServicoPorIdUseCase.executar(id);
            return Optional.of(new Servico(servico.getId(), servico.getNome(), servico.getPreco()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
