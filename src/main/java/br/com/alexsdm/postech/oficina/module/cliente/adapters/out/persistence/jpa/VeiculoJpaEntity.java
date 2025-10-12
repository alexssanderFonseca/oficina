package br.com.alexsdm.postech.oficina.module.cliente.adapters.out.persistence.jpa;

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
    private String marca;
    private String modelo;
    private String cor;
    private String ano;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteJpaEntity cliente;

    public VeiculoJpaEntity(UUID id,
                            String placa,
                            String marca,
                            String modelo,
                            String cor,
                            String ano) {
        this.id = id;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
    }
}
