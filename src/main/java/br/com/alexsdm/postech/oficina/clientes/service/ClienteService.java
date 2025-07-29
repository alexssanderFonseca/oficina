package br.com.alexsdm.postech.oficina.clientes.service;

import br.com.alexsdm.postech.oficina.clientes.controller.request.ClienteAtualizarRequest;
import br.com.alexsdm.postech.oficina.clientes.controller.request.CadastrarClienteRequest;
import br.com.alexsdm.postech.oficina.clientes.controller.request.DadosVeiculoRequest;
import br.com.alexsdm.postech.oficina.clientes.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.clientes.model.Cliente;
import br.com.alexsdm.postech.oficina.clientes.model.Endereco;
import br.com.alexsdm.postech.oficina.clientes.model.Veiculo;
import br.com.alexsdm.postech.oficina.clientes.repository.ClienteRepository;
import br.com.alexsdm.postech.oficina.veiculos.repository.VeiculoModeloRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final VeiculoModeloRepository veiculoModeloRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          VeiculoModeloRepository veiculoModeloRepository) {
        this.clienteRepository = clienteRepository;
        this.veiculoModeloRepository = veiculoModeloRepository;
    }

    public Cliente cadastrar(CadastrarClienteRequest cadastrarClienteRequest) {
        var endereco = new Endereco(
                UUID.randomUUID(),
                cadastrarClienteRequest.endereco().rua(),
                cadastrarClienteRequest.endereco().bairro(),
                cadastrarClienteRequest.endereco().cep(),
                cadastrarClienteRequest.endereco().cidade(),
                cadastrarClienteRequest.endereco().uf()
        );


        var cliente = new Cliente(
                UUID.randomUUID(),
                cadastrarClienteRequest.nome(),
                cadastrarClienteRequest.sobrenome(),
                cadastrarClienteRequest.cpf(),
                cadastrarClienteRequest.email(),
                endereco,
                cadastrarClienteRequest.telefone()
        );
        clienteRepository.save(cliente);
        return cliente;
    }

    public void adicionarVeiculo(UUID idCliente,
                                 DadosVeiculoRequest dadosVeiculoRequest) {
        var cliente = clienteRepository.findById(idCliente)
                .orElseThrow(ClienteNaoEncontradoException::new);

        var veiculoModelo = veiculoModeloRepository.findById(dadosVeiculoRequest.veiculoModeloID())
                .orElseThrow(RuntimeException::new);


        cliente.adicionarVeiculo(new Veiculo(
                UUID.randomUUID(),
                dadosVeiculoRequest.placa(),
                dadosVeiculoRequest.ano(),
                dadosVeiculoRequest.cor(),
                veiculoModelo
        ));

        clienteRepository.save(cliente);
    }

    public Cliente buscar(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(ClienteNaoEncontradoException::new);
    }

    public Cliente atualizar(UUID id,
                             ClienteAtualizarRequest clienteAtualizarRequest) {
        var cliente = clienteRepository.findById(id)
                .orElseThrow(ClienteNaoEncontradoException::new);
        clienteRepository.save(cliente);
        return cliente;
    }

    public void deletar(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNaoEncontradoException();
        }
        clienteRepository.deleteById(id);
    }
}
