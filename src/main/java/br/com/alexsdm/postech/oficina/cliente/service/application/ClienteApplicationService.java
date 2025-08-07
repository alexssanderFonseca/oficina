package br.com.alexsdm.postech.oficina.cliente.service.application;


import br.com.alexsdm.postech.oficina.cliente.controller.request.AtualizarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.cliente.model.Cliente;
import br.com.alexsdm.postech.oficina.cliente.model.Endereco;
import br.com.alexsdm.postech.oficina.cliente.model.Veiculo;
import br.com.alexsdm.postech.oficina.cliente.repository.ClienteRepository;
import br.com.alexsdm.postech.oficina.cliente.service.application.output.BuscarClienteEnderecoOutput;
import br.com.alexsdm.postech.oficina.cliente.service.application.output.BuscarClienteOuput;
import br.com.alexsdm.postech.oficina.cliente.service.application.output.BuscarClienteVeiculoOutput;
import br.com.alexsdm.postech.oficina.cliente.service.domain.ClienteDomainService;
import br.com.alexsdm.postech.oficina.cliente.service.input.AdicionarVeiculoClientInput;
import br.com.alexsdm.postech.oficina.cliente.service.input.CadastrarClienteInput;
import br.com.alexsdm.postech.oficina.veiculo.repository.VeiculoModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteApplicationService {

    private final ClienteDomainService clienteDomainService;
    private final ClienteRepository clienteRepository;
    private final VeiculoModeloRepository veiculoModeloRepository;

    public String cadastrar(CadastrarClienteInput cadastrarClienteInput) {
        var endereco = new Endereco(
                UUID.randomUUID(),
                cadastrarClienteInput.endereco().rua(),
                cadastrarClienteInput.endereco().bairro(),
                cadastrarClienteInput.endereco().cep(),
                cadastrarClienteInput.endereco().cidade(),
                cadastrarClienteInput.endereco().uf()
        );


        var cliente = new Cliente(
                UUID.randomUUID(),
                cadastrarClienteInput.nome(),
                cadastrarClienteInput.sobrenome(),
                cadastrarClienteInput.cpfCnpj(),
                cadastrarClienteInput.email(),
                endereco,
                cadastrarClienteInput.telefone()
        );

        clienteRepository.save(cliente);

        return cliente.getId().toString();
    }


    public BuscarClienteOuput buscar(UUID id) {
        var cliente = clienteRepository.findById(id)
                .orElseThrow(ClienteNaoEncontradoException::new);
        return buildBuscaClienteOuput(cliente);
    }

    public UUID adicionarVeiculo(UUID idCliente, AdicionarVeiculoClientInput input) {
        var cliente = clienteRepository.findById(idCliente)
                .orElseThrow(ClienteNaoEncontradoException::new);

        var veiculoModelo = veiculoModeloRepository.findById(input.veiculoModeloId())
                .orElseThrow(RuntimeException::new);

        var veiculo = new Veiculo(
                UUID.randomUUID(),
                input.placa(),
                input.ano(),
                input.cor(),
                veiculoModelo
        );

        var veiculoId = clienteDomainService.adicionarVeiculo(cliente, veiculo);

        clienteRepository.save(cliente);

        return veiculoId;
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

    private BuscarClienteOuput buildBuscaClienteOuput(Cliente cliente) {
        var veiculosOutput = cliente.getVeiculos()
                .stream()
                .map(veiculo -> new BuscarClienteVeiculoOutput(
                        veiculo.getPlaca(),
                        veiculo.getVeiculoModelo().getMarca(),
                        veiculo.getAno(),
                        veiculo.getVeiculoModelo().getModelo(),
                        veiculo.getCor()
                )).toList();

        return new BuscarClienteOuput(cliente.getId().toString(),
                cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getCpfCnpj(),
                cliente.getEmail(),
                cliente.getTelefone(),
                new BuscarClienteEnderecoOutput(cliente.getEndereco().getRua(),
                        cliente.getEndereco().getBairro(),
                        cliente.getEndereco().getCep(),
                        cliente.getEndereco().getCidade(),
                        cliente.getEndereco().getUf()),
                veiculosOutput);
    }
}
