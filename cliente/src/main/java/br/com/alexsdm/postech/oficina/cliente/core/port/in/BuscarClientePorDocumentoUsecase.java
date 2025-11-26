package br.com.alexsdm.postech.oficina.cliente.core.port.in;

import br.com.alexsdm.postech.oficina.cliente.core.usecase.output.BuscarClientePorDocumentoOutput;

public interface BuscarClientePorDocumentoUsecase {
    BuscarClientePorDocumentoOutput executar(String documento);
}
