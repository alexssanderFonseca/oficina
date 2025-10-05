package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.out.PecaInsumoRepository;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.in.ListarPecasInsumoUseCase;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.input.ListarPecasInsumoUseCaseInput;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.output.ListarPecasInsumoUseCaseOutput;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.domain.pagination.Page;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class ListarPecasInsumoUseCaseImpl implements ListarPecasInsumoUseCase {

    private final PecaInsumoRepository pecaInsumoRepository;

    @Override
    public Page<ListarPecasInsumoUseCaseOutput> executar(ListarPecasInsumoUseCaseInput input) {
        var pecaInsumosPage = pecaInsumoRepository.listarTodos(input.pagina(), input.quantidade());
        var output = pecaInsumosPage.conteudo()
                .stream()
                .map(pecaInsumo -> new ListarPecasInsumoUseCaseOutput(
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
                )).toList();

        return new Page<>(output, pecaInsumosPage.totalElementos(), pecaInsumosPage.pagina());
    }
}
