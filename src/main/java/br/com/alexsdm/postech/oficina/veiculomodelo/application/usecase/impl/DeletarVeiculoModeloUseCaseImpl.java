package br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.impl;

import br.com.alexsdm.postech.oficina.veiculomodelo.application.gateway.VeiculoModeloGateway;
import br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.DeletarVeiculoModeloUseCase;
import br.com.alexsdm.postech.oficina.veiculomodelo.domain.exception.VeiculoModeloNaoEncontradoException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class DeletarVeiculoModeloUseCaseImpl implements DeletarVeiculoModeloUseCase {

    private final VeiculoModeloGateway gateway;

    @Override
    public void executar(Long id) {
        gateway.buscarPorId(id)
                .orElseThrow(VeiculoModeloNaoEncontradoException::new);
        gateway.deletar(id);
    }
}