package br.com.alexsdm.postech.oficina.orcamento.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "servico_orcamento")
@Getter
@Setter
@NoArgsConstructor
public class ServicoOrcamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long servicoId;
    private String nome;
    private BigDecimal preco;
}
