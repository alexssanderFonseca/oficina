package br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.persistence;

import br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.persistence.VeiculoModeloEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "peca_insumo")
@Getter
@Setter
@NoArgsConstructor
public class PecaInsumoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String codigoFabricante;
    private String marca;

    @ManyToMany
    @JoinTable(
            name = "peca_modelo_veiculo",
            joinColumns = @JoinColumn(name = "peca_id"),
            inverseJoinColumns = @JoinColumn(name = "modelo_id")
    )
    private List<VeiculoModeloEntity> modelosCompativeis;

    private Integer quantidadeEstoque;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private String categoria;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
}
