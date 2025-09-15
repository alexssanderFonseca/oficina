package br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.orcamento.application.gateway.OrcamentoPecaInsumoGateway;
import br.com.alexsdm.postech.oficina.orcamento.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.BuscarPecaInsumoPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PecaInsumoParaOrcamentoAdapter implements OrcamentoPecaInsumoGateway {

    private final BuscarPecaInsumoPorIdUseCase buscarPecaInsumoPorIdUseCase;

    @Override
    public Optional<PecaInsumo> buscarPecaParaOrcamentoPorId(Long id) {
        try {
            var peca = buscarPecaInsumoPorIdUseCase.executar(id);
            return Optional.of(new PecaInsumo(peca.getId(), peca.getNome(), peca.getPrecoVenda()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
