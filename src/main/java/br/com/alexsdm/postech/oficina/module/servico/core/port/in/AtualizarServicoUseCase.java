package br.com.alexsdm.postech.oficina.module.servico.core.port.in;

import br.com.alexsdm.postech.oficina.module.servico.core.usecase.input.AtualizarServicoInput;

public interface AtualizarServicoUseCase {
    void executar(AtualizarServicoInput dto);
}
