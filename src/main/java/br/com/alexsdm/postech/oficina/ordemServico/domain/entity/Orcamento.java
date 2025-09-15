package br.com.alexsdm.postech.oficina.ordemServico.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Orcamento {
    private Long id;
    private UUID clienteId;
    private UUID veiculoId;
    // Adicionar outros campos se forem necessários para a OS
}
