package br.com.alexsdm.postech.oficina.admin.cliente.entity;

import br.com.alexsdm.postech.oficina.cliente.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.entity.Veiculo;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteDocumentoInvalidoException;
import br.com.alexsdm.postech.oficina.veiculomodelo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.cliente.entity.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Cliente cliente;
    private Veiculo veiculo;
    private Veiculo veiculo2;
    private Endereco endereco;
    private VeiculoModelo veiculoModelo;

    private UUID clienteId = UUID.randomUUID();
    private UUID veiculoId = UUID.randomUUID();
    private UUID veiculoId2 = UUID.randomUUID();
    private String cpfValido = "59953085056";
    private String cnpjValido = "11222333000181";

    @BeforeEach
    void setUp() {
        this.cliente = new Cliente();

        this.endereco = new Endereco(UUID.randomUUID(), "Rua das Flores", "123",
                "Centro", "38400-123", "Uberlândia", "MG");

        var veiculoModelo = new VeiculoModelo(
                "Toyota",
                "Corolla",
                1997,
                2026,
                "Sedan"
        );

        this.veiculoModelo = veiculoModelo;

        this.veiculo = new Veiculo(veiculoId,
                "FFA3A31",
                "2020",
                "cinza",
                veiculoModelo
        );

        this.veiculo2 = new Veiculo(veiculoId2,
                "ABC1A34",
                "2019",
                "branco",
                veiculoModelo
        );
    }

    @DisplayName("Deve adicionar veiculo a lista do cliente")
    @Test
    void deveAdicionarVeiculoAListaDoCliente() {
        // Act
        cliente.adicionarVeiculo(veiculo);

        // Assert
        assertEquals(1, cliente.getVeiculos().size());
        var veiculoAdicionado = cliente.getVeiculos().get(0);
        assertEquals("2020", veiculoAdicionado.getAno());
        assertEquals("cinza", veiculoAdicionado.getCor());
        assertEquals("FFA3A31", veiculoAdicionado.getPlaca());
        assertEquals("Corolla", veiculoAdicionado.getVeiculoModelo().getModelo());
        assertEquals("Toyota", veiculoAdicionado.getVeiculoModelo().getMarca());
        assertEquals("Sedan", veiculoAdicionado.getVeiculoModelo().getTipo());
    }

    @DisplayName("Deve criar cliente com CPF válido")
    @Test
    void deveCriarClienteComCpfValido() {
        // Act
        var clienteComCpf = new Cliente(
                clienteId,
                "João",
                "Silva",
                cpfValido,
                "joao@email.com",
                endereco,
                "34999999999"
        );

        // Assert
        assertNotNull(clienteComCpf);
        assertEquals(clienteId, clienteComCpf.getId());
        assertEquals("João", clienteComCpf.getNome());
        assertEquals("Silva", clienteComCpf.getSobrenome());
        assertEquals(cpfValido, clienteComCpf.getCpfCnpj());
        assertEquals("joao@email.com", clienteComCpf.getEmail());
        assertEquals(endereco, clienteComCpf.getEndereco());
        assertEquals("34999999999", clienteComCpf.getTelefone());
        assertNotNull(clienteComCpf.getVeiculos());
        assertTrue(clienteComCpf.getVeiculos().isEmpty());
    }

    @DisplayName("Deve criar cliente com CNPJ válido")
    @Test
    void deveCriarClienteComCnpjValido() {
        // Act
        var clienteComCnpj = new Cliente(
                clienteId,
                "Empresa ABC",
                "Ltda",
                cnpjValido,
                "contato@empresa.com",
                endereco,
                "34988888888"
        );

        // Assert
        assertNotNull(clienteComCnpj);
        assertEquals(cnpjValido, clienteComCnpj.getCpfCnpj());
        assertEquals("Empresa ABC", clienteComCnpj.getNome());
        assertEquals("Ltda", clienteComCnpj.getSobrenome());
    }

    @DisplayName("Deve lançar exceção ao criar cliente com CPF inválido")
    @Test
    void deveLancarExcecaoAoCriarClienteComCpfInvalido() {
        // Act & Assert
        assertThrows(ClienteDocumentoInvalidoException.class, () -> {
            new Cliente(
                    clienteId,
                    "João",
                    "Silva",
                    "12345678901", // CPF inválido
                    "joao@email.com",
                    endereco,
                    "34999999999"
            );
        });
    }

    @DisplayName("Deve lançar exceção ao criar cliente com CNPJ inválido")
    @Test
    void deveLancarExcecaoAoCriarClienteComCnpjInvalido() {
        // Act & Assert
        assertThrows(ClienteDocumentoInvalidoException.class, () -> {
            new Cliente(
                    clienteId,
                    "Empresa",
                    "Ltda",
                    "11222333000199", // CNPJ inválido
                    "contato@empresa.com",
                    endereco,
                    "34988888888"
            );
        });
    }

    @DisplayName("Deve lançar exceção ao criar cliente com documento vazio")
    @Test
    void deveLancarExcecaoAoCriarClienteComDocumentoVazio() {
        // Act & Assert
        assertThrows(ClienteDocumentoInvalidoException.class, () -> {
            new Cliente(
                    clienteId,
                    "João",
                    "Silva",
                    "", // Documento vazio
                    "joao@email.com",
                    endereco,
                    "34999999999"
            );
        });
    }

    @DisplayName("Deve lançar exceção ao criar cliente com documento nulo")
    @Test
    void deveLancarExcecaoAoCriarClienteComDocumentoNulo() {
        // Act & Assert
        assertThrows(ClienteDocumentoInvalidoException.class, () -> {
            new Cliente(
                    clienteId,
                    "João",
                    "Silva",
                    null, // Documento nulo
                    "joao@email.com",
                    endereco,
                    "34999999999"
            );
        });
    }

    @DisplayName("Deve adicionar múltiplos veículos ao cliente")
    @Test
    void deveAdicionarMultiplosVeiculosAoCliente() {
        // Act
        cliente.adicionarVeiculo(veiculo);
        cliente.adicionarVeiculo(veiculo2);

        // Assert
        assertEquals(2, cliente.getVeiculos().size());
        assertTrue(cliente.getVeiculos().contains(veiculo));
        assertTrue(cliente.getVeiculos().contains(veiculo2));
    }

    @DisplayName("Deve inicializar lista de veículos quando for nula")
    @Test
    void deveInicializarListaDeVeiculosQuandoForNula() {
        // Arrange
        cliente.setVeiculos(null);

        // Act
        cliente.adicionarVeiculo(veiculo);

        // Assert
        assertNotNull(cliente.getVeiculos());
        assertEquals(1, cliente.getVeiculos().size());
        assertEquals(veiculo, cliente.getVeiculos().get(0));
    }

    @DisplayName("Deve buscar veículo por ID com sucesso")
    @Test
    void deveBuscarVeiculoPorIdComSucesso() {
        // Arrange
        cliente.adicionarVeiculo(veiculo);
        cliente.adicionarVeiculo(veiculo2);

        // Act
        var resultado = cliente.getVeiculoPorId(veiculoId);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(veiculo, resultado.get());
        assertEquals(veiculoId, resultado.get().getId());
        assertEquals("FFA3A31", resultado.get().getPlaca());
    }

    @DisplayName("Deve retornar Optional vazio ao buscar veículo inexistente")
    @Test
    void deveRetornarOptionalVazioAoBuscarVeiculoInexistente() {
        // Arrange
        cliente.adicionarVeiculo(veiculo);
        var idInexistente = UUID.randomUUID();

        // Act
        var resultado = cliente.getVeiculoPorId(idInexistente);

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @DisplayName("Deve retornar Optional vazio quando cliente não tiver veículos")
    @Test
    void deveRetornarOptionalVazioQuandoClienteNaoTiverVeiculos() {
        // Act
        var resultado = cliente.getVeiculoPorId(veiculoId);

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @DisplayName("Deve buscar veículo específico entre múltiplos veículos")
    @Test
    void deveBuscarVeiculoEspecificoEntreMultiplosVeiculos() {
        // Arrange
        cliente.adicionarVeiculo(veiculo);
        cliente.adicionarVeiculo(veiculo2);

        // Act
        var resultado1 = cliente.getVeiculoPorId(veiculoId);
        var resultado2 = cliente.getVeiculoPorId(veiculoId2);

        // Assert
        assertTrue(resultado1.isPresent());
        assertTrue(resultado2.isPresent());
        assertEquals("FFA3A31", resultado1.get().getPlaca());
        assertEquals("ABC1A34", resultado2.get().getPlaca());
        assertNotEquals(resultado1.get().getId(), resultado2.get().getId());
    }

    @DisplayName("Deve criar cliente com construtor padrão")
    @Test
    void deveCriarClienteComConstrutorPadrao() {
        // Arrange & Act
        var clienteVazio = new Cliente();

        // Assert
        assertNotNull(clienteVazio);
        assertNull(clienteVazio.getId());
        assertNull(clienteVazio.getNome());
        assertNull(clienteVazio.getSobrenome());
        assertNull(clienteVazio.getCpfCnpj());
        assertNull(clienteVazio.getEmail());
        assertNull(clienteVazio.getEndereco());
        assertNull(clienteVazio.getTelefone());
        assertNull(clienteVazio.getVeiculos());
    }

    @DisplayName("Deve manter integridade dos dados após múltiplas operações")
    @Test
    void deveManterIntegridadeDosDadosAposMultiplasOperacoes() {
        // Arrange
        var clienteCompleto = new Cliente(
                clienteId,
                "Maria",
                "Santos",
                cpfValido,
                "maria@email.com",
                endereco,
                "34777777777"
        );

        // Act
        clienteCompleto.adicionarVeiculo(veiculo);
        clienteCompleto.adicionarVeiculo(veiculo2);
        var veiculoEncontrado = clienteCompleto.getVeiculoPorId(veiculoId);

        // Assert
        assertEquals(2, clienteCompleto.getVeiculos().size());
        assertTrue(veiculoEncontrado.isPresent());
        assertEquals("Maria", clienteCompleto.getNome());
        assertEquals(cpfValido, clienteCompleto.getCpfCnpj());
        assertEquals("maria@email.com", clienteCompleto.getEmail());
    }
}