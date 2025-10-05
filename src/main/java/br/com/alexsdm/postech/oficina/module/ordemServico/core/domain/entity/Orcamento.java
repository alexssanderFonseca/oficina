package br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Orcamento {
    private UUID id;
    private UUID clienteId;
    private UUID veiculoId;
    private List<ItemServicoOrcamento> servicos;
    private List<ItemPecaOrcamento> pecas;

}
