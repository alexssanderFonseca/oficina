package br.com.alexsdm.postech.oficina.admin.cliente.service.application;

import br.com.alexsdm.postech.oficina.admin.cliente.controller.request.AtualizarClienteRequest;
import br.com.alexsdm.postech.oficina.admin.cliente.controller.request.EnderecoAtualizarRequest;
import br.com.alexsdm.postech.oficina.admin.cliente.entity.Cliente;
import br.com.alexsdm.postech.oficina.admin.cliente.entity.Endereco;
import br.com.alexsdm.postech.oficina.admin.cliente.entity.Veiculo;
import br.com.alexsdm.postech.oficina.admin.cliente.exception.ClienteException;
import br.com.alexsdm.postech.oficina.admin.cliente.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.admin.cliente.repository.ClienteRepository;
import br.com.alexsdm.postech.oficina.admin.cliente.service.domain.ClienteDomainService;
import br.com.alexsdm.postech.oficina.admin.cliente.service.input.AdicionarVeiculoClientInput;
import br.com.alexsdm.postech.oficina.admin.cliente.service.input.CadastrarClienteEnderecoInput;
import br.com.alexsdm.postech.oficina.admin.cliente.service.input.CadastrarClienteInput;
import br.com.alexsdm.postech.oficina.admin.veiculomodelo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.admin.veiculomodelo.service.application.VeiculoModeloApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para ClienteApplicationService")
class ClienteApplicationServiceTest {

    @Mock
    private ClienteDomainService clienteDomainService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private VeiculoModeloApplicationService veiculoModeloApplicationService;

    @InjectMocks
    private ClienteApplicationService clienteApplicationService;

    private Cliente cliente;
    private Endereco endereco;
    private Veiculo veiculo;
    private VeiculoModelo veiculoModelo;
    private CadastrarClienteInput cadastrarClienteInput;
    private AdicionarVeiculoClientInput adicionarVeiculoInput;
    private AtualizarClienteRequest atualizarClienteRequest;
    private CadastrarClienteEnderecoInput enderecoInput;

    private UUID clienteId = UUID.randomUUID();
    private UUID veiculoId = UUID.randomUUID();
    private long veiculoModeloId = 1L;
    private String cpfCnpj = "59953085056";
    private String placa = "FFA3I31";

    @BeforeEach
    void setUp() {
        endereco = new Endereco(UUID.randomUUID(), "Rua das Flores", "123", "Centro",
                "38400-123", "Uberlândia", "MG");

        veiculoModelo = new VeiculoModelo("Toyota", "Corolla", 2015, 2025, "Sedan");

        veiculo = new Veiculo(veiculoId, placa, "2020", "Branco", veiculoModelo);

        cliente = new Cliente(clienteId, "João", "Silva", cpfCnpj,
                "joao@email.com", endereco, "34999999999");
        cliente.adicionarVeiculo(veiculo);

        enderecoInput = new CadastrarClienteEnderecoInput("Rua das Flores", "123", "Centro",
                "38400-123", "Uberlândia", "MG");

        cadastrarClienteInput = new CadastrarClienteInput("João", "Silva", cpfCnpj,
                "joao@email.com", "34999999999", enderecoInput);

        adicionarVeiculoInput = new AdicionarVeiculoClientInput(veiculoModeloId, placa, "2020", "Branco");

        atualizarClienteRequest = new AtualizarClienteRequest("joao.santos@email.com", "34998392501",
                new EnderecoAtualizarRequest(
                        "Rua dos beija flores",
                        "123",
                        "Centro",
                        "38400-321",
                        "Uberlândia",
                        "MG"
                ));
    }

    @Test
    @DisplayName("Deve cadastrar cliente com sucesso")
    void deveCadastrarClienteComSucesso() {
        // Arrange
        when(clienteRepository.findByCpfCnpj(cpfCnpj)).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        String resultado = clienteApplicationService.cadastrar(cadastrarClienteInput);

        // Assert
        assertNotNull(resultado);
        verify(clienteRepository).findByCpfCnpj(cpfCnpj);
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar cliente com CPF/CNPJ já existente")
    void deveLancarExcecaoAoCadastrarClienteComCpfCnpjJaExistente() {
        // Arrange
        when(clienteRepository.findByCpfCnpj(cpfCnpj)).thenReturn(Optional.of(cliente));

        // Act & Assert
        ClienteException exception = assertThrows(ClienteException.class, () -> {
            clienteApplicationService.cadastrar(cadastrarClienteInput);
        });
        assertEquals("Já existe um cliente com o cpfCnpj informado", exception.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve buscar cliente por ID com sucesso")
    void deveBuscarClientePorIdComSucesso() {
        // Arrange
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        var resultado = clienteApplicationService.buscar(clienteId);

        // Assert
        assertNotNull(resultado);
        assertEquals(clienteId.toString(), resultado.id());
        assertEquals("João", resultado.nome());
        assertEquals("Silva", resultado.sobrenome());
        assertEquals(cpfCnpj, resultado.cpfCnpj());
        assertEquals("joao@email.com", resultado.email());
        assertEquals("34999999999", resultado.telefone());
        assertEquals("Rua das Flores", resultado.endereco().rua());
        assertEquals(1, resultado.veiculos().size());
        assertEquals(placa, resultado.veiculos().get(0).placa());
        verify(clienteRepository).findById(clienteId);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente inexistente")
    void deveLancarExcecaoAoBuscarClienteInexistente() {
        // Arrange
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteApplicationService.buscar(clienteId);
        });
    }

    @Test
    @DisplayName("Deve buscar entidade cliente por ID com sucesso")
    void deveBuscarEntidadeClientePorIdComSucesso() {
        // Arrange
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        var resultado = clienteApplicationService.buscarEntidade(clienteId);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
        verify(clienteRepository).findById(clienteId);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio ao buscar entidade de cliente inexistente")
    void deveRetornarOptionalVazioAoBuscarEntidadeDeClienteInexistente() {
        // Arrange
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act
        var resultado = clienteApplicationService.buscarEntidade(clienteId);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(clienteRepository).findById(clienteId);
    }

    @Test
    @DisplayName("Deve adicionar veículo ao cliente com sucesso")
    void deveAdicionarVeiculoAoClienteComSucesso() {
        // Arrange
        var novoVeiculoId = UUID.randomUUID();
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(veiculoModeloApplicationService.buscarEntidade(veiculoModeloId))
                .thenReturn(veiculoModelo);
        when(clienteDomainService.adicionarVeiculo(any(Cliente.class), any(Veiculo.class)))
                .thenReturn(novoVeiculoId);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        UUID resultado = clienteApplicationService.adicionarVeiculo(clienteId, adicionarVeiculoInput);

        // Assert
        assertEquals(novoVeiculoId, resultado);
        verify(clienteRepository).findById(clienteId);
        verify(veiculoModeloApplicationService).buscarEntidade(veiculoModeloId);
        verify(clienteDomainService).adicionarVeiculo(any(Cliente.class), any(Veiculo.class));
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar veículo a cliente inexistente")
    void deveLancarExcecaoAoAdicionarVeiculoAClienteInexistente() {
        // Arrange
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteApplicationService.adicionarVeiculo(clienteId, adicionarVeiculoInput);
        });
        verify(clienteDomainService, never()).adicionarVeiculo(any(), any());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve buscar cliente por CPF/CNPJ com sucesso")
    void deveBuscarClientePorCpfCnpjComSucesso() {
        // Arrange
        when(clienteRepository.findByCpfCnpj(cpfCnpj)).thenReturn(Optional.of(cliente));

        // Act
        var resultado = clienteApplicationService.buscarPorCpfCnpj(cpfCnpj);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
        verify(clienteRepository).findByCpfCnpj(cpfCnpj);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio ao buscar por CPF/CNPJ inexistente")
    void deveRetornarOptionalVazioAoBuscarPorCpfCnpjInexistente() {
        // Arrange
        when(clienteRepository.findByCpfCnpj(cpfCnpj)).thenReturn(Optional.empty());

        // Act
        var resultado = clienteApplicationService.buscarPorCpfCnpj(cpfCnpj);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(clienteRepository).findByCpfCnpj(cpfCnpj);
    }

    @Test
    @DisplayName("Deve atualizar cliente com sucesso")
    void deveAtualizarClienteComSucesso() {
        // Arrange
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        Cliente resultado = clienteApplicationService.atualizar(clienteId, atualizarClienteRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteRepository).findById(clienteId);
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar cliente inexistente")
    void deveLancarExcecaoAoAtualizarClienteInexistente() {
        // Arrange
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteApplicationService.atualizar(clienteId, atualizarClienteRequest);
        });
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    void deveDeletarClienteComSucesso() {
        // Arrange
        when(clienteRepository.existsById(clienteId)).thenReturn(true);

        // Act
        clienteApplicationService.deletar(clienteId);

        // Assert
        verify(clienteRepository).existsById(clienteId);
        verify(clienteRepository).deleteById(clienteId);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar cliente inexistente")
    void deveLancarExcecaoAoDeletarClienteInexistente() {
        // Arrange
        when(clienteRepository.existsById(clienteId)).thenReturn(false);

        // Act & Assert
        assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteApplicationService.deletar(clienteId);
        });
        verify(clienteRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve construir output de busca com cliente sem veículos")
    void deveConstruirOutputDeBuscaComClienteSemVeiculos() {
        // Arrange
        Cliente clienteSemVeiculos = new Cliente(clienteId, "Maria", "Santos", cpfCnpj,
                "maria@email.com", endereco, "34888888888");
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteSemVeiculos));

        // Act
        var resultado = clienteApplicationService.buscar(clienteId);

        // Assert
        assertNotNull(resultado);
        assertEquals("Maria", resultado.nome());
        assertEquals("Santos", resultado.sobrenome());
        assertTrue(resultado.veiculos().isEmpty());
    }

    @Test
    @DisplayName("Deve construir output de busca com múltiplos veículos")
    void deveConstruirOutputDeBuscaComMultiplosVeiculos() {
        // Arrange
        Cliente clienteComMultiplosVeiculos = new Cliente(clienteId, "Pedro", "Oliveira", cpfCnpj,
                "pedro@email.com", endereco, "34777777777");

        Veiculo veiculo1 = new Veiculo(UUID.randomUUID(), "ABC1A34", "2019", "Azul", veiculoModelo);
        Veiculo veiculo2 = new Veiculo(UUID.randomUUID(), "DEF5L78", "2021", "Vermelho", veiculoModelo);

        clienteComMultiplosVeiculos.adicionarVeiculo(veiculo1);
        clienteComMultiplosVeiculos.adicionarVeiculo(veiculo2);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteComMultiplosVeiculos));

        // Act
        var resultado = clienteApplicationService.buscar(clienteId);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.veiculos().size());
        assertTrue(resultado.veiculos().stream().anyMatch(v -> v.placa().equals("ABC1A34")));
        assertTrue(resultado.veiculos().stream().anyMatch(v -> v.placa().equals("DEF5L78")));
    }
}