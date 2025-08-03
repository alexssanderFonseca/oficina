package br.com.alexsdm.postech.oficina.ordemServico.model;

import br.com.alexsdm.postech.oficina.cliente.model.Cliente;
import br.com.alexsdm.postech.oficina.servico.model.Servico;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Cliente cliente;
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
    private Instant instanteInicioDaExecucao;
    private Instant instanteFimDaExecucao;
    private Instant instanteEntrega;

    public OrdemServico() {
    }

    public OrdemServico(Cliente cliente,
                        Status status) {
        this.cliente = cliente;
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

        this.instanteInicioDaExecucao = Instant.now();
    }

    public void diagnosticar() {
        this.status = Status.EM_DIAGNOSTICO;
    }

    public void finalizarDiagnostico() {
        this.status = Status.AGUARDANDO_APROVACAO;
    }

    public void finalizar() {
        this.status = Status.FINALIZADA;
        this.instanteFimDaExecucao = Instant.now();
    }

    public void entregar() {
        this.status = Status.ENTREGUE;
        this.instanteEntrega = Instant.now();
    }


    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPecaOrdemServico> getItensPecaOrdemServico() {
        return itensPecaOrdemServico;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Instant getInstanteInicioDaExecucao() {
        return instanteInicioDaExecucao;
    }

    public Instant getInstanteFimDaExecucao() {
        return instanteFimDaExecucao;
    }

    public Instant getInstanteEntrega() {
        return instanteEntrega;
    }
}
