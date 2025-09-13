package br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.OrdemServicoGateway;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.OrdemServicoStatus;
import br.com.alexsdm.postech.oficina.ordemServico.service.application.OrdemServicoApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrdemServicoGatewayImpl implements OrdemServicoGateway {

    private final OrdemServicoApplicationService ordemServicoApplicationService;

    @Override
    public List<OrdemServicoStatus> buscarStatusPorCliente(UUID clienteId) {
        var ordensDeServico = ordemServicoApplicationService.statusByCliente(clienteId);

        return ordensDeServico.stream()
                .map(os -> new OrdemServicoStatus(os.getId(), os.getStatus().name()))
                .collect(Collectors.toList());
    }
}
