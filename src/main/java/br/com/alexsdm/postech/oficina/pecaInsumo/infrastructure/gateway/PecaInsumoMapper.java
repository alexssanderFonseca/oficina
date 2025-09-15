package br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.VeiculoModelo;
import br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.persistence.PecaInsumoEntity;
import br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.persistence.VeiculoModeloEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PecaInsumoMapper {

    public PecaInsumo toDomain(PecaInsumoEntity entity) {
        var domain = new PecaInsumo();
        domain.setId(entity.getId());
        domain.setNome(entity.getNome());
        domain.setDescricao(entity.getDescricao());
        domain.setCodigoFabricante(entity.getCodigoFabricante());
        domain.setMarca(entity.getMarca());
        domain.setQuantidadeEstoque(entity.getQuantidadeEstoque());
        domain.setPrecoCusto(entity.getPrecoCusto());
        domain.setPrecoVenda(entity.getPrecoVenda());
        domain.setCategoria(entity.getCategoria());
        domain.setAtivo(entity.getAtivo());
        domain.setDataCadastro(entity.getDataCadastro());
        domain.setDataAtualizacao(entity.getDataAtualizacao());
        if (entity.getModelosCompativeis() != null) {
            domain.setModelosCompativeis(entity.getModelosCompativeis().stream().map(this::toDomain).collect(Collectors.toList()));
        }
        return domain;
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
        if (domain.getModelosCompativeis() != null) {
            entity.setModelosCompativeis(domain.getModelosCompativeis().stream().map(this::toEntity).collect(Collectors.toList()));
        }
        return entity;
    }

    private VeiculoModelo toDomain(VeiculoModeloEntity entity) {
        return new VeiculoModelo(entity.getId(), entity.getMarca(), entity.getModelo(), entity.getAnoInicio(), entity.getAnoFim(), entity.getTipo());
    }

    private VeiculoModeloEntity toEntity(VeiculoModelo domain) {
        var entity = new VeiculoModeloEntity();
        entity.setId(domain.getId());
        entity.setMarca(domain.getMarca());
        entity.setModelo(domain.getModelo());
        entity.setAnoInicio(domain.getAnoInicio());
        entity.setAnoFim(domain.getAnoFim());
        entity.setTipo(domain.getTipo());
        return entity;
    }
}
