package br.com.alexsdm.postech.oficina.module.servico.adapter.out.persistence.mapper;

import br.com.alexsdm.postech.oficina.module.servico.core.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.module.servico.adapter.out.persistence.entity.ServicoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServicoRepositoryMapper {

    Servico toDomain(ServicoEntity entity);

    ServicoEntity toEntity(Servico domain);

}
