package br.com.alexsdm.postech.oficina.servico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.servico.core.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.servico.core.domain.exception.ServicoNaoEncontradoException;
import br.com.alexsdm.postech.oficina.servico.core.port.in.BuscarServicoPorIdUseCase;
import br.com.alexsdm.postech.oficina.servico.core.port.out.ServicoRepository;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Named
@RequiredArgsConstructor
public class BuscarServicoPorIdUseCaseImpl implements BuscarServicoPorIdUseCase {

    private final ServicoRepository servicoRepository;

    @Override
    public Servico executar(UUID id) {
        return servicoRepository.buscarPorId(id)
                .orElseThrow(ServicoNaoEncontradoException::new);
    }
}
