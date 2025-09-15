package br.com.alexsdm.postech.oficina.servico.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "servico")
@Getter
@Setter
@NoArgsConstructor
public class ServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer duracaoEstimada;
    private String categoria;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
}
