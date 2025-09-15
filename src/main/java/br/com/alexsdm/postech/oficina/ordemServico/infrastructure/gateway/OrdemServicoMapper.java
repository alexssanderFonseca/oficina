package br.com.alexsdm.postech.oficina.ordemServico.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.*;
import br.com.alexsdm.postech.oficina.ordemServico.infrastructure.persistence.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdemServicoMapper {
    OrdemServico toDomain(OrdemServicoEntity entity);
    OrdemServicoEntity toEntity(OrdemServico domain);
}
