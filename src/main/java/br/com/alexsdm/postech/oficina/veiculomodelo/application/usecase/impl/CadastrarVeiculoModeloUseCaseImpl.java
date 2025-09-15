package br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.impl;

import br.com.alexsdm.postech.oficina.veiculomodelo.application.gateway.VeiculoModeloGateway;
import br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.CadastrarVeiculoModeloUseCase;
import br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.dto.CadastrarVeiculoModeloInput;
import br.com.alexsdm.postech.oficina.veiculomodelo.domain.entity.VeiculoModelo;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class CadastrarVeiculoModeloUseCaseImpl implements CadastrarVeiculoModeloUseCase {

    private final VeiculoModeloGateway gateway;

    @Override
    public Long executar(CadastrarVeiculoModeloInput input) {
        var veiculoModelo = new VeiculoModelo(
                null,
                input.marca(),
                input.modelo(),
                input.anoInicio(),
                input.anoFim(),
                input.tipo()
        );
        return gateway.salvar(veiculoModelo)
                .getId();
    }
}