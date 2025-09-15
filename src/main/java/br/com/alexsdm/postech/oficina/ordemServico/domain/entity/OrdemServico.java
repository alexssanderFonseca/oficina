package br.com.alexsdm.postech.oficina.ordemServico.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServico {

    private Long id;
    private UUID clienteId;
    private UUID veiculoId;
    private List<ItemPecaOrdemServico> itensPecaOrdemServico;
    private List<Servico> servicos;
    private Status status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataInicioDaExecucao;
    private LocalDateTime dataEntrega;
    private LocalDateTime dataFinalizacao;

    public OrdemServico(UUID clienteId, UUID veiculoId) {
        this.clienteId = clienteId;
        this.veiculoId = veiculoId;
        this.itensPecaOrdemServico = new ArrayList<>();
        this.servicos = new ArrayList<>();
        this.status = Status.EM_DIAGNOSTICO;
        this.dataCriacao = LocalDateTime.now();
    }

    public void executar(List<ItemPecaOrdemServico> itens, List<Servico> servicos) {
        this.status = Status.EM_EXECUCAO;
        this.itensPecaOrdemServico.clear();
        this.itensPecaOrdemServico.addAll(itens);
        this.servicos.clear();
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
