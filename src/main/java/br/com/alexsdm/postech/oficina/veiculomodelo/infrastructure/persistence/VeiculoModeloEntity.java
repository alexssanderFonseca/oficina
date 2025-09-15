package br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "veiculo_modelo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoModeloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marca;
    private String modelo;
    private Integer anoInicio;
    private Integer anoFim;
    private String tipo;
}
