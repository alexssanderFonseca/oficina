package br.com.alexsdm.postech.oficina.module.cliente.core.port.in;

import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.OrdemServicoStatus;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.ListarStatusOsClienteInput;

import java.util.List;

public interface ListarStatusOsClienteUseCase {
    List<OrdemServicoStatus> executar(ListarStatusOsClienteInput input);
}
