package br.com.alexsdm.postech.oficina.orcamento.application.usecase;

public interface EnviarOrcamentoPdfUseCase {
    byte[] executar(Long id);
}
