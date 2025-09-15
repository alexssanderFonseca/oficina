package br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.gateway.impl;

import br.com.alexsdm.postech.oficina.veiculomodelo.application.gateway.VeiculoModeloGateway;
import br.com.alexsdm.postech.oficina.veiculomodelo.domain.entity.VeiculoModelo;
import br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.gateway.VeiculoModeloGatewayMapper;
import br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.persistence.VeiculoModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VeiculoModeloGatewayImpl implements VeiculoModeloGateway {

    private final VeiculoModeloRepository repository;
    private final VeiculoModeloGatewayMapper mapper;

    @Override
    public VeiculoModelo salvar(VeiculoModelo veiculoModelo) {
        var entity = mapper.toEntity(veiculoModelo);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<VeiculoModelo> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
