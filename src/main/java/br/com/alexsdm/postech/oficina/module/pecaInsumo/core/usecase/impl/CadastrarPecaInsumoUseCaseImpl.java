package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.out.PecaInsumoRepository;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.in.CadastrarPecaInsumoUseCase;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.input.CadastrarPecaInsumoInput;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.domain.entity.PecaInsumo;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Named
@RequiredArgsConstructor
public class CadastrarPecaInsumoUseCaseImpl implements CadastrarPecaInsumoUseCase {

    private final PecaInsumoRepository pecaInsumoRepository;

    @Override
    public PecaInsumo executar(CadastrarPecaInsumoInput input) {

        var peca = new PecaInsumo(
                UUID.randomUUID(),
                input.nome(),
                input.descricao(),
                input.codigoFabricante(),
                input.marca(),
                input.quantidadeEstoque(),
                input.precoCusto(),
                input.precoVenda(),
                input.categoria(),
                true,
                LocalDateTime.now(),
                null
        );
        return pecaInsumoRepository.salvar(peca);
    }
}
