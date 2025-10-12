package br.com.alexsdm.postech.oficina.module.cliente.adapters.out.persistence.mapper;

import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.module.cliente.adapters.out.persistence.jpa.ClienteJpaEntity;

public class ClienteMapper {

    public static Cliente toDomain(ClienteJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        var domain = new Cliente(
                jpaEntity.getId(),
                jpaEntity.getNome(),
                jpaEntity.getSobrenome(),
                jpaEntity.getCpfCnpj(),
                jpaEntity.getEmail(),
                EnderecoMapper.toDomain(jpaEntity.getEndereco()),
                jpaEntity.getTelefone()
        );

        VeiculoMapper.toDomain(jpaEntity.getVeiculos()).forEach(domain::adicionarVeiculo);

        return domain;
    }

    public static ClienteJpaEntity toJpaEntity(Cliente domainEntity) {
        if (domainEntity == null) {
            return null;
        }
        var entity = new ClienteJpaEntity(
                domainEntity.getId(),
                domainEntity.getNome(),
                domainEntity.getSobrenome(),
                domainEntity.getCpfCnpj(),
                domainEntity.getEmail(),
                EnderecoMapper.toJpaEntity(domainEntity.getEndereco()),
                domainEntity.getTelefone(),
                VeiculoMapper.toJpaEntity(domainEntity.getVeiculos())
        );

        entity.getVeiculos().forEach(veiculo -> veiculo.setCliente(entity));
        return entity;
    }
}
