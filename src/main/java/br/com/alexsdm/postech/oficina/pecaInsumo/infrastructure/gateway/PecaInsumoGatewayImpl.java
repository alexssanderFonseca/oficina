package br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.pecaInsumo.application.gateway.PecaInsumoGateway;
import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.persistence.PecaInsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PecaInsumoGatewayImpl implements PecaInsumoGateway {

    private final PecaInsumoRepository repository;
    private final PecaInsumoMapper mapper;

    @Override
    public PecaInsumo salvar(PecaInsumo pecaInsumo) {
        var entity = mapper.toEntity(pecaInsumo);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<PecaInsumo> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<PecaInsumo> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
