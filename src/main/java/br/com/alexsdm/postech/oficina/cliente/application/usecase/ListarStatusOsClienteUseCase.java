package br.com.alexsdm.postech.oficina.cliente.application.usecase;

import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.ListarStatusOsClienteInput;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.OrdemServicoStatus;

import java.util.List;

public interface ListarStatusOsClienteUseCase {
    List<OrdemServicoStatus> executar(ListarStatusOsClienteInput input);
}
