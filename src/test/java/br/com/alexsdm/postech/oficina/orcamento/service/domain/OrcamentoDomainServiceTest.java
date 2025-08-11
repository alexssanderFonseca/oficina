package br.com.alexsdm.postech.oficina.orcamento.service.domain;

import br.com.alexsdm.postech.oficina.admin.pecaInsumo.model.PecaInsumo;
import br.com.alexsdm.postech.oficina.admin.servico.model.Servico;
import br.com.alexsdm.postech.oficina.admin.veiculo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.orcamento.model.ItemPecaOrcamento;
import br.com.alexsdm.postech.oficina.orcamento.model.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.model.OrcamentoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para OrcamentoDomainService")
class OrcamentoDomainServiceTest {

    private OrcamentoDomainService orcamentoDomainService;
    private Orcamento orcamento;
    private Orcamento orcamentoAceito;
    private Orcamento orcamentoRecusado;
    private UUID clienteId;
    private UUID veiculoId;
    private ItemPecaOrcamento itemPeca;
    private Servico servico;
    private Long orcamentoId;

    @BeforeEach
    void setUp() {
        orcamentoDomainService = new OrcamentoDomainService();

        clienteId = UUID.randomUUID();
        veiculoId = UUID.randomUUID();
        orcamentoId = 1L;

        LocalDateTime dataAtual = LocalDateTime.now();

        var veiculoModelo = new VeiculoModelo("Toyota", "Corolla", 2015, 2025, "Sedan");

        var peca = new PecaInsumo(
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

        servico = new Servico(
                "Troca de Pastilhas",
                "Troca de pastilhas de freio dianteiras",
                BigDecimal.valueOf(100.00),
                60,
                "Freios",
                true,
                dataAtual.minusDays(30),
                dataAtual
        );

        itemPeca = new ItemPecaOrcamento(peca, 2);

        orcamento = new Orcamento(
                clienteId,
                veiculoId,
                Arrays.asList(itemPeca),
                Arrays.asList(servico),
                OrcamentoStatus.CRIADO
        );

        orcamentoAceito = new Orcamento(
                clienteId,
                veiculoId,
                Arrays.asList(itemPeca),
                Arrays.asList(servico),
                OrcamentoStatus.ACEITO
        );

        orcamentoRecusado = new Orcamento(
                clienteId,
                veiculoId,
                Arrays.asList(itemPeca),
                Arrays.asList(servico),
                OrcamentoStatus.RECUSADO
        );
    }

    @Test
    @DisplayName("Deve aprovar orçamento com sucesso")
    void deveAprovarOrcamentoComSucesso() {
        // Arrange
        assertEquals(OrcamentoStatus.CRIADO, orcamento.getStatus());
        assertFalse(orcamento.isAceito());

        // Act
        orcamentoDomainService.aprovar(orcamento);

        // Assert
        assertEquals(OrcamentoStatus.ACEITO, orcamento.getStatus());
        assertTrue(orcamento.isAceito());
    }

    @Test
    @DisplayName("Deve recusar orçamento com sucesso")
    void deveRecusarOrcamentoComSucesso() {
        // Arrange
        assertEquals(OrcamentoStatus.CRIADO, orcamento.getStatus());
        assertFalse(orcamento.isAceito());

        // Act
        orcamentoDomainService.recusar(orcamento);

        // Assert
        assertEquals(OrcamentoStatus.RECUSADO, orcamento.getStatus());
        assertFalse(orcamento.isAceito());
    }

    @Test
    @DisplayName("Deve aprovar orçamento já aceito")
    void deveAprovarOrcamentoJaAceito() {
        // Arrange
        assertTrue(orcamentoAceito.isAceito());
        assertEquals(OrcamentoStatus.ACEITO, orcamentoAceito.getStatus());

        // Act
        orcamentoDomainService.aprovar(orcamentoAceito);

        // Assert
        assertEquals(OrcamentoStatus.ACEITO, orcamentoAceito.getStatus());
        assertTrue(orcamentoAceito.isAceito());
    }

    @Test
    @DisplayName("Deve recusar orçamento já recusado")
    void deveRecusarOrcamentoJaRecusado() {
        // Arrange
        assertEquals(OrcamentoStatus.RECUSADO, orcamentoRecusado.getStatus());
        assertFalse(orcamentoRecusado.isAceito());

        // Act
        orcamentoDomainService.recusar(orcamentoRecusado);

        // Assert
        assertEquals(OrcamentoStatus.RECUSADO, orcamentoRecusado.getStatus());
        assertFalse(orcamentoRecusado.isAceito());
    }

    @Test
    @DisplayName("Deve aprovar orçamento recusado")
    void deveAprovarOrcamentoRecusado() {
        // Arrange
        assertEquals(OrcamentoStatus.RECUSADO, orcamentoRecusado.getStatus());
        assertFalse(orcamentoRecusado.isAceito());

        // Act
        orcamentoDomainService.aprovar(orcamentoRecusado);

        // Assert
        assertEquals(OrcamentoStatus.ACEITO, orcamentoRecusado.getStatus());
        assertTrue(orcamentoRecusado.isAceito());
    }

    @Test
    @DisplayName("Deve recusar orçamento aceito")
    void deveRecusarOrcamentoAceito() {
        // Arrange
        assertEquals(OrcamentoStatus.ACEITO, orcamentoAceito.getStatus());
        assertTrue(orcamentoAceito.isAceito());

        // Act
        orcamentoDomainService.recusar(orcamentoAceito);

        // Assert
        assertEquals(OrcamentoStatus.RECUSADO, orcamentoAceito.getStatus());
        assertFalse(orcamentoAceito.isAceito());
    }

    @Test
    @DisplayName("Deve manter integridade do orçamento após aprovação")
    void deveManterIntegridadeDoOrcamentoAposAprovacao() {
        // Arrange
        ReflectionTestUtils.setField(orcamento, "id", orcamentoId);
        var valorTotal = orcamento.getValorTotal();
        var clienteOriginal = orcamento.getClienteId();
        var veiculoOriginal = orcamento.getVeiculoId();

        // Act
        orcamentoDomainService.aprovar(orcamento);

        // Assert
        assertEquals(OrcamentoStatus.ACEITO, orcamento.getStatus());
        assertEquals(orcamentoId, orcamento.getId());
        assertEquals(valorTotal, orcamento.getValorTotal());
        assertEquals(clienteOriginal, orcamento.getClienteId());
        assertEquals(veiculoOriginal, orcamento.getVeiculoId());
        assertNotNull(orcamento.getItensPeca());
        assertNotNull(orcamento.getServicos());
        assertEquals(1, orcamento.getItensPeca().size());
        assertEquals(1, orcamento.getServicos().size());
    }

    @Test
    @DisplayName("Deve manter integridade do orçamento após recusa")
    void deveManterIntegridadeDoOrcamentoAposRecusa() {
        // Arrange
        ReflectionTestUtils.setField(orcamento, "id", orcamentoId);
        var valorTotal = orcamento.getValorTotal();
        var clienteOriginal = orcamento.getClienteId();
        var veiculoOriginal = orcamento.getVeiculoId();

        // Act
        orcamentoDomainService.recusar(orcamento);

        // Assert
        assertEquals(OrcamentoStatus.RECUSADO, orcamento.getStatus());
        assertEquals(orcamentoId, orcamento.getId());
        assertEquals(valorTotal, orcamento.getValorTotal());
        assertEquals(clienteOriginal, orcamento.getClienteId());
        assertEquals(veiculoOriginal, orcamento.getVeiculoId());
        assertNotNull(orcamento.getItensPeca());
        assertNotNull(orcamento.getServicos());
        assertEquals(1, orcamento.getItensPeca().size());
        assertEquals(1, orcamento.getServicos().size());
    }

    @Test
    @DisplayName("Deve processar múltiplas transições de status")
    void deveProcessarMultiplasTransicoesDeStatus() {
        // Arrange
        assertEquals(OrcamentoStatus.CRIADO, orcamento.getStatus());

        // Act & Assert - CRIADO -> ACEITO
        orcamentoDomainService.aprovar(orcamento);
        assertEquals(OrcamentoStatus.ACEITO, orcamento.getStatus());
        assertTrue(orcamento.isAceito());

        // Act & Assert - ACEITO -> RECUSADO
        orcamentoDomainService.recusar(orcamento);
        assertEquals(OrcamentoStatus.RECUSADO, orcamento.getStatus());
        assertFalse(orcamento.isAceito());

        // Act & Assert - RECUSADO -> ACEITO
        orcamentoDomainService.aprovar(orcamento);
        assertEquals(OrcamentoStatus.ACEITO, orcamento.getStatus());
        assertTrue(orcamento.isAceito());

        // Act & Assert - ACEITO -> RECUSADO
        orcamentoDomainService.recusar(orcamento);
        assertEquals(OrcamentoStatus.RECUSADO, orcamento.getStatus());
        assertFalse(orcamento.isAceito());
    }

    @Test
    @DisplayName("Deve validar comportamento com orçamentos diferentes")
    void deveValidarComportamentoComOrcamentosDiferentes() {
        // Arrange
        var orcamento1 = new Orcamento(
                UUID.randomUUID(),
                UUID.randomUUID(),
                Arrays.asList(itemPeca),
                Arrays.asList(servico),
                OrcamentoStatus.CRIADO
        );

        var orcamento2 = new Orcamento(
                UUID.randomUUID(),
                UUID.randomUUID(),
                Arrays.asList(itemPeca),
                Arrays.asList(servico),
                OrcamentoStatus.CRIADO
        );

        // Act
        orcamentoDomainService.aprovar(orcamento1);
        orcamentoDomainService.recusar(orcamento2);

        // Assert
        assertEquals(OrcamentoStatus.ACEITO, orcamento1.getStatus());
        assertEquals(OrcamentoStatus.RECUSADO, orcamento2.getStatus());
        assertTrue(orcamento1.isAceito());
        assertFalse(orcamento2.isAceito());
        assertNotEquals(orcamento1.getClienteId(), orcamento2.getClienteId());
        assertNotEquals(orcamento1.getVeiculoId(), orcamento2.getVeiculoId());
    }

    @Test
    @DisplayName("Deve aprovar e recusar orçamentos em sequência")
    void deveAprovarERecusarOrcamentosEmSequencia() {
        // Arrange
        var orcamentos = Arrays.asList(
                new Orcamento(clienteId, veiculoId, Arrays.asList(itemPeca), Arrays.asList(servico), OrcamentoStatus.CRIADO),
                new Orcamento(clienteId, veiculoId, Arrays.asList(itemPeca), Arrays.asList(servico), OrcamentoStatus.CRIADO),
                new Orcamento(clienteId, veiculoId, Arrays.asList(itemPeca), Arrays.asList(servico), OrcamentoStatus.CRIADO)
        );

        // Act
        orcamentoDomainService.aprovar(orcamentos.get(0));
        orcamentoDomainService.recusar(orcamentos.get(1));
        orcamentoDomainService.aprovar(orcamentos.get(2));

        // Assert
        assertEquals(OrcamentoStatus.ACEITO, orcamentos.get(0).getStatus());
        assertEquals(OrcamentoStatus.RECUSADO, orcamentos.get(1).getStatus());
        assertEquals(OrcamentoStatus.ACEITO, orcamentos.get(2).getStatus());

        assertTrue(orcamentos.get(0).isAceito());
        assertFalse(orcamentos.get(1).isAceito());
        assertTrue(orcamentos.get(2).isAceito());
    }
}