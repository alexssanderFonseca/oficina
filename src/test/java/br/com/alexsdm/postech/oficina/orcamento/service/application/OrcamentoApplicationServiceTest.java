package br.com.alexsdm.postech.oficina.orcamento.service.application;

import br.com.alexsdm.postech.oficina.cliente.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.entity.Endereco;
import br.com.alexsdm.postech.oficina.cliente.entity.Veiculo;
import br.com.alexsdm.postech.oficina.cliente.service.application.ClienteApplicationService;
import br.com.alexsdm.postech.oficina.pecaInsumo.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.pecaInsumo.service.application.PecaInsumoApplicationService;
import br.com.alexsdm.postech.oficina.servico.entity.Servico;
import br.com.alexsdm.postech.oficina.servico.service.application.ServicoApplicationService;
import br.com.alexsdm.postech.oficina.veiculomodelo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.orcamento.exception.OrcamentoException;
import br.com.alexsdm.postech.oficina.orcamento.exception.OrcamentoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.orcamento.entity.ItemPecaOrcamento;
import br.com.alexsdm.postech.oficina.orcamento.entity.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.entity.OrcamentoStatus;
import br.com.alexsdm.postech.oficina.orcamento.repository.OrcamentoRepository;
import br.com.alexsdm.postech.oficina.orcamento.service.domain.OrcamentoDomainService;
import br.com.alexsdm.postech.oficina.orcamento.service.input.PecaOrcamentoInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para OrcamentoApplicationService")
class OrcamentoApplicationServiceTest {

    @Mock
    private OrcamentoDomainService orcamentoDomainService;

    @Mock
    private OrcamentoRepository orcamentoRepository;

    @Mock
    private ClienteApplicationService clienteApplicationService;

    @Mock
    private PecaInsumoApplicationService pecaApplicationService;

    @Mock
    private ServicoApplicationService servicoApplicationService;

    @InjectMocks
    private OrcamentoApplicationService orcamentoApplicationService;

    private Cliente cliente;
    private Veiculo veiculo;
    private Orcamento orcamento;
    private PecaInsumo pecaInsumo1;
    private PecaInsumo pecaInsumo2;
    private Servico servico1;
    private Servico servico2;
    private VeiculoModelo veiculoModelo;
    private Endereco endereco;
    private ItemPecaOrcamento itemPecaOrcamento1;
    private ItemPecaOrcamento itemPecaOrcamento2;
    private PecaOrcamentoInput pecaOrcamentoInput1;
    private PecaOrcamentoInput pecaOrcamentoInput2;

    private final String cpfCnpj = "59953085056";
    private final UUID clienteId = UUID.randomUUID();
    private final UUID veiculoId = UUID.randomUUID();
    private final Long orcamentoId = 1L;
    private final Long pecaId1 = 1L;
    private final Long pecaId2 = 2L;
    private final Long servicoId1 = 1L;
    private final Long servicoId2 = 2L;

    @BeforeEach
    void setUp() {
        var dataAtual = LocalDateTime.now();

        endereco = new Endereco(UUID.randomUUID(), "Rua das Flores", "123", "Centro", "38400-123",
                "Uberlândia", "MG");

        veiculoModelo = new VeiculoModelo("Toyota", "Corolla", 2015, 2025, "Sedan");

        veiculo = new Veiculo(veiculoId, "FFA3I31", "2020", "Branco", veiculoModelo);

        cliente = new Cliente(clienteId, "João", "Silva", cpfCnpj,
                "joao@email.com", endereco, "34999999999");
        cliente.adicionarVeiculo(veiculo);

        pecaInsumo1 = new PecaInsumo(
                "Pastilha de Freio",
                "Pastilha de freio dianteira",
                "PF001",
                "Bosch",
                Arrays.asList(veiculoModelo),
                10,
                BigDecimal.valueOf(100.00),
                BigDecimal.valueOf(150.00),
                "Freios",
                true,
                dataAtual.minusDays(30),
                dataAtual
        );
        ReflectionTestUtils.setField(pecaInsumo1, "id", pecaId1);

        pecaInsumo2 = new PecaInsumo(
                "Disco de Freio",
                "Disco de freio dianteiro",
                "DF001",
                "Brembo",
                Arrays.asList(veiculoModelo),
                5,
                BigDecimal.valueOf(200.00),
                BigDecimal.valueOf(300.00),
                "Freios",
                true,
                dataAtual.minusDays(30),
                dataAtual
        );
        ReflectionTestUtils.setField(pecaInsumo2, "id", pecaId2);

        servico1 = new Servico(
                "Troca de Pastilhas",
                "Troca de pastilhas de freio dianteiras",
                BigDecimal.valueOf(80.00),
                60,
                "Freios",
                true,
                dataAtual.minusDays(30),
                dataAtual
        );
        ReflectionTestUtils.setField(servico1, "id", servicoId1);

        servico2 = new Servico(
                "Troca de Discos",
                "Troca de discos de freio dianteiros",
                BigDecimal.valueOf(120.00),
                90,
                "Freios",
                true,
                dataAtual.minusDays(30),
                dataAtual
        );
        ReflectionTestUtils.setField(servico2, "id", servicoId2);

        itemPecaOrcamento1 = new ItemPecaOrcamento(pecaInsumo1, 2);
        itemPecaOrcamento2 = new ItemPecaOrcamento(pecaInsumo2, 1);

        pecaOrcamentoInput1 = new PecaOrcamentoInput(pecaId1, 2);
        pecaOrcamentoInput2 = new PecaOrcamentoInput(pecaId2, 1);

        orcamento = new Orcamento(
                clienteId,
                veiculoId,
                Arrays.asList(itemPecaOrcamento1, itemPecaOrcamento2),
                Arrays.asList(servico1, servico2),
                OrcamentoStatus.CRIADO
        );
        ReflectionTestUtils.setField(orcamento, "id", orcamentoId);
    }

    @Test
    @DisplayName("Deve criar orçamento com sucesso")
    void deveCriarOrcamentoComSucesso() {
        // Arrange
        var pecasInput = Arrays.asList(pecaOrcamentoInput1, pecaOrcamentoInput2);
        var servicosId = Arrays.asList(servicoId1, servicoId2);

        when(clienteApplicationService.buscarPorCpfCnpj(cpfCnpj))
                .thenReturn(Optional.of(cliente));
        when(pecaApplicationService.buscarPorId(pecaId1))
                .thenReturn(pecaInsumo1);
        when(pecaApplicationService.buscarPorId(pecaId2))
                .thenReturn(pecaInsumo2);
        when(servicoApplicationService.buscar(servicoId1))
                .thenReturn(servico1);
        when(servicoApplicationService.buscar(servicoId2))
                .thenReturn(servico2);
        when(orcamentoRepository.save(any(Orcamento.class)))
                .thenReturn(orcamento);

        // Act
        var resultado = orcamentoApplicationService.criar(cpfCnpj, veiculoId, pecasInput, servicosId);

        // Assert
        verify(clienteApplicationService).buscarPorCpfCnpj(cpfCnpj);
        verify(pecaApplicationService).buscarPorId(pecaId1);
        verify(pecaApplicationService).buscarPorId(pecaId2);
        verify(servicoApplicationService).buscar(servicoId1);
        verify(servicoApplicationService).buscar(servicoId2);
        verify(orcamentoRepository).save(any(Orcamento.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não for encontrado ao criar orçamento")
    void deveLancarExcecaoQuandoClienteNaoForEncontradoAoCriar() {
        // Arrange
        var pecasInput = Arrays.asList(pecaOrcamentoInput1);
        var servicosId = Arrays.asList(servicoId1);

        when(clienteApplicationService.buscarPorCpfCnpj(cpfCnpj))
                .thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(OrcamentoException.class, () -> {
            orcamentoApplicationService.criar(cpfCnpj, veiculoId, pecasInput, servicosId);
        });

        assertEquals("Não foi encontrado o cliente vinculado ao orçamento informado", exception.getMessage());
        verify(clienteApplicationService).buscarPorCpfCnpj(cpfCnpj);
        verify(orcamentoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve aprovar orçamento com sucesso")
    void deveAprovarOrcamentoComSucesso() {
        // Arrange
        when(orcamentoRepository.findById(orcamentoId))
                .thenReturn(Optional.of(orcamento));

        // Act
        orcamentoApplicationService.aprovar(orcamentoId);

        // Assert
        verify(orcamentoRepository).findById(orcamentoId);
        verify(orcamentoDomainService).aprovar(orcamento);
        verify(orcamentoRepository).save(orcamento);
    }

    @Test
    @DisplayName("Deve lançar exceção ao aprovar orçamento não encontrado")
    void deveLancarExcecaoAoAprovarOrcamentoNaoEncontrado() {
        // Arrange
        when(orcamentoRepository.findById(orcamentoId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrcamentoNaoEncontradaException.class, () -> {
            orcamentoApplicationService.aprovar(orcamentoId);
        });

        verify(orcamentoRepository).findById(orcamentoId);
        verify(orcamentoDomainService, never()).aprovar(any());
        verify(orcamentoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve recusar orçamento com sucesso")
    void deveRecusarOrcamentoComSucesso() {
        // Arrange
        when(orcamentoRepository.findById(orcamentoId))
                .thenReturn(Optional.of(orcamento));

        // Act
        orcamentoApplicationService.recusar(orcamentoId);

        // Assert
        verify(orcamentoRepository).findById(orcamentoId);
        verify(orcamentoDomainService).recusar(orcamento);
        verify(orcamentoRepository).save(orcamento);
    }

    @Test
    @DisplayName("Deve lançar exceção ao recusar orçamento não encontrado")
    void deveLancarExcecaoAoRecusarOrcamentoNaoEncontrado() {
        // Arrange
        when(orcamentoRepository.findById(orcamentoId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrcamentoNaoEncontradaException.class, () -> {
            orcamentoApplicationService.recusar(orcamentoId);
        });

        verify(orcamentoRepository).findById(orcamentoId);
        verify(orcamentoDomainService, never()).recusar(any());
        verify(orcamentoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve buscar entidade por ID com sucesso")
    void deveBuscarEntidadePorIdComSucesso() {
        // Arrange
        when(orcamentoRepository.findById(orcamentoId))
                .thenReturn(Optional.of(orcamento));

        // Act
        var resultado = orcamentoApplicationService.buscarEntidade(orcamentoId);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(orcamento, resultado.get());
        verify(orcamentoRepository).findById(orcamentoId);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio ao buscar entidade inexistente")
    void deveRetornarOptionalVazioAoBuscarEntidadeInexistente() {
        // Arrange
        when(orcamentoRepository.findById(orcamentoId))
                .thenReturn(Optional.empty());

        // Act
        var resultado = orcamentoApplicationService.buscarEntidade(orcamentoId);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(orcamentoRepository).findById(orcamentoId);
    }

    @Test
    @DisplayName("Deve buscar orçamento por ID com sucesso")
    void deveBuscarOrcamentoPorIdComSucesso() {
        // Arrange
        when(orcamentoRepository.findById(orcamentoId))
                .thenReturn(Optional.of(orcamento));
        when(clienteApplicationService.buscarEntidade(clienteId))
                .thenReturn(Optional.of(cliente));

        // Act
        var resultado = orcamentoApplicationService.buscarPorId(orcamentoId);

        // Assert
        assertNotNull(resultado);
        assertEquals(orcamentoId, resultado.id());
        assertEquals("João", resultado.cliente().nome());
        assertEquals("Silva", resultado.cliente().sobrenome());
        assertEquals(cpfCnpj, resultado.cliente().cpfCnpj());
//        assertEquals("FFA3I31", resultado.veiculo().placa());
//        assertEquals("Toyota", resultado.veiculo().marca());
//        assertEquals("2020", resultado.veiculo().ano());
//        assertEquals("Corolla", resultado.veiculo().modelo());
        assertEquals(OrcamentoStatus.CRIADO.name(), resultado.status());
        assertEquals(2, resultado.pecas().size());
        assertEquals(2, resultado.servicos().size());

        verify(orcamentoRepository).findById(orcamentoId);
        verify(clienteApplicationService).buscarEntidade(clienteId);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar orçamento não encontrado")
    void deveLancarExcecaoAoBuscarOrcamentoNaoEncontrado() {
        // Arrange
        when(orcamentoRepository.findById(orcamentoId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrcamentoNaoEncontradaException.class, () -> {
            orcamentoApplicationService.buscarPorId(orcamentoId);
        });

        verify(orcamentoRepository).findById(orcamentoId);
        verify(clienteApplicationService, never()).buscarEntidade(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não for encontrado ao buscar orçamento")
    void deveLancarExcecaoQuandoClienteNaoForEncontradoAoBuscar() {
        // Arrange
        when(orcamentoRepository.findById(orcamentoId))
                .thenReturn(Optional.of(orcamento));
        when(clienteApplicationService.buscarEntidade(clienteId))
                .thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(OrcamentoException.class, () -> {
            orcamentoApplicationService.buscarPorId(orcamentoId);
        });

        assertEquals("Não foi encontrado o cliente vinculado ao orçamento informado", exception.getMessage());
        verify(orcamentoRepository).findById(orcamentoId);
        verify(clienteApplicationService).buscarEntidade(clienteId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando veículo não for encontrado ao buscar orçamento")
    void deveLancarExcecaoQuandoVeiculoNaoForEncontradoAoBuscar() {
        // Arrange
        var veiculoInexistente = UUID.randomUUID();
        var orcamentoComVeiculoInexistente = new Orcamento(
                clienteId,
                veiculoInexistente,
                Arrays.asList(itemPecaOrcamento1),
                Arrays.asList(servico1),
                OrcamentoStatus.CRIADO
        );

        when(orcamentoRepository.findById(orcamentoId))
                .thenReturn(Optional.of(orcamentoComVeiculoInexistente));
        when(clienteApplicationService.buscarEntidade(clienteId))
                .thenReturn(Optional.of(cliente));

        // Act & Assert
        var exception = assertThrows(OrcamentoException.class, () -> {
            orcamentoApplicationService.buscarPorId(orcamentoId);
        });

        assertEquals("Não foi encontrado o veiculo vinculado ao orçamento informado", exception.getMessage());
        verify(orcamentoRepository).findById(orcamentoId);
        verify(clienteApplicationService).buscarEntidade(clienteId);
    }
}