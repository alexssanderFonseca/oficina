package br.com.alexsdm.postech.oficina.ordemServico.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPecaOrdemServico {
    private Long id;
    private PecaInsumo peca;
    private BigDecimal precoUnitario;
    private Integer quantidade;
}
