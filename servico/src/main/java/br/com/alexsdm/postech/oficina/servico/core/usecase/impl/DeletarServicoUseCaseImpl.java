package br.com.alexsdm.postech.oficina.servico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.servico.core.port.out.ServicoRepository;
import br.com.alexsdm.postech.oficina.servico.core.port.in.BuscarServicoPorIdUseCase;
import br.com.alexsdm.postech.oficina.servico.core.port.in.DeletarServicoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletarServicoUseCaseImpl implements DeletarServicoUseCase {

    private final BuscarServicoPorIdUseCase buscarServicoPorIdUseCase;
    private final ServicoRepository servicoRepository;

    @Override
    public void executar(UUID id) {
        var servico = buscarServicoPorIdUseCase.executar(id);
        servico.inativar();
        servicoRepository.salvar(servico);
    }
}
