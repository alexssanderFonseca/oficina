package br.com.alexsdm.postech.oficina.module.orcamento.adapter.out.persistence;

import br.com.alexsdm.postech.oficina.module.orcamento.adapter.out.persistence.entity.OrcamentoJpaRepository;
import br.com.alexsdm.postech.oficina.module.orcamento.adapter.out.persistence.mapper.OrcamentoMapper;
import br.com.alexsdm.postech.oficina.module.orcamento.core.entity.Orcamento;
import br.com.alexsdm.postech.oficina.module.orcamento.core.port.out.OrcamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrcamentoJpaRepositoryAdapter implements OrcamentoRepository {

    private final OrcamentoJpaRepository repository;
    private final OrcamentoMapper mapper;

    @Override
    public Orcamento salvar(Orcamento orcamento) {
        var entity = mapper.toEntity(orcamento);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Orcamento> buscarPorId(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
