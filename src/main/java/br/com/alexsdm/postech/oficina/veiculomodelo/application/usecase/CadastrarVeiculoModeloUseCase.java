package br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase;

import br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.dto.CadastrarVeiculoModeloInput;

public interface CadastrarVeiculoModeloUseCase {
    Long executar(CadastrarVeiculoModeloInput veiculoModelo);
}