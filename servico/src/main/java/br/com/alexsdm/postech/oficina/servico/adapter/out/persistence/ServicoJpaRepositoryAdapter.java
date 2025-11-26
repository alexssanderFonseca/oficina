package br.com.alexsdm.postech.oficina.servico.adapter.out.persistence;

import br.com.alexsdm.postech.oficina.servico.adapter.out.persistence.entity.ServicoJpaRepository;
import br.com.alexsdm.postech.oficina.servico.adapter.out.persistence.mapper.ServicoRepositoryMapper;
import br.com.alexsdm.postech.oficina.servico.core.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.servico.core.port.out.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ServicoJpaRepositoryAdapter implements ServicoRepository {

    private final ServicoJpaRepository repository;
    private final ServicoRepositoryMapper mapper;

    @Override
    public Servico salvar(Servico servico) {
        var entity = mapper.toEntity(servico);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Servico> buscarPorId(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Servico> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
