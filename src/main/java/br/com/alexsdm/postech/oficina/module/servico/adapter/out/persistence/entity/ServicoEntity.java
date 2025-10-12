package br.com.alexsdm.postech.oficina.module.servico.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "servico")
@Getter
@Setter
@NoArgsConstructor
public class ServicoEntity {

    @Id
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer duracaoEstimada;
    private String categoria;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
}
