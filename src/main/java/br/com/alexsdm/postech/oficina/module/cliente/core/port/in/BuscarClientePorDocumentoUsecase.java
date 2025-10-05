package br.com.alexsdm.postech.oficina.module.cliente.core.port.in;

import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.output.BuscarClientePorDocumentoOutput;

public interface BuscarClientePorDocumentoUsecase {
    BuscarClientePorDocumentoOutput executar(String documento);
}
