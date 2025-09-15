package br.com.alexsdm.postech.oficina.ordemServico.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "peca_os") // Tabela para armazenar a 'foto' da peça na OS
@Getter
@Setter
@NoArgsConstructor
public class PecaOsEntity {
    @Id
    private Long id;
    private String nome;
    private BigDecimal precoVenda;
}
