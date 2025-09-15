package br.com.alexsdm.postech.oficina.ordemServico.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.ordemServico.application.gateway.OrdemServicoGateway;
import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.infrastructure.persistence.OrdemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrdemServicoGatewayImpl implements OrdemServicoGateway {

    private final OrdemServicoRepository repository;
    private final OrdemServicoMapper mapper;

    @Override
    public OrdemServico salvar(OrdemServico ordemServico) {
        var entity = mapper.toEntity(ordemServico);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

        @Override
    public Optional<OrdemServico> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<OrdemServico> buscarFinalizadas() {
        return repository.findByStatus("FINALIZADA").stream()
                .map(mapper::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }
}
