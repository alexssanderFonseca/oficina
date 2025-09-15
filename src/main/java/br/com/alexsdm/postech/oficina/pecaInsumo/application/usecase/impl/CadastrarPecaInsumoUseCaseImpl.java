package br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.impl;

import br.com.alexsdm.postech.oficina.pecaInsumo.application.gateway.PecaInsumoGateway;
import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.CadastrarPecaInsumoUseCase;
import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.dto.CadastrarPecaInsumoDTO;
import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.PecaInsumo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarPecaInsumoUseCaseImpl implements CadastrarPecaInsumoUseCase {

    private final PecaInsumoGateway pecaInsumoGateway;

    @Override
    public PecaInsumo executar(CadastrarPecaInsumoDTO dto) {
        // A lógica para buscar os modelos compatíveis precisará de um gateway para VeiculoModelo
        // Por enquanto, vou deixar a lista de modelos vazia.
        var peca = new PecaInsumo(
            null, dto.nome(), dto.descricao(), dto.codigoFabricante(), dto.marca(),
            new java.util.ArrayList<>(), dto.quantidadeEstoque(), dto.precoCusto(), dto.precoVenda(),
            dto.categoria(), true, java.time.LocalDateTime.now(), null
        );
        return pecaInsumoGateway.salvar(peca);
    }
}
