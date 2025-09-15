package br.com.alexsdm.postech.oficina.orcamento.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.orcamento.application.gateway.OrcamentoGateway;
import br.com.alexsdm.postech.oficina.orcamento.domain.entity.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.infrastructure.persistence.OrcamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrcamentoGatewayImpl implements OrcamentoGateway {

    private final OrcamentoRepository repository;
    private final OrcamentoMapper mapper;

    @Override
    public Orcamento salvar(Orcamento orcamento) {
        var entity = mapper.toEntity(orcamento);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Orcamento> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
