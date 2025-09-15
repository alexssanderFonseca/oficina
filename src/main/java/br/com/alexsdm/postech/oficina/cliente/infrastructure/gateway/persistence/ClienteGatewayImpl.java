package br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteGateway;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.jpa.ClienteJpaRepository;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.mapper.ClienteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClienteGatewayImpl implements ClienteGateway {

    private final ClienteJpaRepository repository;

    @Override
    public Cliente salvar(Cliente cliente) {
        var entity = ClienteMapper.toJpaEntity(cliente);
        var savedEntity = repository.save(entity);
        return ClienteMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Cliente> buscarPorId(UUID id) {
        return repository.findById(id).map(ClienteMapper::toDomain);
    }

    @Override
    public Optional<Cliente> buscarPorDocumento(String documento) {
        return repository.findByCpfCnpj(documento).map(ClienteMapper::toDomain);
    }

    @Override
    public void deletar(UUID id) {
        repository.deleteById(id);
    }
}
