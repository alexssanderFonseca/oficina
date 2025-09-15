package br.com.alexsdm.postech.oficina.orcamento.infrastructure.gateway;

import br.com.alexsdm.postech.oficina.orcamento.domain.entity.*;
import br.com.alexsdm.postech.oficina.orcamento.infrastructure.persistence.*;
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
        domain.setServicos(entity.getServicos().stream().map(this::toDomain).collect(Collectors.toList()));
        domain.setItensPeca(entity.getItensPeca().stream().map(this::toDomain).collect(Collectors.toList()));
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
        entity.setServicos(domain.getServicos().stream().map(this::toEntity).collect(Collectors.toList()));
        entity.setItensPeca(domain.getItensPeca().stream().map(this::toEntity).collect(Collectors.toList()));
        return entity;
    }

    private ServicoOrcamento toDomain(ServicoOrcamentoEntity entity) {
        return new ServicoOrcamento(entity.getId(), entity.getServicoId(), entity.getNome(), entity.getPreco());
    }

    private ServicoOrcamentoEntity toEntity(ServicoOrcamento domain) {
        var entity = new ServicoOrcamentoEntity();
        entity.setId(domain.getId());
        entity.setServicoId(domain.getServicoId());
        entity.setNome(domain.getNome());
        entity.setPreco(domain.getPreco());
        return entity;
    }

    private ItemPecaOrcamento toDomain(ItemPecaOrcamentoEntity entity) {
        return new ItemPecaOrcamento(entity.getId(), toDomain(entity.getPeca()), entity.getQuantidade());
    }

    private ItemPecaOrcamentoEntity toEntity(ItemPecaOrcamento domain) {
        var entity = new ItemPecaOrcamentoEntity();
        entity.setId(domain.getId());
        entity.setPeca(toEntity(domain.getPeca()));
        entity.setQuantidade(domain.getQuantidade());
        return entity;
    }

    private PecaOrcamento toDomain(PecaOrcamentoEntity entity) {
        return new PecaOrcamento(entity.getId(), entity.getPecaId(), entity.getNome(), entity.getPreco());
    }

    private PecaOrcamentoEntity toEntity(PecaOrcamento domain) {
        var entity = new PecaOrcamentoEntity();
        entity.setId(domain.getId());
        entity.setPecaId(domain.getPecaId());
        entity.setNome(domain.getNome());
        entity.setPreco(domain.getPreco());
        return entity;
    }
}
