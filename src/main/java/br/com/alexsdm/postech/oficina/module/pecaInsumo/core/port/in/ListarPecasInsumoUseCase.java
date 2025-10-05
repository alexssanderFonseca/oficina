package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.in;

import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.input.ListarPecasInsumoUseCaseInput;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.output.ListarPecasInsumoUseCaseOutput;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.domain.pagination.Page;

public interface ListarPecasInsumoUseCase {
    Page<ListarPecasInsumoUseCaseOutput> executar(ListarPecasInsumoUseCaseInput input);
}
