package br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.impl;

import br.com.alexsdm.postech.oficina.veiculomodelo.application.gateway.VeiculoModeloGateway;
import br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.BuscarVeiculoModeloPorIdUseCase;
import br.com.alexsdm.postech.oficina.veiculomodelo.domain.entity.VeiculoModelo;
import br.com.alexsdm.postech.oficina.veiculomodelo.domain.exception.VeiculoModeloNaoEncontradoException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Named
@RequiredArgsConstructor
public class BuscarVeiculoModeloPorIdUseCaseImpl implements BuscarVeiculoModeloPorIdUseCase {

    private final VeiculoModeloGateway gateway;

    @Override
    public VeiculoModelo executar(Long id) {
        return gateway.buscarPorId(id).orElseThrow(VeiculoModeloNaoEncontradoException::new);
    }
}