package br.com.alexsdm.postech.oficina.servico.application.usecase;

import br.com.alexsdm.postech.oficina.servico.application.usecase.input.AtualizarServicoInput;

public interface AtualizarServicoUseCase {
    void executar(AtualizarServicoInput dto);
}
