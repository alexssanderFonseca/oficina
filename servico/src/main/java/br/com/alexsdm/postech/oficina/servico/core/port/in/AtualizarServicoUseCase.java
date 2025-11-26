package br.com.alexsdm.postech.oficina.servico.core.port.in;

import br.com.alexsdm.postech.oficina.servico.core.usecase.input.AtualizarServicoInput;

public interface AtualizarServicoUseCase {
    void executar(AtualizarServicoInput dto);
}
