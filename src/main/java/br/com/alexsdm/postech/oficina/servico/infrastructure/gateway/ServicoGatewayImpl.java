package br.com.alexsdm.postech.oficina.servico.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.servico.application.gateway.ServicoGateway;
import br.com.alexsdm.postech.oficina.servico.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.servico.infrastructure.gateway.mapper.ServicoGatewayMapper;
import br.com.alexsdm.postech.oficina.servico.infrastructure.persistence.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ServicoGatewayImpl implements ServicoGateway {

    private final ServicoRepository repository;
    private final ServicoGatewayMapper mapper;

    @Override
    public Servico salvar(Servico servico) {
        var entity = mapper.toEntity(servico);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Servico> buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Servico> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
