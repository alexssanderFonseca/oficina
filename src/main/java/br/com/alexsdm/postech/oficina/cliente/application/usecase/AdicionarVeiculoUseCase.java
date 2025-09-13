package br.com.alexsdm.postech.oficina.cliente.application.usecase;

import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.AdicionarVeiculoInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.AdicionarVeiculoOutput;

public interface AdicionarVeiculoUseCase {
    AdicionarVeiculoOutput executar(AdicionarVeiculoInput input);
}
