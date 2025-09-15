package br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteVeiculoModeloGateway;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.VeiculoModelo;
import br.com.alexsdm.postech.oficina.veiculomodelo.application.gateway.VeiculoModeloGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteVeiculoModeloGatewayImpl implements ClienteVeiculoModeloGateway {

    private final VeiculoModeloGateway veiculoModeloGateway;

    @Override
    public Optional<VeiculoModelo> buscarPorId(Long id) {
        var veiculoModeloExternoOptional = veiculoModeloGateway.buscarPorId(id);
        return veiculoModeloExternoOptional
                .map(veiculoModeloExterno ->
                        new VeiculoModelo(veiculoModeloExterno.getId(),
                                veiculoModeloExterno.getMarca(),
                                veiculoModeloExterno.getModelo()));
    }
}
