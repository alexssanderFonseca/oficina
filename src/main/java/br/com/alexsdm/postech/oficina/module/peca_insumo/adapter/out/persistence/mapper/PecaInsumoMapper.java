package br.com.alexsdm.postech.oficina.module.peca_insumo.adapter.out.persistence.mapper;

import br.com.alexsdm.postech.oficina.module.peca_insumo.adapter.out.persistence.entity.PecaInsumoEntity;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.entity.PecaInsumo;
import org.springframework.stereotype.Component;

@Component
public class PecaInsumoMapper {

    public PecaInsumo toDomain(PecaInsumoEntity entity) {
   return new PecaInsumo(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getCodigoFabricante(),
                entity.getMarca(),
                entity.getQuantidadeEstoque(),
                entity.getPrecoCusto(),
                entity.getPrecoVenda(),
                entity.getCategoria(),
                entity.getAtivo(),
                entity.getDataCadastro(),
                entity.getDataAtualizacao()
        );
    }

    public PecaInsumoEntity toEntity(PecaInsumo domain) {
        var entity = new PecaInsumoEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setDescricao(domain.getDescricao());
        entity.setCodigoFabricante(domain.getCodigoFabricante());
        entity.setMarca(domain.getMarca());
        entity.setQuantidadeEstoque(domain.getQuantidadeEstoque());
        entity.setPrecoCusto(domain.getPrecoCusto());
        entity.setPrecoVenda(domain.getPrecoVenda());
        entity.setCategoria(domain.getCategoria());
        entity.setAtivo(domain.getAtivo());
        entity.setDataCadastro(domain.getDataCadastro());
        entity.setDataAtualizacao(domain.getDataAtualizacao());
        return entity;
    }


}
