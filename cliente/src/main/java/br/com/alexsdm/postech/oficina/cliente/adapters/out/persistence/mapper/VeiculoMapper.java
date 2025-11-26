package br.com.alexsdm.postech.oficina.cliente.adapters.out.persistence.mapper;

import br.com.alexsdm.postech.oficina.cliente.core.domain.entity.Veiculo;
import br.com.alexsdm.postech.oficina.cliente.adapters.out.persistence.jpa.VeiculoJpaEntity;

import java.util.Collections;
import java.util.List;

public class VeiculoMapper {

    private VeiculoMapper(){}

    public static Veiculo toDomain(VeiculoJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        return new Veiculo(
                jpaEntity.getId(),
                jpaEntity.getPlaca(),
                jpaEntity.getMarca(),
                jpaEntity.getModelo(),
                jpaEntity.getCor(),
                jpaEntity.getAno()
        );
    }

    public static VeiculoJpaEntity toJpaEntity(Veiculo domainEntity) {
        if (domainEntity == null) {
            return null;
        }


        return new VeiculoJpaEntity(
                domainEntity.getId(),
                domainEntity.getPlaca(),
                domainEntity.getMarca(),
                domainEntity.getModelo(),
                domainEntity.getCor(),
                domainEntity.getAno()
        );
    }

    public static List<Veiculo> toDomain(List<VeiculoJpaEntity> jpaEntities) {
        if (jpaEntities == null || jpaEntities.isEmpty()) {
            return Collections.emptyList();
        }
        return jpaEntities.stream().map(VeiculoMapper::toDomain).toList();
    }

    public static List<VeiculoJpaEntity> toJpaEntity(List<Veiculo> domainEntities) {
        if (domainEntities == null || domainEntities.isEmpty()) {
            return Collections.emptyList();
        }
        return domainEntities.stream().map(VeiculoMapper::toJpaEntity).toList();
    }
}