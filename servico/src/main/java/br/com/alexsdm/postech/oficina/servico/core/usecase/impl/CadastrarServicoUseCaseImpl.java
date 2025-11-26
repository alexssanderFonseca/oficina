package br.com.alexsdm.postech.oficina.servico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.servico.core.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.servico.core.port.in.CadastrarServicoUseCase;
import br.com.alexsdm.postech.oficina.servico.core.port.out.ServicoRepository;
import br.com.alexsdm.postech.oficina.servico.core.usecase.input.CadastrarServicoInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CadastrarServicoUseCaseImpl implements CadastrarServicoUseCase {

    private final ServicoRepository servicoRepository;

    @Override
    public UUID executar(CadastrarServicoInput input) {
        var servico = new Servico(
                UUID.randomUUID(),
                input.nome(),
                input.descricao(),
                input.preco(),
                input.duracaoEstimada(),
                input.categoria()
        );
        return servicoRepository.salvar(servico)
                .getId();
    }
}
