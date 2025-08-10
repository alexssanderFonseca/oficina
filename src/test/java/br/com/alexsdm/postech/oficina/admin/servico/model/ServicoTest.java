package br.com.alexsdm.postech.oficina.admin.servico.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ServicoTest {

    private Servico servico;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private BigDecimal precoOriginal;
    private BigDecimal novoPreco;
    private String nome;
    private String descricao;
    private Integer duracaoEstimada;
    private String categoria;
    private Long servicoId;

    @BeforeEach
    void setUp() {
        dataCadastro = LocalDateTime.now().minusDays(10);
        dataAtualizacao = LocalDateTime.now().minusDays(5);
        precoOriginal = BigDecimal.valueOf(150.00);
        novoPreco = BigDecimal.valueOf(180.00);
        nome = "Troca de Óleo";
        descricao = "Troca completa de óleo do motor";
        duracaoEstimada = 30;
        categoria = "Motor";
        servicoId = 1L;

        servico = new Servico(
                nome,
                descricao,
                precoOriginal,
                duracaoEstimada,
                categoria,
                true,
                dataCadastro,
                dataAtualizacao
        );
    }

    @DisplayName("Deve criar serviço com construtor parametrizado")
    @Test
    void deveCriarServicoComConstrutorParametrizado() {
        // Act
        var novoServico = new Servico(
                "Alinhamento",
                "Alinhamento e balanceamento das rodas",
                BigDecimal.valueOf(120.00),
                45,
                "Pneus",
                true,
                dataCadastro,
                dataAtualizacao
        );

        // Assert
        assertNotNull(novoServico);
        assertEquals("Alinhamento", novoServico.getNome());
        assertEquals("Alinhamento e balanceamento das rodas", novoServico.getDescricao());
        assertEquals(BigDecimal.valueOf(120.00), novoServico.getPreco());
        assertEquals(45, novoServico.getDuracaoEstimada());
        assertEquals("Pneus", novoServico.getCategoria());
        assertTrue(novoServico.getAtivo());
        assertEquals(dataCadastro, novoServico.getDataCadastro());
        assertEquals(dataAtualizacao, novoServico.getDataAtualizacao());
        assertNull(novoServico.getId());
    }

    @DisplayName("Deve criar serviço com construtor padrão")
    @Test
    void deveCriarServicoComConstrutorPadrao() {
        // Act
        var servicoVazio = new Servico();

        // Assert
        assertNotNull(servicoVazio);
        assertNull(servicoVazio.getId());
        assertNull(servicoVazio.getNome());
        assertNull(servicoVazio.getDescricao());
        assertNull(servicoVazio.getPreco());
        assertNull(servicoVazio.getDuracaoEstimada());
        assertNull(servicoVazio.getCategoria());
        assertNull(servicoVazio.getAtivo());
        assertNull(servicoVazio.getDataCadastro());
        assertNull(servicoVazio.getDataAtualizacao());
    }

    @DisplayName("Deve atualizar preço e status ativo do serviço")
    @Test
    void deveAtualizarPrecoEStatusAtivoDoServico() {
        // Arrange
        ReflectionTestUtils.setField(servico, "id", servicoId);
        var dataAntes = servico.getDataAtualizacao();

        // Act
        servico.atualizar(novoPreco, false);

        // Assert
        assertEquals(novoPreco, servico.getPreco());
        assertFalse(servico.getAtivo());
        assertNotEquals(dataAntes, servico.getDataAtualizacao());
        assertTrue(servico.getDataAtualizacao().isAfter(dataAntes));

        // Verificar que outros campos não foram alterados
        assertEquals(servicoId, servico.getId());
        assertEquals(nome, servico.getNome());
        assertEquals(descricao, servico.getDescricao());
        assertEquals(duracaoEstimada, servico.getDuracaoEstimada());
        assertEquals(categoria, servico.getCategoria());
        assertEquals(dataCadastro, servico.getDataCadastro());
    }

    @DisplayName("Deve atualizar serviço ativo para inativo")
    @Test
    void deveAtualizarServicoAtivoParaInativo() {
        // Arrange
        assertTrue(servico.getAtivo());
        var precoAtual = servico.getPreco();

        // Act
        servico.atualizar(precoAtual, false);

        // Assert
        assertFalse(servico.getAtivo());
        assertEquals(precoAtual, servico.getPreco());
        assertNotNull(servico.getDataAtualizacao());
    }

    @DisplayName("Deve atualizar serviço inativo para ativo")
    @Test
    void deveAtualizarServicoInativoParaAtivo() {
        // Arrange
        servico.atualizar(precoOriginal, false);
        assertFalse(servico.getAtivo());

        // Act
        servico.atualizar(novoPreco, true);

        // Assert
        assertTrue(servico.getAtivo());
        assertEquals(novoPreco, servico.getPreco());
    }

    @DisplayName("Deve atualizar apenas o preço mantendo status ativo")
    @Test
    void deveAtualizarApenasPrecoMantendoStatusAtivo() {
        // Arrange
        var statusOriginal = servico.getAtivo();
        var novoPrecoMaior = BigDecimal.valueOf(200.00);

        // Act
        servico.atualizar(novoPrecoMaior, statusOriginal);

        // Assert
        assertEquals(novoPrecoMaior, servico.getPreco());
        assertEquals(statusOriginal, servico.getAtivo());
        assertNotNull(servico.getDataAtualizacao());
    }

    @DisplayName("Deve atualizar data de atualização ao chamar método atualizar")
    @Test
    void deveAtualizarDataDeAtualizacaoAoChamarMetodoAtualizar() {
        // Arrange
        var dataAtualizacaoAntes = servico.getDataAtualizacao();

        // Act
        servico.atualizar(precoOriginal, true);

        // Assert
        assertNotEquals(dataAtualizacaoAntes, servico.getDataAtualizacao());
        assertTrue(servico.getDataAtualizacao().isAfter(dataAtualizacaoAntes));
    }

    @DisplayName("Deve permitir preço zero na atualização")
    @Test
    void devePermitirPrecoZeroNaAtualizacao() {
        // Arrange
        var precoZero = BigDecimal.ZERO;

        // Act
        servico.atualizar(precoZero, true);

        // Assert
        assertEquals(precoZero, servico.getPreco());
        assertTrue(servico.getAtivo());
        assertNotNull(servico.getDataAtualizacao());
    }

    @DisplayName("Deve permitir preço negativo na atualização")
    @Test
    void devePermitirPrecoNegativoNaAtualizacao() {
        // Arrange
        var precoNegativo = BigDecimal.valueOf(-50.00);

        // Act
        servico.atualizar(precoNegativo, false);

        // Assert
        assertEquals(precoNegativo, servico.getPreco());
        assertFalse(servico.getAtivo());
    }

    @DisplayName("Deve manter integridade dos dados após múltiplas atualizações")
    @Test
    void deveManterIntegridadeDosDadosAposMultiplasAtualizacoes() {
        // Arrange
        ReflectionTestUtils.setField(servico, "id", servicoId);
        var dataOriginal = servico.getDataAtualizacao();

        // Act
        servico.atualizar(BigDecimal.valueOf(100.00), false);
        servico.atualizar(BigDecimal.valueOf(200.00), true);
        servico.atualizar(BigDecimal.valueOf(250.00), false);

        // Assert
        assertEquals(BigDecimal.valueOf(250.00), servico.getPreco());
        assertFalse(servico.getAtivo());
        assertTrue(servico.getDataAtualizacao().isAfter(dataOriginal));

        // Verificar que campos imutáveis não foram alterados
        assertEquals(servicoId, servico.getId());
        assertEquals(nome, servico.getNome());
        assertEquals(descricao, servico.getDescricao());
        assertEquals(duracaoEstimada, servico.getDuracaoEstimada());
        assertEquals(categoria, servico.getCategoria());
        assertEquals(dataCadastro, servico.getDataCadastro());
    }

    @DisplayName("Deve criar serviço com valores de BigDecimal precisos")
    @Test
    void deveCriarServicoComValoresDeBigDecimalPrecisos() {
        // Arrange
        var precoPreciso = new BigDecimal("199.99");

        // Act
        var servicoComPrecoPreciso = new Servico(
                "Revisão Completa",
                "Revisão completa do veículo",
                precoPreciso,
                120,
                "Revisão",
                true,
                dataCadastro,
                dataAtualizacao
        );

        // Assert
        assertEquals(precoPreciso, servicoComPrecoPreciso.getPreco());
        assertEquals(0, precoPreciso.compareTo(servicoComPrecoPreciso.getPreco()));
    }
}