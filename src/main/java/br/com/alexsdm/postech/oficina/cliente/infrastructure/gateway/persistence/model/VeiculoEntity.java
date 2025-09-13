package br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.model;

import br.com.alexsdm.postech.oficina.veiculomodelo.model.VeiculoModelo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "veiculo")
public class VeiculoEntity {

    @Id
    private UUID id;
    private String placa;
    @ManyToOne
    @JoinColumn(name = "veiculo_modelo_id", nullable = false)
    private VeiculoModelo veiculoModelo;
    private String cor;
    private String ano;

}
