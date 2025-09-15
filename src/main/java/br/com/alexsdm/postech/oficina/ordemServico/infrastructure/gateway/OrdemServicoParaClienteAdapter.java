package br.com.alexsdm.postech.oficina.ordemServico.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteOrdemServicoGateway;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.OrdemServicoStatus;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.ListarOrdensServicoConcluidasUseCase; // Usando este como exemplo
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrdemServicoParaClienteAdapter implements ClienteOrdemServicoGateway {

    private final ListarOrdensServicoConcluidasUseCase listarOrdensServicoConcluidasUseCase; // TODO: Usar um use case que busque por clienteId

    @Override
    public List<OrdemServicoStatus> buscarStatusPorCliente(UUID clienteId) {
        // Lógica de exemplo, precisa de um use case que filtre por cliente
        return listarOrdensServicoConcluidasUseCase.executar().stream()
                .map(os -> new OrdemServicoStatus(os.getId(), os.getStatus().name()))
                .collect(Collectors.toList());
    }
}
