package br.com.alexsdm.postech.oficina.orcamento.adapter.out.gateway;

import br.com.alexsdm.postech.oficina.orcamento.core.port.out.OrcamentoPecaInsumoPort;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.peca_insumo.core.port.in.BuscarPecaInsumoPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PecaInsumoParaOrcamentoAdapter implements OrcamentoPecaInsumoPort {

    private final BuscarPecaInsumoPorIdUseCase buscarPecaInsumoPorIdUseCase;

    @Override
    public Optional<PecaInsumo> buscarPecaParaOrcamentoPorId(UUID id) {
        var buscarPecaInsumoPorIdUseCaseOutput= buscarPecaInsumoPorIdUseCase.executar(id);
        return Optional.of(new PecaInsumo(buscarPecaInsumoPorIdUseCaseOutput.id(),
                buscarPecaInsumoPorIdUseCaseOutput.nome(),
                buscarPecaInsumoPorIdUseCaseOutput.descricao(),
                buscarPecaInsumoPorIdUseCaseOutput.precoVenda()));
    }
}
