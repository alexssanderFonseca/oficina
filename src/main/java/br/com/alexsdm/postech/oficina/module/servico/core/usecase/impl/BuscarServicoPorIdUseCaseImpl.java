package br.com.alexsdm.postech.oficina.module.servico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.servico.core.port.out.ServicoRepository;
import br.com.alexsdm.postech.oficina.module.servico.core.port.in.BuscarServicoPorIdUseCase;
import br.com.alexsdm.postech.oficina.module.servico.core.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.module.servico.core.domain.exception.ServicoNaoEncontradoException;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
