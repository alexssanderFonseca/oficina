package br.com.alexsdm.postech.oficina.ordemServico.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "ordem_servico")
@Getter
@Setter
@NoArgsConstructor
public class OrdemServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID clienteId;
    private UUID veiculoId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ordem_servico_id")
    private List<ItemPecaOrdemServicoEntity> itensPecaOrdemServico;

    @ManyToMany
    @JoinTable(name = "ordem_servico_servico",
            joinColumns = @JoinColumn(name = "ordem_servico_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id"))
    private List<ServicoOsEntity> servicos;

    @Enumerated(EnumType.STRING)
    private StatusEntity status;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataInicioDaExecucao;
    private LocalDateTime dataEntrega;
    private LocalDateTime dataFinalizacao;
}
