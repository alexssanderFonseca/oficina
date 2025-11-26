package br.com.alexsdm.postech.oficina.peca_insumo.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "peca_insumo")
@Getter
@Setter
@NoArgsConstructor
public class PecaInsumoEntity {

    @Id
    private UUID id;

    private String nome;
    private String descricao;
    private String codigoFabricante;
    private String marca;
    private Integer quantidadeEstoque;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private String categoria;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
}
