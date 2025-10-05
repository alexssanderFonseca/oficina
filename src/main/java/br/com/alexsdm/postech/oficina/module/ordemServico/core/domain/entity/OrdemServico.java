package br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter

public class OrdemServico {
    private UUID id;
    private UUID clienteId;
    private UUID veiculoId;
    private List<ItemPecaOrdemServico> itensPecaOrdemServico;
    private List<ItemServicoOrdemServico> servicos;
    private Status status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataInicioDaExecucao;
    private LocalDateTime dataEntrega;
    private LocalDateTime dataFinalizacao;


    public OrdemServico(UUID id,
                        UUID clienteId,
                        UUID veiculoId,
                        List<ItemPecaOrdemServico> itensPecaOrdemServico,
                        List<ItemServicoOrdemServico> servicos,
                        Status status,
                        LocalDateTime dataCriacao,
                        LocalDateTime dataInicioDaExecucao,
                        LocalDateTime dataEntrega,
                        LocalDateTime dataFinalizacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.veiculoId = veiculoId;
        this.itensPecaOrdemServico = itensPecaOrdemServico;
        this.servicos = servicos;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataInicioDaExecucao = dataInicioDaExecucao;
        this.dataEntrega = dataEntrega;
        this.dataFinalizacao = dataFinalizacao;
    }

    public OrdemServico(UUID id, UUID clienteId, UUID veiculoId) {
        this.id = id;
        this.clienteId = clienteId;
        this.veiculoId = veiculoId;
        this.itensPecaOrdemServico = new ArrayList<>();
        this.servicos = new ArrayList<>();
        this.status = Status.EM_DIAGNOSTICO;
        this.dataCriacao = LocalDateTime.now();
    }

    public void executar(List<ItemPecaOrdemServico> itens,
                         List<ItemServicoOrdemServico> servicos) {
        this.status = Status.EM_EXECUCAO;
        this.itensPecaOrdemServico.addAll(itens);
        this.servicos.addAll(servicos);
        this.dataInicioDaExecucao = LocalDateTime.now();
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
