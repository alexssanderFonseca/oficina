package br.com.alexsdm.postech.oficina.ordemServico.entity;

import br.com.alexsdm.postech.oficina.admin.servico.entity.Servico;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID clienteId;
    private UUID veiculoId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ordem_servico_id")
    private List<ItemPecaOrdemServico> itensPecaOrdemServico;
    @ManyToMany
    @JoinTable(name = "ordem_servico_servico",
            joinColumns = @JoinColumn(name = "ordem_servico_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id"))
    private List<Servico> servicos;
    private Status status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataInicioDaExecucao;
    private LocalDateTime dataEntrega;
    private LocalDateTime dataFinalizacao;
    ;

    public OrdemServico() {
    }

    public OrdemServico(UUID clienteId,
                        UUID veiculoId,
                        Status status) {
        this.clienteId = clienteId;
        this.veiculoId = veiculoId;
        this.itensPecaOrdemServico = new ArrayList<>();
        this.servicos = new ArrayList<>();
        this.status = status;
        this.dataCriacao = LocalDateTime.now();
    }


    public void executar(List<ItemPecaOrdemServico> itens, List<Servico> servicos) {
        this.status = Status.EM_EXECUCAO;
        this.itensPecaOrdemServico.clear();            // Remove os antigos (serão deletados pelo orphanRemoval)
        this.itensPecaOrdemServico.addAll(itens);      // Adiciona os novos

        this.servicos.clear();                         // Mesmo raciocínio, se quiser limpar
        this.servicos.addAll(servicos);                // Adiciona novos serviços

        this.dataInicioDaExecucao = LocalDateTime.now();
    }

    public void diagnosticar() {
        this.status = Status.EM_DIAGNOSTICO;
    }

    public void finalizarDiagnostico() {
        this.status = Status.AGUARDANDO_APROVACAO;
    }

    public void finalizar() {
        this.status = Status.FINALIZADA;
        this.dataFinalizacao = LocalDateTime.now();
    }

    public void entregar() {
        this.status = Status.ENTREGUE;
        this.dataEntrega = LocalDateTime.now();
    }

}
