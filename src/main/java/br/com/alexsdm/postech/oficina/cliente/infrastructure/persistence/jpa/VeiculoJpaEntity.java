package br.com.alexsdm.postech.oficina.cliente.infrastructure.persistence.jpa;

import br.com.alexsdm.postech.oficina.veiculomodelo.model.VeiculoModelo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "veiculo")
@Getter
@Setter
@NoArgsConstructor
public class VeiculoJpaEntity {

    @Id
    private UUID id;
    private String placa;
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "veiculo_modelo_id", nullable = false)
    private VeiculoModelo veiculoModelo;
    private String cor;
    private String ano;

    public VeiculoJpaEntity(UUID id, String placa, VeiculoModelo veiculoModelo, String cor, String ano) {
        this.id = id;
        this.placa = placa;
        this.veiculoModelo = veiculoModelo;
        this.cor = cor;
        this.ano = ano;
    }
}
