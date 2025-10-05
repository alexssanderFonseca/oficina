package br.com.alexsdm.postech.oficina.module.cliente.core.port.in;

import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.CadastrarClienteInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.output.CadastrarClienteOutput;

public interface CadastrarClienteUseCase {
    CadastrarClienteOutput executar(CadastrarClienteInput input);
}
