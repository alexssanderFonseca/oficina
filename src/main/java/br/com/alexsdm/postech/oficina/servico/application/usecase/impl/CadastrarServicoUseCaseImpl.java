package br.com.alexsdm.postech.oficina.servico.application.usecase.impl;

import br.com.alexsdm.postech.oficina.servico.application.gateway.ServicoGateway;
import br.com.alexsdm.postech.oficina.servico.application.usecase.CadastrarServicoUseCase;
import br.com.alexsdm.postech.oficina.servico.application.usecase.input.CadastrarServicoInput;
import br.com.alexsdm.postech.oficina.servico.domain.entity.Servico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarServicoUseCaseImpl implements CadastrarServicoUseCase {

    private final ServicoGateway servicoGateway;

    @Override
    public Long executar(CadastrarServicoInput dto) {
        var servico = new Servico(
                null,
                dto.nome(),
                dto.descricao(),
                dto.preco(),
                dto.duracaoEstimada(),
                dto.categoria()
        );
        return servicoGateway.salvar(servico)
                .getId();
    }
}
