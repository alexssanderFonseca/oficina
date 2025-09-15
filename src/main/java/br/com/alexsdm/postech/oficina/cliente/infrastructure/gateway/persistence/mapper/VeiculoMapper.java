package br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.mapper;

import br.com.alexsdm.postech.oficina.cliente.domain.entity.Veiculo;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.jpa.VeiculoJpaEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VeiculoMapper {

    public static Veiculo toDomain(VeiculoJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        var veiculoModelo = new br.com.alexsdm.postech.oficina.cliente.domain.entity.VeiculoModelo(
            jpaEntity.getVeiculoModelo().getId(),
            jpaEntity.getVeiculoModelo().getMarca(),
            jpaEntity.getVeiculoModelo().getModelo()
        );
        return new Veiculo(
                jpaEntity.getId(),
                jpaEntity.getPlaca(),
                veiculoModelo,
                jpaEntity.getCor(),
                jpaEntity.getAno()
        );
    }

    public static VeiculoJpaEntity toJpaEntity(Veiculo domainEntity) {
        if (domainEntity == null) {
            return null;
        }
        var veiculoModeloEntity = new br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.persistence.VeiculoModeloEntity();
        veiculoModeloEntity.setId(domainEntity.getVeiculoModelo().getId());
        veiculoModeloEntity.setMarca(domainEntity.getVeiculoModelo().getMarca());
        veiculoModeloEntity.setModelo(domainEntity.getVeiculoModelo().getModelo());
        
        return new VeiculoJpaEntity(
                domainEntity.getId(),
                domainEntity.getPlaca(),
                veiculoModeloEntity,
                domainEntity.getCor(),
                domainEntity.getAno()
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