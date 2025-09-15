package br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.ordemServico.application.gateway.OrdemServicoPecaInsumoGateway;
import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.BuscarPecaInsumoPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PecaInsumoParaOrdemServicoAdapter implements OrdemServicoPecaInsumoGateway {

    private final BuscarPecaInsumoPorIdUseCase buscarPecaInsumoPorIdUseCase;

    @Override
    public Optional<PecaInsumo> buscarPecaParaOsPorId(Long id) {
        try {
            var peca = buscarPecaInsumoPorIdUseCase.executar(id);
            return Optional.of(new PecaInsumo(peca.getId(), peca.getNome(), peca.getPrecoVenda()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
