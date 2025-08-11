package br.com.alexsdm.postech.oficina.ordemServico.service.application;

import br.com.alexsdm.postech.oficina.admin.cliente.entity.Cliente;
import br.com.alexsdm.postech.oficina.admin.cliente.entity.Endereco;
import br.com.alexsdm.postech.oficina.admin.cliente.entity.Veiculo;
import br.com.alexsdm.postech.oficina.admin.cliente.service.application.ClienteApplicationService;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.service.application.PecaInsumoApplicationService;
import br.com.alexsdm.postech.oficina.admin.servico.entity.Servico;
import br.com.alexsdm.postech.oficina.admin.veiculomodelo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.orcamento.entity.ItemPecaOrcamento;
import br.com.alexsdm.postech.oficina.orcamento.entity.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.entity.OrcamentoStatus;
import br.com.alexsdm.postech.oficina.orcamento.service.application.OrcamentoApplicationService;
import br.com.alexsdm.postech.oficina.ordemServico.controller.request.CriarOrdemDeServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.exception.*;
import br.com.alexsdm.postech.oficina.ordemServico.entity.ItemPecaOrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.entity.Status;
import br.com.alexsdm.postech.oficina.ordemServico.repository.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.ordemServico.service.application.input.OsPecaNecessariasInput;
import br.com.alexsdm.postech.oficina.ordemServico.service.domain.OrdemServicoDomainService;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para OrdemServicoApplicationService")
class OrdemServicoApplicationServiceTest {

    @Mock
    private PecaInsumoApplicationService pecaApplicationService;

    @Mock
    private OrcamentoApplicationService orcamentoApplicationService;

    @Mock
    private OrdemServicoRepository ordemServicoRepository;

    @Mock
    private OrdemServicoDomainService ordemServicoDomainService;

    @Mock
    private ClienteApplicationService clienteApplicationService;

    @InjectMocks
    private OrdemServicoApplicationService ordemServicoApplicationService;

    private Cliente cliente;
    private Veiculo veiculo;
    private OrdemServico ordemServico;
    private Orcamento orcamento;
    private CriarOrdemDeServicoRequest request;
    private VeiculoModelo veiculoModelo;
    private PecaInsumo pecaInsumo;
    private ItemPecaOrcamento itemPecaOrcamento;
    private Servico servico;
    private Endereco endereco;
    private String cpfCnpj = "59953085056";
    private ItemPecaOrdemServico itemPecaOrdemServico;

    private UUID clienteId = UUID.randomUUID();
    private UUID veiculoId = UUID.randomUUID();
    private Long orcamentoId = 1L;
    private Long ordemServicoId = 1L;

    @BeforeEach
    void setUp() {
        LocalDateTime dataAtual = LocalDateTime.now();

        endereco = new Endereco(UUID.randomUUID(), "Rua das Flores", "123", "Centro", "38400-123",
                "Uberlândia", "MG");

        veiculoModelo = new VeiculoModelo("Toyota", "Corolla", 2015, 2025, "Sedan");

        veiculo = new Veiculo(veiculoId, "FFA3I31", "2020", "Branco", veiculoModelo);

        cliente = new Cliente(clienteId, "João", "Silva", cpfCnpj,
                "joao@email.com", endereco, "34999999999");

        cliente.adicionarVeiculo(veiculo);

        ordemServico = new OrdemServico(clienteId, veiculoId, Status.RECEBIDA);
        ReflectionTestUtils.setField(ordemServico, "id", 1L);

        pecaInsumo = new PecaInsumo(
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

        ReflectionTestUtils.setField(pecaInsumo, "id", 1L);

        itemPecaOrcamento = new ItemPecaOrcamento(pecaInsumo, 2);

        itemPecaOrdemServico = new ItemPecaOrdemServico(pecaInsumo,
                pecaInsumo.getPrecoVenda(),
                10,
                ordemServico);

        ReflectionTestUtils.setField(itemPecaOrdemServico, "id", 1L);


        servico = new Servico(
                "Troca de Pastilhas",
                "Troca de pastilhas de freio dianteiras",
                BigDecimal.valueOf(80.00),
                60, // 60 minutos
                "Freios",
                true,
                dataAtual.minusDays(30),
                dataAtual
        );


        orcamento = new Orcamento(
                clienteId,
                veiculoId,
                Arrays.asList(itemPecaOrcamento),
                Arrays.asList(servico),
                OrcamentoStatus.ACEITO
        );


        request = new CriarOrdemDeServicoRequest(cpfCnpj, veiculoId, null);
    }

    @Test
    @DisplayName("Deve criar ordem de serviço sem orçamento com sucesso")
    void deveCriarOrdemDeServicoSemOrcamentoComSucesso() {
        // Arrange
        when(clienteApplicationService.buscarPorCpfCnpj(cpfCnpj))
                .thenReturn(Optional.of(cliente));
        when(ordemServicoRepository.save(any(OrdemServico.class)))
                .thenReturn(ordemServico);

        // Act
        ordemServicoApplicationService.criar(request);

        // Assert
        verify(clienteApplicationService).buscarPorCpfCnpj(cpfCnpj);
        verify(ordemServicoRepository).save(any(OrdemServico.class));
    }

    @Test
    @DisplayName("Deve criar ordem de serviço com orçamento aceito com sucesso")
    void deveCriarOrdemDeServicoComOrcamentoAceitoComSucesso() {
        // Arrange
        var requestComOrcamento =
                new CriarOrdemDeServicoRequest(cpfCnpj, veiculoId, orcamentoId);

        when(clienteApplicationService.buscarPorCpfCnpj(cpfCnpj))
                .thenReturn(Optional.of(cliente));
        when(orcamentoApplicationService.buscarEntidade(orcamentoId))
                .thenReturn(Optional.of(orcamento));
        when(ordemServicoRepository.save(any(OrdemServico.class)))
                .thenReturn(ordemServico);

        // Act
        ordemServicoApplicationService.criar(requestComOrcamento);

        verify(orcamentoApplicationService).buscarEntidade(orcamentoId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não for encontrado")
    void deveLancarExcecaoQuandoClienteNaoForEncontrado() {
        // Arrange
        when(clienteApplicationService.buscarPorCpfCnpj(cpfCnpj))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrdemServicoClienteNaoEncontradoException.class, () -> {
            ordemServicoApplicationService.criar(request);
        });
        verify(ordemServicoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando veículo não for encontrado")
    void deveLancarExcecaoQuandoVeiculoNaoForEncontrado() {
        // Arrange
        var veiculoInexistente = UUID.randomUUID();
        CriarOrdemDeServicoRequest requestVeiculoInvalido =
                new CriarOrdemDeServicoRequest(cpfCnpj, veiculoInexistente, null);

        when(clienteApplicationService.buscarPorCpfCnpj(cpfCnpj))
                .thenReturn(Optional.of(cliente));
        // Act & Assert
        assertThrows(OrdemServicoVeiculoNaoEncontradoException.class, () -> {
            ordemServicoApplicationService.criar(requestVeiculoInvalido);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando orçamento não for encontrado")
    void deveLancarExcecaoQuandoOrcamentoNaoForEncontrado() {
        // Arrange
        var requestComOrcamento =
                new CriarOrdemDeServicoRequest(cpfCnpj, veiculoId, orcamentoId);

        when(clienteApplicationService.buscarPorCpfCnpj(cpfCnpj))
                .thenReturn(Optional.of(cliente));
        when(orcamentoApplicationService.buscarEntidade(orcamentoId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrdemServicoOrcamentoNaoEncontradoException.class, () -> {
            ordemServicoApplicationService.criar(requestComOrcamento);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando orçamento não estiver aceito")
    void deveLancarExcecaoQuandoOrcamentoNaoEstiverAceito() {
        // Arrange
        CriarOrdemDeServicoRequest requestComOrcamento =
                new CriarOrdemDeServicoRequest(cpfCnpj, veiculoId, orcamentoId);

        // Criando orçamento não aceito
        Orcamento orcamentoNaoAceito = new Orcamento(
                clienteId, veiculoId, Arrays.asList(itemPecaOrcamento),
                Arrays.asList(servico), OrcamentoStatus.CRIADO
        );

        when(clienteApplicationService.buscarPorCpfCnpj(cpfCnpj))
                .thenReturn(Optional.of(cliente));
        when(orcamentoApplicationService.buscarEntidade(orcamentoId))
                .thenReturn(Optional.of(orcamentoNaoAceito));

        // Act & Assert
        OrdemServicoException exception = assertThrows(OrdemServicoException.class, () -> {
            ordemServicoApplicationService.criar(requestComOrcamento);
        });
        assertEquals("Ordem de serviço não pode ser executada, sem o aceite do orçamento",
                exception.getMessage());
    }

    @Test
    @DisplayName("Deve iniciar diagnóstico com sucesso")
    void deveIniciarDiagnosticoComSucesso() {
        // Arrange
        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.of(ordemServico));

        // Act
        ordemServicoApplicationService.iniciarDiagnostico(ordemServicoId);

        // Assert
        verify(ordemServicoDomainService).iniciarDiagnostico(ordemServico);
        verify(ordemServicoRepository).save(ordemServico);
    }

    @Test
    @DisplayName("Deve lançar exceção ao iniciar diagnóstico de ordem inexistente")
    void deveLancarExcecaoAoIniciarDiagnosticoDeOrdemInexistente() {
        // Arrange
        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrdemServicoNaoEncontradaException.class, () -> {
            ordemServicoApplicationService.iniciarDiagnostico(ordemServicoId);
        });
        verify(ordemServicoDomainService, never()).iniciarDiagnostico(any());
    }

    @Test
    @DisplayName("Deve finalizar diagnóstico com sucesso")
    void deveFinalizarDiagnosticoComSucesso() {
        // Arrange
        var pecasNecessarias = Arrays.asList(
                new OsPecaNecessariasInput(1L, 2)
        );
        var servicosNecessarios = Arrays.asList(1L);

        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.of(ordemServico));
        when(clienteApplicationService.buscarEntidade(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        ordemServicoApplicationService.finalizarDiagnostico(ordemServicoId, pecasNecessarias, servicosNecessarios);

        // Assert
        verify(ordemServicoDomainService).finalizarDiagnostico(ordemServico);
        verify(ordemServicoRepository).save(ordemServico);
        verify(orcamentoApplicationService).criar(
                eq(cpfCnpj),
                eq(veiculoId),
                anyList(),
                eq(servicosNecessarios)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção ao finalizar diagnóstico quando cliente não for encontrado")
    void deveLancarExcecaoAoFinalizarDiagnosticoQuandoClienteNaoForEncontrado() {
        // Arrange
        var pecasNecessarias = Arrays.asList(
                new OsPecaNecessariasInput(1l, 2)
        );
        var servicosNecessarios = Arrays.asList(1L);

        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.of(ordemServico));
        when(clienteApplicationService.buscarEntidade(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrdemServicoClienteNaoEncontradoException.class, () -> {
            ordemServicoApplicationService.finalizarDiagnostico(ordemServicoId, pecasNecessarias, servicosNecessarios);
        });
    }

    @Test
    @DisplayName("Deve executar ordem de serviço com sucesso")
    void deveExecutarOrdemDeServicoComSucesso() {
        // Arrange
        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.of(ordemServico));
        when(orcamentoApplicationService.buscarEntidade(orcamentoId)).thenReturn(Optional.of(orcamento));


        // Act
        ordemServicoApplicationService.executar(ordemServicoId, orcamentoId);

        // Assert
        verify(pecaApplicationService).retirarItemEstoque(itemPecaOrdemServico.getId(), 2);
        verify(ordemServicoDomainService).executar(any(OrdemServico.class), anyList(), anyList());
        verify(ordemServicoRepository).save(ordemServico);
    }

    @Test
    @DisplayName("Deve lançar exceção ao executar quando orçamento não for encontrado")
    void deveLancarExcecaoAoExecutarQuandoOrcamentoNaoForEncontrado() {
        // Arrange
        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.of(ordemServico));
        when(orcamentoApplicationService.buscarEntidade(orcamentoId)).thenReturn(Optional.empty());

        // Act & Assert
        OrdemServicoException exception = assertThrows(OrdemServicoException.class, () -> {
            ordemServicoApplicationService.executar(ordemServicoId, orcamentoId);
        });
        assertEquals("Orcamento relacionado a ordem de servico não encontrada",
                exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao executar quando orçamento não estiver aceito")
    void deveLancarExcecaoAoExecutarQuandoOrcamentoNaoEstiverAceito() {
        // Arrange
        Orcamento orcamentoNaoAceito = new Orcamento(
                clienteId, veiculoId, Arrays.asList(itemPecaOrcamento),
                Arrays.asList(servico), OrcamentoStatus.CRIADO
        );

        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.of(ordemServico));
        when(orcamentoApplicationService.buscarEntidade(orcamentoId)).thenReturn(Optional.of(orcamentoNaoAceito));

        // Act & Assert
        OrdemServicoException exception = assertThrows(OrdemServicoException.class, () -> {
            ordemServicoApplicationService.executar(ordemServicoId, orcamentoId);
        });
        assertEquals("A ordem de servico não pode ser executada, sem aceite do orcamento",
                exception.getMessage());
    }

    @Test
    @DisplayName("Deve finalizar ordem de serviço com sucesso")
    void deveFinalizarOrdemDeServicoComSucesso() {
        // Arrange
        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.of(ordemServico));

        // Act
        ordemServicoApplicationService.finalizar(ordemServicoId);

        // Assert
        verify(ordemServicoDomainService).finalizar(ordemServico);
        verify(ordemServicoRepository).save(ordemServico);
    }

    @Test
    @DisplayName("Deve entregar ordem de serviço com sucesso")
    void deveEntregarOrdemDeServicoComSucesso() {
        // Arrange
        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.of(ordemServico));

        // Act
        ordemServicoApplicationService.entregar(ordemServicoId);

        // Assert
        verify(ordemServicoDomainService).entregar(ordemServico);
        verify(ordemServicoRepository).save(ordemServico);
    }

    @Test
    @DisplayName("Deve lançar exceção ao finalizar ordem inexistente")
    void deveLancarExcecaoAoFinalizarOrdemInexistente() {
        // Arrange
        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrdemServicoNaoEncontradaException.class, () -> {
            ordemServicoApplicationService.finalizar(ordemServicoId);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao entregar ordem inexistente")
    void deveLancarExcecaoAoEntregarOrdemInexistente() {
        // Arrange
        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrdemServicoNaoEncontradaException.class, () -> {
            ordemServicoApplicationService.entregar(ordemServicoId);
        });
    }

    @Test
    @DisplayName("Deve visualizar ordem de serviço com sucesso")
    void deveVisualizarOrdemDeServicoComSucesso() {

        ordemServico.executar(List.of(itemPecaOrdemServico), List.of(servico));

        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.of(ordemServico));
        when(clienteApplicationService.buscarEntidade(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        var resultado = ordemServicoApplicationService.visualizarOrdemServico(ordemServicoId);

        // Assert
        assertNotNull(resultado);
        assertEquals(ordemServicoId.toString(), resultado.id());
        assertEquals("João", resultado.dadosCliente().nome());
        assertEquals(cpfCnpj, resultado.dadosCliente().cpfCnpj());
        assertEquals("FFA3I31", resultado.dadosVeiculo().placa());
        assertEquals("Toyota", resultado.dadosVeiculo().marca());
        assertEquals("2020", resultado.dadosVeiculo().ano());
        assertEquals("Branco", resultado.dadosVeiculo().cor());
        assertEquals(1, resultado.pecasInsumos().size());
        assertEquals("Pastilha de Freio", resultado.pecasInsumos().get(0).nome());
        assertEquals(1, resultado.servicos().size());
        assertEquals("Troca de Pastilhas", resultado.servicos().get(0).nome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao visualizar ordem inexistente")
    void deveLancarExcecaoAoVisualizarOrdemInexistente() {
        // Arrange
        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrdemServicoNaoEncontradaException.class, () -> {
            ordemServicoApplicationService.visualizarOrdemServico(ordemServicoId);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao visualizar quando cliente não for encontrado")
    void deveLancarExcecaoAoVisualizarQuandoClienteNaoForEncontrado() {
        // Arrange
        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.of(ordemServico));
        when(clienteApplicationService.buscarEntidade(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrdemServicoClienteNaoEncontradoException.class, () -> {
            ordemServicoApplicationService.visualizarOrdemServico(ordemServicoId);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao visualizar quando veículo não for encontrado")
    void deveLancarExcecaoAoVisualizarQuandoVeiculoNaoForEncontrado() {
        // Arrange
        var veiculoInexistente = UUID.randomUUID();
        var ordemComVeiculoInexistente = new OrdemServico(clienteId, veiculoInexistente, Status.RECEBIDA);

        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.of(ordemComVeiculoInexistente));
        when(clienteApplicationService.buscarEntidade(clienteId)).thenReturn(Optional.of(cliente));

        // Act & Assert
        assertThrows(OrdemServicoVeiculoNaoEncontradoException.class, () -> {
            ordemServicoApplicationService.visualizarOrdemServico(ordemServicoId);
        });
    }

    @Test
    @DisplayName("Deve retornar tempo médio de execução")
    void deveRetornarTempoMedioDeExecucao() {
        // Arrange
        var tempoMedio = 3600.0; // 1 hora em segundos
        when(ordemServicoRepository.calcularTempoMedioExecucaoSegundos()).thenReturn(tempoMedio);

        // Act
        var resultado = ordemServicoApplicationService.getTempoMedioExecucaoServicosEmSegundos();

        // Assert
        assertEquals(tempoMedio, resultado);
        verify(ordemServicoRepository).calcularTempoMedioExecucaoSegundos();
    }


    @Test
    @DisplayName("Deve visualizar ordem de serviço sem peças e serviços")
    void deveVisualizarOrdemDeServicoSemPecasEServicos() {
        // Arrange

        when(ordemServicoRepository.findById(ordemServicoId)).thenReturn(Optional.of(ordemServico));
        when(clienteApplicationService.buscarEntidade(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        var resultado = ordemServicoApplicationService.visualizarOrdemServico(ordemServicoId);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.pecasInsumos().isEmpty());
        assertTrue(resultado.servicos().isEmpty());
    }
}