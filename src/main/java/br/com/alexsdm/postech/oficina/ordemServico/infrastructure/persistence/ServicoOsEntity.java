package br.com.alexsdm.postech.oficina.ordemServico.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "servico_os") // Tabela para armazenar a 'foto' do serviço na OS
@Getter
@Setter
@NoArgsConstructor
public class ServicoOsEntity {
    @Id
    private Long id;
    private String nome;
    private BigDecimal preco;
}
