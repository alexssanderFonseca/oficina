package br.com.alexsdm.postech.oficina.orcamento.application.usecase.impl;

import br.com.alexsdm.postech.oficina.orcamento.application.usecase.BuscarOrcamentoPorIdUseCase;
import br.com.alexsdm.postech.oficina.orcamento.application.usecase.EnviarOrcamentoPdfUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnviarOrcamentoPdfUseCaseImpl implements EnviarOrcamentoPdfUseCase {

    private final BuscarOrcamentoPorIdUseCase buscarOrcamentoPorIdUseCase;

    @Override
    public byte[] executar(Long id) {
        var orcamento = buscarOrcamentoPorIdUseCase.executar(id);
        // TODO: Implementar a lógica de geração de PDF aqui
        // Por enquanto, retorna um array de bytes vazio.
        return new byte[0];
    }
}
