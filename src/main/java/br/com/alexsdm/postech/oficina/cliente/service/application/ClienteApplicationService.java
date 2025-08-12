package br.com.alexsdm.postech.oficina.cliente.service.application;


import br.com.alexsdm.postech.oficina.cliente.controller.request.AtualizarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.entity.Endereco;
import br.com.alexsdm.postech.oficina.cliente.entity.Veiculo;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteException;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.cliente.repository.ClienteRepository;
import br.com.alexsdm.postech.oficina.cliente.service.application.output.BuscarClienteEnderecoOutput;
import br.com.alexsdm.postech.oficina.cliente.service.application.output.BuscarClienteOuput;
import br.com.alexsdm.postech.oficina.cliente.service.application.output.BuscarClienteVeiculoOutput;
import br.com.alexsdm.postech.oficina.cliente.service.application.output.StatusOsOuput;
import br.com.alexsdm.postech.oficina.cliente.service.domain.ClienteDomainService;
import br.com.alexsdm.postech.oficina.cliente.service.input.AdicionarVeiculoClientInput;
import br.com.alexsdm.postech.oficina.cliente.service.input.CadastrarClienteInput;
import br.com.alexsdm.postech.oficina.ordemServico.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.veiculomodelo.service.application.VeiculoModeloApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteApplicationService {

    private final ClienteDomainService clienteDomainService;
    private final ClienteRepository clienteRepository;
    private final VeiculoModeloApplicationService veiculoModeloApplicationService;

    public String cadastrar(CadastrarClienteInput cadastrarClienteInput) {


        var endereco = new Endereco(
                UUID.randomUUID(),
                cadastrarClienteInput.endereco().rua(),
                cadastrarClienteInput.endereco().numero(),
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

        var clienteExistente = clienteRepository.findByCpfCnpj(cadastrarClienteInput.cpfCnpj());
        if (clienteExistente.isPresent()) {
            throw new ClienteException("Já existe um cliente com o cpfCnpj informado");
        }


        clienteRepository.save(cliente);

        return cliente.getId().toString();
    }


    public BuscarClienteOuput buscar(UUID id) {
        var cliente = clienteRepository.findById(id)
                .orElseThrow(ClienteNaoEncontradoException::new);
        return buildBuscaClienteOuput(cliente);
    }


    public Optional<Cliente> buscarEntidade(UUID id) {
        return clienteRepository.findById(id);

    }

    public UUID adicionarVeiculo(UUID idCliente, AdicionarVeiculoClientInput input) {
        var cliente = clienteRepository.findById(idCliente)
                .orElseThrow(ClienteNaoEncontradoException::new);

        var veiculoModelo = veiculoModeloApplicationService.buscarEntidade(input.veiculoModeloId());

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

        cliente.atualizarEmail(clienteAtualizarRequest.email());
        cliente.atualizarTelefone(clienteAtualizarRequest.telefone());

        if (clienteAtualizarRequest.endereco() != null) {
            var novoEnderecoDados = clienteAtualizarRequest.endereco();
            var endereco = new Endereco(
                    UUID.randomUUID(),
                    novoEnderecoDados.rua(),
                    novoEnderecoDados.numero(),
                    novoEnderecoDados.cep(),
                    novoEnderecoDados.cep(),
                    novoEnderecoDados.cidade(),
                    novoEnderecoDados.uf()
            );
            cliente.atualizarEndereco(endereco);
        }


        clienteRepository.save(cliente);
        return cliente;
    }

    public void deletar(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNaoEncontradoException();
        }
        clienteRepository.deleteById(id);
    }

    public List<StatusOsOuput> listStatusOs(List<OrdemServico> ordensServico) {
        return ordensServico
                .stream().map(
                        os -> new StatusOsOuput(
                                os.getId(),
                                os.getStatus().name()
                        )).toList();
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
