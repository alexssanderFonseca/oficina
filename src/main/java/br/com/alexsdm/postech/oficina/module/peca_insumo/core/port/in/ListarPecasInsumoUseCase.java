package br.com.alexsdm.postech.oficina.module.peca_insumo.core.port.in;

import br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.input.ListarPecasInsumoUseCaseInput;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.output.ListarPecasInsumoUseCaseOutput;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.pagination.Page;

public interface ListarPecasInsumoUseCase {
    Page<ListarPecasInsumoUseCaseOutput> executar(ListarPecasInsumoUseCaseInput input);
}
