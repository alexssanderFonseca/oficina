package br.com.alexsdm.postech.oficina.cliente.service;

import br.com.alexsdm.postech.oficina.cliente.controller.request.AtualizarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.controller.request.CadastrarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.controller.request.DadosVeiculoRequest;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.cliente.model.Cliente;
import br.com.alexsdm.postech.oficina.cliente.model.Endereco;
import br.com.alexsdm.postech.oficina.cliente.model.Veiculo;
import br.com.alexsdm.postech.oficina.cliente.repository.ClienteRepository;
import br.com.alexsdm.postech.oficina.veiculo.repository.VeiculoModeloRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
                cadastrarClienteRequest.cpfCnpj(),
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

        var veiculoModelo = veiculoModeloRepository.findById(dadosVeiculoRequest.veiculoModeloId())
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

    public Optional<Cliente> buscarPorCpfCnpj(String cpfCnpj) {
        return clienteRepository.findByCpfCnpj(cpfCnpj);
    }

    public Cliente atualizar(UUID id,
                             AtualizarClienteRequest clienteAtualizarRequest) {
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
