package br.com.alexsdm.postech.oficina.module.cliente.adapters.out.persistence.mapper;

import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Endereco;
import br.com.alexsdm.postech.oficina.module.cliente.adapters.out.persistence.jpa.EnderecoJpaEntity;

public class EnderecoMapper {

    public static Endereco toDomain(EnderecoJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        return new Endereco(
                jpaEntity.getId(),
                jpaEntity.getRua(),
                jpaEntity.getNumero(),
                jpaEntity.getBairro(),
                jpaEntity.getCep(),
                jpaEntity.getCidade(),
                jpaEntity.getUf()
        );
    }

    public static EnderecoJpaEntity toJpaEntity(Endereco domainEntity) {
        if (domainEntity == null) {
            return null;
        }
        return new EnderecoJpaEntity(
                domainEntity.id(),
                domainEntity.rua(),
                domainEntity.numero(),
                domainEntity.bairro(),
                domainEntity.cep(),
                domainEntity.cidade(),
                domainEntity.uf()
        );
    }
}
