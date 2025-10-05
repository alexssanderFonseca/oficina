package br.com.alexsdm.postech.oficina.module.cliente.core.port.in;

import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.AdicionarVeiculoInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.output.AdicionarVeiculoOutput;

public interface AdicionarVeiculoUseCase {
    AdicionarVeiculoOutput executar(AdicionarVeiculoInput input);
}
