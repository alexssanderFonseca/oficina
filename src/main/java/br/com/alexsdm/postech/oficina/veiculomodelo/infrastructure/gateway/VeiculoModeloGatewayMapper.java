package br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.veiculomodelo.domain.entity.VeiculoModelo;
import br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.persistence.VeiculoModeloEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeiculoModeloGatewayMapper {
    VeiculoModelo toDomain(VeiculoModeloEntity entity);

    VeiculoModeloEntity toEntity(VeiculoModelo domain);
}
