package br.com.alexsdm.postech.oficina.orcamento.service.application;

import br.com.alexsdm.postech.oficina.admin.cliente.entity.Cliente;
import br.com.alexsdm.postech.oficina.admin.cliente.entity.Veiculo;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.admin.servico.entity.Servico;
import br.com.alexsdm.postech.oficina.admin.veiculomodelo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.orcamento.entity.ItemPecaOrcamento;
import br.com.alexsdm.postech.oficina.orcamento.entity.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.entity.OrcamentoStatus;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrcamentoPdfGeradorServiceTest {

    @InjectMocks
    private OrcamentoPdfGeradorService orcamentoPdfGeradorService;

    @Mock
    private Cliente cliente;

    @Mock
    private Veiculo veiculo;

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

        itemPeca1 = new ItemPecaOrcamento(peca1, 2);
        itemPeca2 = new ItemPecaOrcamento(peca2, 4);

        itensPeca = Arrays.asList(itemPeca1, itemPeca2);
        servicos = Arrays.asList(servico1, servico2);

        orcamento = new Orcamento(
                clienteId,
                veiculoId,
                itensPeca,
                servicos,
                OrcamentoStatus.CRIADO
        );

        ReflectionTestUtils.setField(orcamento, "id", orcamentoId);

        // Setup dos mocks
        when(cliente.getNomeCompleto()).thenReturn("João da Silva");
        when(cliente.getCpfCnpj()).thenReturn("123.456.789-00");
        when(veiculo.getDescricaoCompleta()).thenReturn("Toyota Corolla 2020 Sedan");
        when(veiculo.getPlaca()).thenReturn("ABC-1234");
    }

    @DisplayName("Deve gerar PDF com dados completos do orçamento")
    @Test
    void deveGerarPdfComDadosCompletosDoOrcamento() {
        // Act
        var pdfBytes = orcamentoPdfGeradorService.gerar(orcamento, cliente, veiculo);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        // Verificar que os dados do cliente foram acessados
        verify(cliente, times(1)).getNomeCompleto();
        verify(cliente, times(1)).getCpfCnpj();
        verify(veiculo, times(1)).getDescricaoCompleta();
        verify(veiculo, times(1)).getPlaca();
    }

    @DisplayName("Deve gerar PDF com orçamento sem peças")
    @Test
    void deveGerarPdfComOrcamentoSemPecas() {
        // Arrange
        var orcamentoSemPecas = new Orcamento(
                clienteId,
                veiculoId,
                Collections.emptyList(),
                servicos,
                OrcamentoStatus.CRIADO
        );

        // Act
        var pdfBytes = orcamentoPdfGeradorService.gerar(orcamentoSemPecas, cliente, veiculo);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        assertEquals(Collections.emptyList(), orcamentoSemPecas.getItensPeca());
        assertEquals(2, orcamentoSemPecas.getServicos().size());
    }

    @DisplayName("Deve gerar PDF com orçamento sem serviços")
    @Test
    void deveGerarPdfComOrcamentoSemServicos() {
        // Arrange
        var orcamentoSemServicos = new Orcamento(
                clienteId,
                veiculoId,
                itensPeca,
                Collections.emptyList(),
                OrcamentoStatus.CRIADO
        );

        // Act
        var pdfBytes = orcamentoPdfGeradorService.gerar(orcamentoSemServicos, cliente, veiculo);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        assertEquals(2, orcamentoSemServicos.getItensPeca().size());
        assertEquals(Collections.emptyList(), orcamentoSemServicos.getServicos());
    }

    @DisplayName("Deve gerar PDF com orçamento vazio")
    @Test
    void deveGerarPdfComOrcamentoVazio() {
        // Arrange
        var orcamentoVazio = new Orcamento(
                clienteId,
                veiculoId,
                Collections.emptyList(),
                Collections.emptyList(),
                OrcamentoStatus.CRIADO
        );

        // Act
        var pdfBytes = orcamentoPdfGeradorService.gerar(orcamentoVazio, cliente, veiculo);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        assertEquals(Collections.emptyList(), orcamentoVazio.getItensPeca());
        assertEquals(Collections.emptyList(), orcamentoVazio.getServicos());
        assertEquals(BigDecimal.ZERO, orcamentoVazio.getValorTotal());
    }

    @DisplayName("Deve gerar PDF com orçamento aceito")
    @Test
    void deveGerarPdfComOrcamentoAceito() {
        // Arrange
        orcamento.aceitar();

        // Act
        var pdfBytes = orcamentoPdfGeradorService.gerar(orcamento, cliente, veiculo);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        assertEquals(OrcamentoStatus.ACEITO, orcamento.getStatus());
        assertTrue(orcamento.isAceito());
    }

    @DisplayName("Deve gerar PDF com orçamento recusado")
    @Test
    void deveGerarPdfComOrcamentoRecusado() {
        // Arrange
        orcamento.recusar();

        // Act
        var pdfBytes = orcamentoPdfGeradorService.gerar(orcamento, cliente, veiculo);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        assertEquals(OrcamentoStatus.RECUSADO, orcamento.getStatus());
        assertFalse(orcamento.isAceito());
    }

    @DisplayName("Deve gerar PDF com um único item de peça")
    @Test
    void deveGerarPdfComUmUnicoItemDePeca() {
        // Arrange
        var itemUnico = Arrays.asList(itemPeca1);
        var orcamentoSimples = new Orcamento(
                clienteId,
                veiculoId,
                itemUnico,
                servicos,
                OrcamentoStatus.CRIADO
        );

        // Act
        var pdfBytes = orcamentoPdfGeradorService.gerar(orcamentoSimples, cliente, veiculo);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        assertEquals(1, orcamentoSimples.getItensPeca().size());
        assertEquals(2, orcamentoSimples.getServicos().size());
        assertEquals("Pastilha de Freio", orcamentoSimples.getItensPeca().get(0).getPeca().getNome());
    }

    @DisplayName("Deve gerar PDF com um único serviço")
    @Test
    void deveGerarPdfComUmUnicoServico() {
        // Arrange
        var servicoUnico = Arrays.asList(servico1);
        var orcamentoSimples = new Orcamento(
                clienteId,
                veiculoId,
                itensPeca,
                servicoUnico,
                OrcamentoStatus.CRIADO
        );

        // Act
        var pdfBytes = orcamentoPdfGeradorService.gerar(orcamentoSimples, cliente, veiculo);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        assertEquals(2, orcamentoSimples.getItensPeca().size());
        assertEquals(1, orcamentoSimples.getServicos().size());
        assertEquals("Troca de Pastilhas", orcamentoSimples.getServicos().get(0).getNome());
    }

    @DisplayName("Deve validar conteúdo do PDF gerado")
    @Test
    void deveValidarConteudoDoPdfGerado() {
        // Act
        var pdfBytes = orcamentoPdfGeradorService.gerar(orcamento, cliente, veiculo);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        // Verificar que é um PDF válido (verifica os primeiros bytes do cabeçalho PDF)
        assertEquals('%', (char) pdfBytes[0]);
        assertEquals('P', (char) pdfBytes[1]);
        assertEquals('D', (char) pdfBytes[2]);
        assertEquals('F', (char) pdfBytes[3]);

        // Verificar se todos os dados foram acessados
        verify(cliente, times(1)).getNomeCompleto();
        verify(cliente, times(1)).getCpfCnpj();
        verify(veiculo, times(1)).getDescricaoCompleta();
        verify(veiculo, times(1)).getPlaca();

        // Verificar valores calculados do orçamento
        assertNotNull(orcamento.getValorTotal());
        assertNotNull(orcamento.getValorTotalEmPecas());
        assertNotNull(orcamento.getValorTotalEmServicos());
        assertEquals(BigDecimal.valueOf(520.00), orcamento.getValorTotal());
    }

    @DisplayName("Deve gerar PDF com valores decimais precisos")
    @Test
    void deveGerarPdfComValoresDecimaisPrecisos() {
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
        var pdfBytes = orcamentoPdfGeradorService.gerar(orcamentoDecimal, cliente, veiculo);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        assertEquals(new BigDecimal("77.97"), orcamentoDecimal.getValorTotal());
        assertEquals(new BigDecimal("77.97"), orcamentoDecimal.getValorTotalEmPecas());
        assertEquals(BigDecimal.ZERO, orcamentoDecimal.getValorTotalEmServicos());
    }

    @DisplayName("Deve manter integridade dos dados após geração do PDF")
    @Test
    void deveManterIntegridadeDosDadosAposGeracaoDoPdf() {
        // Arrange
        var valorTotalOriginal = orcamento.getValorTotal();
        var statusOriginal = orcamento.getStatus();
        var quantidadeItensPecaOriginal = orcamento.getItensPeca().size();
        var quantidadeServicosOriginal = orcamento.getServicos().size();

        // Act
        var pdfBytes = orcamentoPdfGeradorService.gerar(orcamento, cliente, veiculo);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        // Verificar que os dados originais não foram alterados
        assertEquals(valorTotalOriginal, orcamento.getValorTotal());
        assertEquals(statusOriginal, orcamento.getStatus());
        assertEquals(quantidadeItensPecaOriginal, orcamento.getItensPeca().size());
        assertEquals(quantidadeServicosOriginal, orcamento.getServicos().size());
        assertEquals(orcamentoId, orcamento.getId());
        assertEquals(clienteId, orcamento.getClienteId());
        assertEquals(veiculoId, orcamento.getVeiculoId());
    }

    @DisplayName("Deve gerar múltiplos PDFs consecutivos")
    @Test
    void deveGerarMultiplosPdfsConsecutivos() {
        // Act
        var pdf1 = orcamentoPdfGeradorService.gerar(orcamento, cliente, veiculo);
        var pdf2 = orcamentoPdfGeradorService.gerar(orcamento, cliente, veiculo);
        var pdf3 = orcamentoPdfGeradorService.gerar(orcamento, cliente, veiculo);

        // Assert
        assertNotNull(pdf1);
        assertNotNull(pdf2);
        assertNotNull(pdf3);

        assertTrue(pdf1.length > 0);
        assertTrue(pdf2.length > 0);
        assertTrue(pdf3.length > 0);

        // PDFs gerados devem ter o mesmo tamanho (mesmo conteúdo)
        assertEquals(pdf1.length, pdf2.length);
        assertEquals(pdf2.length, pdf3.length);

        // Verificar múltiplas chamadas aos mocks
        verify(cliente, times(3)).getNomeCompleto();
        verify(cliente, times(3)).getCpfCnpj();
        verify(veiculo, times(3)).getDescricaoCompleta();
        verify(veiculo, times(3)).getPlaca();
    }
}