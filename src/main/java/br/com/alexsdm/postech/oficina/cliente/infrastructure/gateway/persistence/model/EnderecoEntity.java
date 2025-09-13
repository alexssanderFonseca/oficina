package br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "endereco")
public class EnderecoEntity {

    @Id
    private UUID id;

    private String rua;

    private String numero;

    private String bairro;

    private String cep;

    private String cidade;

    private String uf;
}
