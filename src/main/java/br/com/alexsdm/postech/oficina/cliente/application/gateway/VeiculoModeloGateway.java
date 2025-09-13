package br.com.alexsdm.postech.oficina.cliente.application.gateway;

import br.com.alexsdm.postech.oficina.cliente.domain.entity.VeiculoModelo;

import java.util.Optional;

public interface VeiculoModeloGateway {
    Optional<VeiculoModelo> buscarPorId(Long id);
}
