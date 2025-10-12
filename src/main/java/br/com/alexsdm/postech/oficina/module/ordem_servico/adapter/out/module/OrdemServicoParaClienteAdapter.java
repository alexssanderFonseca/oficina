package br.com.alexsdm.postech.oficina.module.ordem_servico.adapter.out.module;

import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.OrdemServicoStatus;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteOrdemServicoPort;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.in.ListarOrdensServicoPeloIdClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrdemServicoParaClienteAdapter implements ClienteOrdemServicoPort {

    private final ListarOrdensServicoPeloIdClienteUseCase listarOrdensServicoConcluidasUseCase;

    @Override
    public List<OrdemServicoStatus> buscarStatusPorCliente(UUID clienteId) {

        return listarOrdensServicoConcluidasUseCase.executar(clienteId)
                .stream()
                .map(output -> new OrdemServicoStatus(
                        output.numeroOs(),
                        output.status()
                )).toList();
    }
}
