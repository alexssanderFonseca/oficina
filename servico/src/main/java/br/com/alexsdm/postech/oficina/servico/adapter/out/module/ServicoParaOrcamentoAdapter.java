package br.com.alexsdm.postech.oficina.servico.adapter.out.module;

import br.com.alexsdm.postech.oficina.orcamento.core.entity.Servico;
import br.com.alexsdm.postech.oficina.orcamento.core.port.out.OrcamentoServicoPort;
import br.com.alexsdm.postech.oficina.servico.core.port.in.BuscarServicoPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ServicoParaOrcamentoAdapter implements OrcamentoServicoPort {

    private final BuscarServicoPorIdUseCase buscarServicoPorIdUseCase;

    @Override
    public Optional<Servico> buscarServicoParaOrcamentoPorId(UUID id) {
        try {
            var servico = buscarServicoPorIdUseCase.executar(id);
            return Optional.of(new Servico(servico.getId(),
                    servico.getNome(),
                    servico.getPreco(),
                    servico.getDescricao()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
