package br.com.alexsdm.postech.oficina.cliente.application.usecase;

import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.CadastrarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.CadastrarClienteOutput;

public interface CadastrarClienteUseCase {
    CadastrarClienteOutput executar(CadastrarClienteInput input);
}
