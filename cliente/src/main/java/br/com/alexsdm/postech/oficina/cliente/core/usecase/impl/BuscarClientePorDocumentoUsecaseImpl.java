package br.com.alexsdm.postech.oficina.cliente.core.usecase.impl;


import br.com.alexsdm.postech.oficina.cliente.core.port.in.BuscarClientePorDocumentoUsecase;
import br.com.alexsdm.postech.oficina.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.output.BuscarClientePorDocumentoOutput;
import br.com.alexsdm.postech.oficina.cliente.core.domain.exception.ClienteNaoEncontradoException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class BuscarClientePorDocumentoUsecaseImpl implements BuscarClientePorDocumentoUsecase {

    private final ClienteRepository clienteRepository;

    @Override
    public BuscarClientePorDocumentoOutput executar(String documento) {
        return clienteRepository.buscarPorDocumento(documento)
                .map(BuscarClientePorDocumentoOutput::toOutput)
                .orElseThrow(ClienteNaoEncontradoException::new);
    }


}
