package br.com.alexsdm.postech.oficina.orcamento.core.port.in;

import br.com.alexsdm.postech.oficina.orcamento.core.usecase.input.CriarOrcamentoInput;

import java.util.UUID;

public interface CriarOrcamentoUseCase {
    UUID executar(CriarOrcamentoInput input);
}
