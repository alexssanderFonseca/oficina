package br.com.alexsdm.postech.oficina.cliente.infrastructure.persistence.mapper;

import br.com.alexsdm.postech.oficina.cliente.domain.entity.Veiculo;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.persistence.jpa.VeiculoJpaEntity;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.VeiculoModelo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VeiculoMapper {

    public static Veiculo toDomain(VeiculoJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        return new Veiculo(
                jpaEntity.getId(),
                jpaEntity.getPlaca(),
                new VeiculoModelo(jpaEntity.getVeiculoModelo().getMarca(), jpaEntity.getVeiculoModelo().getModelo()),
                jpaEntity.getCor(),
                jpaEntity.getAno()
        );
    }

    public static VeiculoJpaEntity toJpaEntity(Veiculo domainEntity) {
        if (domainEntity == null) {
            return null;
        }
        return new VeiculoJpaEntity(
                domainEntity.id(),
                domainEntity.placa(),
                new br.com.alexsdm.postech.oficina.veiculomodelo.model.VeiculoModelo(
                        domainEntity.veiculoModelo().marca(),
                        domainEntity.veiculoModelo().modelo(),
                        null, null, null // Pass null for other fields not present in client's domain VeiculoModelo
                ),
                domainEntity.cor(),
                domainEntity.ano()
        );
    }

    public static List<Veiculo> toDomain(List<VeiculoJpaEntity> jpaEntities) {
        if (jpaEntities == null || jpaEntities.isEmpty()) {
            return Collections.emptyList();
        }
        return jpaEntities.stream().map(VeiculoMapper::toDomain).collect(Collectors.toList());
    }

    public static List<VeiculoJpaEntity> toJpaEntity(List<Veiculo> domainEntities) {
        if (domainEntities == null || domainEntities.isEmpty()) {
            return Collections.emptyList();
        }
        return domainEntities.stream().map(VeiculoMapper::toJpaEntity).collect(Collectors.toList());
    }
}