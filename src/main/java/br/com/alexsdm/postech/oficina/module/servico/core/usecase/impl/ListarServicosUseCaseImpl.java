package br.com.alexsdm.postech.oficina.module.servico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.servico.core.port.out.ServicoRepository;
import br.com.alexsdm.postech.oficina.module.servico.core.port.in.ListarServicosUseCase;
import br.com.alexsdm.postech.oficina.module.servico.core.domain.entity.Servico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarServicosUseCaseImpl implements ListarServicosUseCase {

    private final ServicoRepository servicoRepository;

    @Override
    public List<Servico> executar() {
        return servicoRepository.listarTodos();
    }
}
