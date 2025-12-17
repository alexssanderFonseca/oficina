package br.com.alexsdm.postech.oficina.orcamento.adapter.out.persistence.mapper;

import br.com.alexsdm.postech.oficina.orcamento.core.entity.ItemPecaOrcamento;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.ItemServicoOrcamento;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.OrcamentoStatus;
import br.com.alexsdm.postech.oficina.orcamento.adapter.out.persistence.entity.ItemPecaOrcamentoEntity;
import br.com.alexsdm.postech.oficina.orcamento.adapter.out.persistence.entity.OrcamentoEntity;
import br.com.alexsdm.postech.oficina.orcamento.adapter.out.persistence.entity.ItemServicoOrcamentoEntity;
import br.com.alexsdm.postech.oficina.orcamento.adapter.out.persistence.entity.OrcamentoStatusEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrcamentoMapper {

    public Orcamento toDomain(OrcamentoEntity entity) {
        var domain = new Orcamento();
        domain.setId(entity.getId());
        domain.setClienteId(entity.getClienteId());
        domain.setVeiculoId(entity.getVeiculoId());
        domain.setStatus(OrcamentoStatus.valueOf(entity.getStatus().name()));
        domain.setValorTotal(entity.getValorTotal());
        domain.setValorTotalEmPecas(entity.getValorTotalEmPecas());
        domain.setValorTotalEmServicos(entity.getValorTotalEmServicos());
        domain.setServicos(entity.getServicos().stream().map(this::toDomain).toList());
        domain.setItensPeca(entity.getItensPeca().stream().map(this::toDomain).toList());
        return domain;
    }

    public OrcamentoEntity toEntity(Orcamento domain) {
        var entity = new OrcamentoEntity();
        entity.setId(domain.getId());
        entity.setClienteId(domain.getClienteId());
        entity.setVeiculoId(domain.getVeiculoId());
        entity.setStatus(OrcamentoStatusEntity.valueOf(domain.getStatus().name()));
        entity.setValorTotal(domain.getValorTotal());
        entity.setValorTotalEmPecas(domain.getValorTotalEmPecas());
        entity.setValorTotalEmServicos(domain.getValorTotalEmServicos());
        entity.setServicos(domain.getServicos().stream()
                .map((itemServicoOrcamento -> this.toEntity(itemServicoOrcamento, entity)))
                .toList());
        entity.setItensPeca(domain.getItensPeca().stream().map(item->
                this.toEntity(item, entity)).toList());
        return entity;
    }

    private ItemServicoOrcamento toDomain(ItemServicoOrcamentoEntity entity) {
        return new ItemServicoOrcamento(
                entity.getId(),
                entity.getServicoId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco());

    }

    private ItemServicoOrcamentoEntity toEntity(ItemServicoOrcamento domain, OrcamentoEntity orcamentoEntity) {
        var entity = new ItemServicoOrcamentoEntity();
        entity.setId(domain.getId());
        entity.setOrcamento(orcamentoEntity);
        entity.setServicoId(domain.getServicoId());
        entity.setNome(domain.getNome());
        entity.setPreco(domain.getPreco());
        entity.setDescricao(domain.getDescricao());
        return entity;
    }

    private ItemPecaOrcamento toDomain(ItemPecaOrcamentoEntity entity) {
        return new ItemPecaOrcamento(
                entity.getId(),
                entity.getPecaId(),
                entity.getNome(),
                entity.getQuantidade(),
                entity.getPreco(),
                entity.getDescricao());
    }

    private ItemPecaOrcamentoEntity toEntity(ItemPecaOrcamento domain,
                                             OrcamentoEntity orcamentoEntity) {
        var entity = new ItemPecaOrcamentoEntity();
        entity.setId(domain.getId());
        entity.setOrcamento(orcamentoEntity);
        entity.setNome(domain.getNome());
        entity.setPecaId(domain.getPecaId());
        entity.setPreco(domain.getPreco());
        entity.setQuantidade(domain.getQuantidade());
        entity.setDescricao(domain.getDescricao());
        return entity;
    }

}
