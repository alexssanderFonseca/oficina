package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.out.PecaInsumoRepository;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.in.BuscarPecaInsumoPorIdUseCase;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.output.BuscarPecaInsumoPorIdUseCaseOutput;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.domain.exception.PecaInsumoNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarPecaInsumoPorIdUseCaseImpl implements BuscarPecaInsumoPorIdUseCase {

    private final PecaInsumoRepository pecaInsumoRepository;

    @Override
    public BuscarPecaInsumoPorIdUseCaseOutput executar(UUID id) {
        var pecaInsumo = pecaInsumoRepository.buscarPorId(id)
                .orElseThrow(PecaInsumoNaoEncontradaException::new);

        return new BuscarPecaInsumoPorIdUseCaseOutput(
                pecaInsumo.getId(),
                pecaInsumo.getNome(),
                pecaInsumo.getDescricao(),
                pecaInsumo.getCodigoFabricante(),
                pecaInsumo.getMarca(),
                pecaInsumo.getQuantidadeEstoque(),
                pecaInsumo.getPrecoCusto(),
                pecaInsumo.getPrecoVenda(),
                pecaInsumo.getCategoria(),
                pecaInsumo.getAtivo(),
                pecaInsumo.getDataCadastro(),
                pecaInsumo.getDataAtualizacao()
        );
    }
}
