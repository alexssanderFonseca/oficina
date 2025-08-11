package br.com.alexsdm.postech.oficina.orcamento.model;

import br.com.alexsdm.postech.oficina.admin.pecaInsumo.model.PecaInsumo;
import br.com.alexsdm.postech.oficina.admin.servico.model.Servico;
import br.com.alexsdm.postech.oficina.admin.veiculo.model.VeiculoModelo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrcamentoTest {

    private Orcamento orcamento;
    private UUID clienteId;
    private UUID veiculoId;
    private List<ItemPecaOrcamento> itensPeca;
    private List<Servico> servicos;
    private ItemPecaOrcamento itemPeca1;
    private ItemPecaOrcamento itemPeca2;
    private Servico servico1;
    private Servico servico2;
    private PecaInsumo peca1;
    private PecaInsumo peca2;
    private VeiculoModelo veiculoModelo;
    private Long orcamentoId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        veiculoId = UUID.randomUUID();
        orcamentoId = 1L;

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

        itemPeca1 = new ItemPecaOrcamento(peca1, 2); // 2 pastilhas = 160.00
        itemPeca2 = new ItemPecaOrcamento(peca2, 4); // 4 litros = 180.00

        itensPeca = Arrays.asList(itemPeca1, itemPeca2);
        servicos = Arrays.asList(servico1, servico2);

        orcamento = new Orcamento(
                clienteId,
                veiculoId,
                itensPeca,
                servicos,
                OrcamentoStatus.CRIADO
        );
    }

    @DisplayName("Deve criar orçamento com construtor parametrizado")
    @Test
    void deveCriarOrcamentoComConstrutorParametrizado() {
        // Act
        var novoOrcamento = new Orcamento(
                clienteId,
                veiculoId,
                itensPeca,
                servicos,
                OrcamentoStatus.CRIADO
        );

        // Assert
        assertNotNull(novoOrcamento);
        assertEquals(clienteId, novoOrcamento.getClienteId());
        assertEquals(veiculoId, novoOrcamento.getVeiculoId());
        assertEquals(itensPeca, novoOrcamento.getItensPeca());
        assertEquals(servicos, novoOrcamento.getServicos());
        assertEquals(OrcamentoStatus.CRIADO, novoOrcamento.getStatus());
        assertNotNull(novoOrcamento.getValorTotal());
        assertNotNull(novoOrcamento.getValorTotalEmPecas());
        assertNotNull(novoOrcamento.getValorTotalEmServicos());
        assertNull(novoOrcamento.getId());
    }

    @DisplayName("Deve criar orçamento com construtor padrão")
    @Test
    void deveCriarOrcamentoComConstrutorPadrao() {
        // Act
        var orcamentoVazio = new Orcamento();

        // Assert
        assertNotNull(orcamentoVazio);
        assertNull(orcamentoVazio.getId());
        assertNull(orcamentoVazio.getClienteId());
        assertNull(orcamentoVazio.getVeiculoId());
        assertNull(orcamentoVazio.getItensPeca());
        assertNull(orcamentoVazio.getServicos());
        assertNull(orcamentoVazio.getStatus());
        assertNull(orcamentoVazio.getValorTotal());
        assertNull(orcamentoVazio.getValorTotalEmPecas());
        assertNull(orcamentoVazio.getValorTotalEmServicos());
    }

    @DisplayName("Deve calcular valor total corretamente")
    @Test
    void deveCalcularValorTotalCorretamente() {
        // Act
        var valorTotal = orcamento.calcularValorTotal();

        // Assert
        // Peças: (80.00 * 2) + (45.00 * 4) = 160.00 + 180.00 = 340.00
        // Serviços: 100.00 + 80.00 = 180.00
        // Total: 340.00 + 180.00 = 520.00
        assertEquals(BigDecimal.valueOf(520.00), valorTotal);
        assertEquals(valorTotal, orcamento.getValorTotal());
    }

    @DisplayName("Deve calcular valor total de serviços corretamente")
    @Test
    void deveCalcularValorTotalDeServicosCorretamente() {
        // Act
        var valorTotalServicos = orcamento.calcularValorTotalServicos();

        // Assert
        assertEquals(BigDecimal.valueOf(180.00), valorTotalServicos);
        assertEquals(valorTotalServicos, orcamento.getValorTotalEmServicos());
    }

    @DisplayName("Deve calcular valor total de peças corretamente")
    @Test
    void deveCalcularValorTotalDePecasCorretamente() {
        // Act
        var valorTotalPecas = orcamento.calcularValorTotalPecas();

        // Assert
        assertEquals(BigDecimal.valueOf(340.00), valorTotalPecas);
        assertEquals(valorTotalPecas, orcamento.getValorTotalEmPecas());
    }

    @DisplayName("Deve aceitar orçamento com sucesso")
    @Test
    void deveAceitarOrcamentoComSucesso() {
        // Arrange
        assertEquals(OrcamentoStatus.CRIADO, orcamento.getStatus());
        assertFalse(orcamento.isAceito());

        // Act
        orcamento.aceitar();

        // Assert
        assertEquals(OrcamentoStatus.ACEITO, orcamento.getStatus());
        assertTrue(orcamento.isAceito());
    }

    @DisplayName("Deve recusar orçamento com sucesso")
    @Test
    void deveRecusarOrcamentoComSucesso() {
        // Arrange
        assertEquals(OrcamentoStatus.CRIADO, orcamento.getStatus());

        // Act
        orcamento.recusar();

        // Assert
        assertEquals(OrcamentoStatus.RECUSADO, orcamento.getStatus());
        assertFalse(orcamento.isAceito());
    }

    @DisplayName("Deve verificar se orçamento está aceito")
    @Test
    void deveVerificarSeOrcamentoEstaAceito() {
        // Arrange
        assertFalse(orcamento.isAceito());

        // Act
        orcamento.aceitar();

        // Assert
        assertTrue(orcamento.isAceito());
    }

    @DisplayName("Deve calcular valor zero quando não há peças")
    @Test
    void deveCalcularValorZeroQuandoNaoHaPecas() {
        // Arrange
        var orcamentoSemPecas = new Orcamento(
                clienteId,
                veiculoId,
                Collections.emptyList(),
                servicos,
                OrcamentoStatus.CRIADO
        );

        // Act
        var valorPecas = orcamentoSemPecas.calcularValorTotalPecas();
        var valorTotal = orcamentoSemPecas.calcularValorTotal();

        // Assert
        assertEquals(BigDecimal.ZERO, valorPecas);
        assertEquals(BigDecimal.valueOf(180.00), valorTotal);
    }

    @DisplayName("Deve calcular valor zero quando não há serviços")
    @Test
    void deveCalcularValorZeroQuandoNaoHaServicos() {
        // Arrange
        var orcamentoSemServicos = new Orcamento(
                clienteId,
                veiculoId,
                itensPeca,
                Collections.emptyList(),
                OrcamentoStatus.CRIADO
        );

        // Act
        var valorServicos = orcamentoSemServicos.calcularValorTotalServicos();
        var valorTotal = orcamentoSemServicos.calcularValorTotal();

        // Assert
        assertEquals(BigDecimal.ZERO, valorServicos);
        assertEquals(BigDecimal.valueOf(340.00), valorTotal);
    }

    @DisplayName("Deve calcular valor zero para orçamento vazio")
    @Test
    void deveCalcularValorZeroParaOrcamentoVazio() {
        // Arrange
        var orcamentoVazio = new Orcamento(
                clienteId,
                veiculoId,
                Collections.emptyList(),
                Collections.emptyList(),
                OrcamentoStatus.CRIADO
        );

        // Act
        var valorTotal = orcamentoVazio.calcularValorTotal();
        var valorPecas = orcamentoVazio.calcularValorTotalPecas();
        var valorServicos = orcamentoVazio.calcularValorTotalServicos();

        // Assert
        assertEquals(BigDecimal.ZERO, valorTotal);
        assertEquals(BigDecimal.ZERO, valorPecas);
        assertEquals(BigDecimal.ZERO, valorServicos);
    }


    @DisplayName("Deve manter integridade após mudanças de status")
    @Test
    void deveManterIntegridadeAposMudancasDeStatus() {
        // Arrange
        ReflectionTestUtils.setField(orcamento, "id", orcamentoId);
        var valorTotalOriginal = orcamento.getValorTotal();

        // Act
        orcamento.aceitar();
        orcamento.recusar();
        orcamento.aceitar();

        // Assert
        assertEquals(OrcamentoStatus.ACEITO, orcamento.getStatus());
        assertTrue(orcamento.isAceito());
        assertEquals(orcamentoId, orcamento.getId());
        assertEquals(clienteId, orcamento.getClienteId());
        assertEquals(veiculoId, orcamento.getVeiculoId());
        assertEquals(valorTotalOriginal, orcamento.getValorTotal());
        assertEquals(itensPeca.size(), orcamento.getItensPeca().size());
        assertEquals(servicos.size(), orcamento.getServicos().size());
    }

    @DisplayName("Deve calcular valores com precisão decimal")
    @Test
    void deveCalcularValoresComPrecisaoDecimal() {
        // Arrange
        var pecaComPrecoDecimal = new PecaInsumo(
                "Filtro",
                "Filtro de óleo",
                "FO001",
                "Mann",
                Arrays.asList(veiculoModelo),
                5,
                BigDecimal.valueOf(15.99),
                new BigDecimal("25.99"),
                "Motor",
                true,
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now()
        );

        var itemComPrecoDecimal = new ItemPecaOrcamento(pecaComPrecoDecimal, 3);
        var itensComDecimal = Arrays.asList(itemComPrecoDecimal);

        var orcamentoDecimal = new Orcamento(
                clienteId,
                veiculoId,
                itensComDecimal,
                Collections.emptyList(),
                OrcamentoStatus.CRIADO
        );

        // Act
        var valorTotalPecas = orcamentoDecimal.calcularValorTotalPecas();
        var valorTotal = orcamentoDecimal.calcularValorTotal();

        // Assert
        assertEquals(new BigDecimal("77.97"), valorTotalPecas);
        assertEquals(new BigDecimal("77.97"), valorTotal);
    }

    @DisplayName("Deve validar transições de status")
    @Test
    void deveValidarTransicoesDeStatus() {
        // Arrange
        assertEquals(OrcamentoStatus.CRIADO, orcamento.getStatus());

        // Act & Assert - CRIADO -> ACEITO
        orcamento.aceitar();
        assertEquals(OrcamentoStatus.ACEITO, orcamento.getStatus());
        assertTrue(orcamento.isAceito());

        // Act & Assert - ACEITO -> RECUSADO
        orcamento.recusar();
        assertEquals(OrcamentoStatus.RECUSADO, orcamento.getStatus());
        assertFalse(orcamento.isAceito());

        // Act & Assert - RECUSADO -> ACEITO
        orcamento.aceitar();
        assertEquals(OrcamentoStatus.ACEITO, orcamento.getStatus());
        assertTrue(orcamento.isAceito());
    }

    @DisplayName("Deve manter consistência dos valores calculados")
    @Test
    void deveManterConsistenciaDosValoresCalculados() {
        // Act
        var valorPecasCalculado = orcamento.calcularValorTotalPecas();
        var valorServicosCalculado = orcamento.calcularValorTotalServicos();
        var valorTotalCalculado = orcamento.calcularValorTotal();
        var somaPecasEServicos = valorPecasCalculado.add(valorServicosCalculado);

        // Assert
        assertEquals(valorPecasCalculado, orcamento.getValorTotalEmPecas());
        assertEquals(valorServicosCalculado, orcamento.getValorTotalEmServicos());
        assertEquals(valorTotalCalculado, orcamento.getValorTotal());
        assertEquals(somaPecasEServicos, valorTotalCalculado);
        assertEquals(0, somaPecasEServicos.compareTo(valorTotalCalculado));
    }

    @DisplayName("Deve criar orçamento com um único item")
    @Test
    void deveCriarOrcamentoComUmUnicoItem() {
        // Arrange
        var itemUnico = Arrays.asList(itemPeca1);
        var servicoUnico = Arrays.asList(servico1);

        // Act
        var orcamentoSimples = new Orcamento(
                clienteId,
                veiculoId,
                itemUnico,
                servicoUnico,
                OrcamentoStatus.CRIADO
        );

        // Assert
        assertNotNull(orcamentoSimples);
        assertEquals(1, orcamentoSimples.getItensPeca().size());
        assertEquals(1, orcamentoSimples.getServicos().size());
        assertEquals(BigDecimal.valueOf(160.00), orcamentoSimples.getValorTotalEmPecas());
        assertEquals(BigDecimal.valueOf(100.00), orcamentoSimples.getValorTotalEmServicos());
        assertEquals(BigDecimal.valueOf(260.00), orcamentoSimples.getValorTotal());
    }
}