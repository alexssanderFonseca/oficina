package br.com.alexsdm.postech.oficina.ordemServico.model;

import br.com.alexsdm.postech.oficina.clientes.model.Cliente;
import br.com.alexsdm.postech.oficina.pecas.model.Peca;
import br.com.alexsdm.postech.oficina.servicos.model.Servico;

import java.util.ArrayList;
import java.util.List;

public class OrdemServico {

    private Cliente cliente;
    private List<Peca> pecas;
    private List<Servico> servicos;
    private Status status;

    public OrdemServico(Cliente cliente,
                        List<Peca> pecas,
                        List<Servico> servicos,
                        Status status) {
        this.cliente = cliente;
        this.pecas = pecas;
        this.servicos = servicos;
        this.status = status;
    }

    public void executar() {
        this.status = Status.EM_EXECUCAO;
    }

    public void diagnosticar() {
        this.status = Status.EM_DIAGNOSTICO;
    }

    public void adicionarPeca(Peca peca) {
        this.pecas.add(peca);
    }

    public void adicionarServico(Servico servico) {
        this.servicos.add(servico);
    }


}
