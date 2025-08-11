package br.com.alexsdm.postech.oficina.ordemServico.service.domain;

import br.com.alexsdm.postech.oficina.admin.pecaInsumo.model.PecaInsumo;
import br.com.alexsdm.postech.oficina.admin.servico.model.Servico;
import br.com.alexsdm.postech.oficina.admin.veiculo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.ordemServico.model.ItemPecaOrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.model.OrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.model.Status;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrdemServicoDomainServiceTest {

    @InjectMocks
    private OrdemServicoDomainService ordemServicoDomainService;

    @Mock
    private OrdemServico ordemServico;

    private UUID clienteId;
    private UUID veiculoId;
    private Long ordemServicoId;
    private List<ItemPecaOrdemServico> itemPecaOS;
    private List<Servico> servicos;
    private ItemPecaOrdemServico itemPeca1;
    private ItemPecaOrdemServico itemPeca2;
    private Servico servico1;
    private Servico servico2;
    private PecaInsumo peca1;
    private PecaInsumo peca2;
    private VeiculoModelo veiculoModelo;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        veiculoId = UUID.randomUUID();
        ordemServicoId = 1L;

        LocalDateTime dataAtual = LocalDateTime.now();

        veiculoModelo = new VeiculoModelo("Toyota", "Corolla", 2015, 2025, "Sedan");

        peca1 = new PecaInsumo(
                "Pastilha de Freio",
                "Pastilha de freio dianteira",
                "PF001",
                "Bosch",
                Arrays.asList(veiculoModelo),
                10,
                BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(80.00),
                "Freios",
                true,
                dataAtual.minusDays(30),
                dataAtual
        );

        peca2 = new PecaInsumo(
                "Óleo do Motor",
                "Óleo sintético 5W30",
                "OM001",
                "Mobil",
                Arrays.asList(veiculoModelo),
                20,
                BigDecimal.valueOf(30.00),
                BigDecimal.valueOf(45.00),
                "Motor",
                true,
                dataAtual.minusDays(30),
                dataAtual
        );

        servico1 = new Servico(
                "Troca de Pastilhas",
                "Troca de pastilhas de freio dianteiras",
                BigDecimal.valueOf(100.00),
                60,
                "Freios",
                true,
                dataAtual.minusDays(30),
                dataAtual
        );

        servico2 = new Servico(
                "Troca de Óleo",
                "Troca completa de óleo do motor",
                BigDecimal.valueOf(80.00),
                30,
                "Motor",
                true,
                dataAtual.minusDays(30),
                dataAtual
        );

        // Criando OrdemServico real para os ItemPecaOrdemServico
        var ordemServicoReal = new OrdemServico(clienteId, veiculoId, Status.RECEBIDA);
        ReflectionTestUtils.setField(ordemServicoReal, "id", ordemServicoId);

        itemPeca1 = new ItemPecaOrdemServico(peca1, BigDecimal.valueOf(80.00), 2, ordemServicoReal);
        itemPeca2 = new ItemPecaOrdemServico(peca2, BigDecimal.valueOf(45.00), 4, ordemServicoReal);

        itemPecaOS = Arrays.asList(itemPeca1, itemPeca2);
        servicos = Arrays.asList(servico1, servico2);
    }

    @DisplayName("Deve iniciar diagnóstico com sucesso")
    @Test
    void deveIniciarDiagnosticoComSucesso() {
        // Arrange
        doNothing().when(ordemServico).diagnosticar();

        // Act
        ordemServicoDomainService.iniciarDiagnostico(ordemServico);

        // Assert
        verify(ordemServico, times(1)).diagnosticar();
        verifyNoMoreInteractions(ordemServico);
    }

    @DisplayName("Deve finalizar diagnóstico com sucesso")
    @Test
    void deveFinalizarDiagnosticoComSucesso() {
        // Arrange
        doNothing().when(ordemServico).finalizarDiagnostico();

        // Act
        ordemServicoDomainService.finalizarDiagnostico(ordemServico);

        // Assert
        verify(ordemServico, times(1)).finalizarDiagnostico();
        verifyNoMoreInteractions(ordemServico);
    }

    @DisplayName("Deve executar ordem de serviço com peças e serviços")
    @Test
    void deveExecutarOrdemDeServicoComPecasEServicos() {
        // Arrange
        doNothing().when(ordemServico).executar(itemPecaOS, servicos);

        // Act
        ordemServicoDomainService.executar(ordemServico, itemPecaOS, servicos);

        // Assert
        verify(ordemServico, times(1)).executar(itemPecaOS, servicos);
        verifyNoMoreInteractions(ordemServico);
    }

    @DisplayName("Deve executar ordem de serviço com lista de peças vazia")
    @Test
    void deveExecutarOrdemDeServicoComListaDePecasVazia() {
        // Arrange
        var itemPecaVazia = Collections.<ItemPecaOrdemServico>emptyList();
        doNothing().when(ordemServico).executar(itemPecaVazia, servicos);

        // Act
        ordemServicoDomainService.executar(ordemServico, itemPecaVazia, servicos);

        // Assert
        verify(ordemServico, times(1)).executar(itemPecaVazia, servicos);
        verifyNoMoreInteractions(ordemServico);
    }

    @DisplayName("Deve executar ordem de serviço com lista de serviços vazia")
    @Test
    void deveExecutarOrdemDeServicoComListaDeServicosVazia() {
        // Arrange
        var servicosVazios = Collections.<Servico>emptyList();
        doNothing().when(ordemServico).executar(itemPecaOS, servicosVazios);

        // Act
        ordemServicoDomainService.executar(ordemServico, itemPecaOS, servicosVazios);

        // Assert
        verify(ordemServico, times(1)).executar(itemPecaOS, servicosVazios);
        verifyNoMoreInteractions(ordemServico);
    }

    @DisplayName("Deve executar ordem de serviço com listas vazias")
    @Test
    void deveExecutarOrdemDeServicoComListasVazias() {
        // Arrange
        var itemPecaVazia = Collections.<ItemPecaOrdemServico>emptyList();
        var servicosVazios = Collections.<Servico>emptyList();
        doNothing().when(ordemServico).executar(itemPecaVazia, servicosVazios);

        // Act
        ordemServicoDomainService.executar(ordemServico, itemPecaVazia, servicosVazios);

        // Assert
        verify(ordemServico, times(1)).executar(itemPecaVazia, servicosVazios);
        verifyNoMoreInteractions(ordemServico);
    }

    @DisplayName("Deve finalizar ordem de serviço com sucesso")
    @Test
    void deveFinalizarOrdemDeServicoComSucesso() {
        // Arrange
        doNothing().when(ordemServico).finalizar();

        // Act
        ordemServicoDomainService.finalizar(ordemServico);

        // Assert
        verify(ordemServico, times(1)).finalizar();
        verifyNoMoreInteractions(ordemServico);
    }

    @DisplayName("Deve entregar ordem de serviço com sucesso")
    @Test
    void deveEntregarOrdemDeServicoComSucesso() {
        // Arrange
        doNothing().when(ordemServico).entregar();

        // Act
        ordemServicoDomainService.entregar(ordemServico);

        // Assert
        verify(ordemServico, times(1)).entregar();
        verifyNoMoreInteractions(ordemServico);
    }

    @DisplayName("Deve executar ordem de serviço com um único item de peça")
    @Test
    void deveExecutarOrdemDeServicoComUmUnicoItemDePeca() {
        // Arrange
        var itemUnico = Arrays.asList(itemPeca1);
        doNothing().when(ordemServico).executar(itemUnico, servicos);

        // Act
        ordemServicoDomainService.executar(ordemServico, itemUnico, servicos);

        // Assert
        verify(ordemServico, times(1)).executar(itemUnico, servicos);
        assertEquals(1, itemUnico.size());
        assertEquals(itemPeca1, itemUnico.get(0));
        verifyNoMoreInteractions(ordemServico);
    }

    @DisplayName("Deve executar ordem de serviço com um único serviço")
    @Test
    void deveExecutarOrdemDeServicoComUmUnicoServico() {
        // Arrange
        var servicoUnico = Arrays.asList(servico1);
        doNothing().when(ordemServico).executar(itemPecaOS, servicoUnico);

        // Act
        ordemServicoDomainService.executar(ordemServico, itemPecaOS, servicoUnico);

        // Assert
        verify(ordemServico, times(1)).executar(itemPecaOS, servicoUnico);
        assertEquals(1, servicoUnico.size());
        assertEquals(servico1, servicoUnico.get(0));
        verifyNoMoreInteractions(ordemServico);
    }

    @DisplayName("Deve validar integridade dos dados nas operações")
    @Test
    void deveValidarIntegridadeDosDadosNasOperacoes() {
        // Arrange
        doNothing().when(ordemServico).diagnosticar();
        doNothing().when(ordemServico).finalizarDiagnostico();
        doNothing().when(ordemServico).executar(itemPecaOS, servicos);
        doNothing().when(ordemServico).finalizar();
        doNothing().when(ordemServico).entregar();

        // Act
        ordemServicoDomainService.iniciarDiagnostico(ordemServico);
        ordemServicoDomainService.finalizarDiagnostico(ordemServico);
        ordemServicoDomainService.executar(ordemServico, itemPecaOS, servicos);
        ordemServicoDomainService.finalizar(ordemServico);
        ordemServicoDomainService.entregar(ordemServico);

        // Assert
        verify(ordemServico, times(1)).diagnosticar();
        verify(ordemServico, times(1)).finalizarDiagnostico();
        verify(ordemServico, times(1)).executar(itemPecaOS, servicos);
        verify(ordemServico, times(1)).finalizar();
        verify(ordemServico, times(1)).entregar();

        // Verificar que as listas mantiveram integridade
        assertNotNull(itemPecaOS);
        assertNotNull(servicos);
        assertEquals(2, itemPecaOS.size());
        assertEquals(2, servicos.size());
    }

    @DisplayName("Deve manter consistência ao executar múltiplas operações")
    @Test
    void deveManterConsistenciaAoExecutarMultiplasOperacoes() {
        // Arrange
        doNothing().when(ordemServico).diagnosticar();
        doNothing().when(ordemServico).executar(any(), any());
        doNothing().when(ordemServico).finalizar();

        // Act
        ordemServicoDomainService.iniciarDiagnostico(ordemServico);
        ordemServicoDomainService.executar(ordemServico, itemPecaOS, servicos);
        ordemServicoDomainService.executar(ordemServico, Collections.emptyList(), Collections.emptyList());
        ordemServicoDomainService.finalizar(ordemServico);

        // Assert
        verify(ordemServico, times(1)).diagnosticar();
        verify(ordemServico, times(2)).executar(any(), any());
        verify(ordemServico, times(1)).finalizar();

        // Verificar que os objetos originais não foram alterados
        assertEquals(2, itemPecaOS.size());
        assertEquals(2, servicos.size());
        assertEquals("Pastilha de Freio", itemPeca1.getPeca().getNome());
        assertEquals("Troca de Pastilhas", servico1.getNome());
    }

    @DisplayName("Deve tratar corretamente operações com dados nulos")
    @Test
    void deveTratarCorretamenteOperacoesComDadosNulos() {
        // Arrange
        doNothing().when(ordemServico).executar(isNull(), isNull());

        // Act
        ordemServicoDomainService.executar(ordemServico, null, null);

        // Assert
        verify(ordemServico, times(1)).executar(isNull(), isNull());
        verifyNoMoreInteractions(ordemServico);
    }
}