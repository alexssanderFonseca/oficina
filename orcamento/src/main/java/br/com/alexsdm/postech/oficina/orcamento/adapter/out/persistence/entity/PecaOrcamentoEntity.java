package br.com.alexsdm.postech.oficina.orcamento.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "peca_orcamento")
@Getter
@Setter
@NoArgsConstructor
public class PecaOrcamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pecaId;
    private String nome;
    private BigDecimal preco;
}
