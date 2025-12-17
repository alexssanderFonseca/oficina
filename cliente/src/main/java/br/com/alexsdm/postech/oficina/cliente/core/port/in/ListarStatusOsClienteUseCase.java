package br.com.alexsdm.postech.oficina.cliente.core.port.in;

import br.com.alexsdm.postech.oficina.cliente.core.domain.entity.OrdemServicoStatus;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.ListarStatusOsClienteInput;

import java.util.List;

public interface ListarStatusOsClienteUseCase {
    List<OrdemServicoStatus> executar(ListarStatusOsClienteInput input);
}
