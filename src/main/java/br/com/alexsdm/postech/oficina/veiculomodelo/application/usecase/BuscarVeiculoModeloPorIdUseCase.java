package br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase;

import br.com.alexsdm.postech.oficina.veiculomodelo.domain.entity.VeiculoModelo;

public interface BuscarVeiculoModeloPorIdUseCase {
    VeiculoModelo executar(Long id);
}