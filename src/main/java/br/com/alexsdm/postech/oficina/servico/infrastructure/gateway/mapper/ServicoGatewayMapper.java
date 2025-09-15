package br.com.alexsdm.postech.oficina.servico.infrastructure.gateway.mapper;

import br.com.alexsdm.postech.oficina.servico.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.servico.infrastructure.persistence.ServicoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServicoGatewayMapper {

    Servico toDomain(ServicoEntity entity);

    ServicoEntity toEntity(Servico domain);

}
