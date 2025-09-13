package br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.gateway.impl;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.VeiculoModeloGateway;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.VeiculoModelo;
import br.com.alexsdm.postech.oficina.veiculomodelo.repository.VeiculoModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VeiculoModeloGatewayImpl implements VeiculoModeloGateway {

    private final VeiculoModeloRepository veiculoModeloRepository;

    @Override
    public Optional<VeiculoModelo> buscarPorId(Long id) {
        return veiculoModeloRepository.findById(id)
                .map(jpaEntity -> new VeiculoModelo(jpaEntity.getMarca(), jpaEntity.getModelo()));
    }
}
