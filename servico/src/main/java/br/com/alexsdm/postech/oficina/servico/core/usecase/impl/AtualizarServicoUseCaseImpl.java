package br.com.alexsdm.postech.oficina.servico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.servico.core.port.out.ServicoRepository;
import br.com.alexsdm.postech.oficina.servico.core.port.in.AtualizarServicoUseCase;
import br.com.alexsdm.postech.oficina.servico.core.port.in.BuscarServicoPorIdUseCase;
import br.com.alexsdm.postech.oficina.servico.core.usecase.input.AtualizarServicoInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarServicoUseCaseImpl implements AtualizarServicoUseCase {

    private final BuscarServicoPorIdUseCase buscarServicoPorIdUseCase;
    private final ServicoRepository servicoRepository;

    @Override
    public void executar(AtualizarServicoInput input) {
        var servico = buscarServicoPorIdUseCase.executar(input.id());
        servico.atualizar(input.preco(), input.ativo());
        servicoRepository.salvar(servico);
    }
}
