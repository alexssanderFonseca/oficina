package br.com.alexsdm.postech.oficina.admin.pecaInsumo.entity;

import br.com.alexsdm.postech.oficina.veiculomodelo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.pecaInsumo.entity.PecaInsumo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PecaInsumoTest {

    private PecaInsumo pecaInsumo;
    private VeiculoModelo veiculoModelo1;
    private VeiculoModelo veiculoModelo2;
    private LocalDateTime dataAtual = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        veiculoModelo1 = new VeiculoModelo("Toyota", "Corolla", 2015, 2025, "Sedan");
        veiculoModelo2 = new VeiculoModelo("Honda", "Civic", 2010, 2020, "Sedan");

        pecaInsumo = new PecaInsumo(
                "Pastilha de Freio",
                "Pastilha de freio dianteira",
                "PF001",
                "Bosch",
                Arrays.asList(veiculoModelo1),
                10,
                BigDecimal.valueOf(100.00),
                BigDecimal.valueOf(150.00),
                "Freios",
                true,
                dataAtual.minusDays(30),
                dataAtual
        );
    }

    @DisplayName("Deve criar peça insumo com construtor padrão")
    @Test
    void deveCriarPecaInsumoComConstrutorPadrao() {
        // Act
        var pecaVazia = new PecaInsumo();

        // Assert
        assertNotNull(pecaVazia);
        assertNull(pecaVazia.getId());
        assertNull(pecaVazia.getNome());
        assertNull(pecaVazia.getDescricao());
        assertNull(pecaVazia.getCodigoFabricante());
        assertNull(pecaVazia.getMarca());
        assertNull(pecaVazia.getModelosCompativeis());
        assertNull(pecaVazia.getQuantidadeEstoque());
        assertNull(pecaVazia.getPrecoCusto());
        assertNull(pecaVazia.getPrecoVenda());
        assertNull(pecaVazia.getCategoria());
        assertNull(pecaVazia.getAtivo());
        assertNull(pecaVazia.getDataCadastro());
        assertNull(pecaVazia.getDataAtualizacao());
    }

    @DisplayName("Deve criar peça insumo com construtor completo")
    @Test
    void deveCriarPecaInsumoComConstrutorCompleto() {
        // Assert
        assertNotNull(pecaInsumo);
        assertEquals("Pastilha de Freio", pecaInsumo.getNome());
        assertEquals("Pastilha de freio dianteira", pecaInsumo.getDescricao());
        assertEquals("PF001", pecaInsumo.getCodigoFabricante());
        assertEquals("Bosch", pecaInsumo.getMarca());
        assertEquals(1, pecaInsumo.getModelosCompativeis().size());
        assertEquals(veiculoModelo1, pecaInsumo.getModelosCompativeis().get(0));
        assertEquals(10, pecaInsumo.getQuantidadeEstoque());
        assertEquals(BigDecimal.valueOf(100.00), pecaInsumo.getPrecoCusto());
        assertEquals(BigDecimal.valueOf(150.00), pecaInsumo.getPrecoVenda());
        assertEquals("Freios", pecaInsumo.getCategoria());
        assertTrue(pecaInsumo.getAtivo());
        assertEquals(dataAtual.minusDays(30), pecaInsumo.getDataCadastro());
        assertEquals(dataAtual, pecaInsumo.getDataAtualizacao());
    }

    @DisplayName("Deve criar peça com múltiplos modelos compatíveis")
    @Test
    void deveCriarPecaComMultiplosModelosCompativeis() {
        // Arrange & Act
        var pecaMultiplosModelos = new PecaInsumo(
                "Filtro de Óleo",
                "Filtro de óleo universal",
                "FO002",
                "Mann",
                Arrays.asList(veiculoModelo1, veiculoModelo2),
                20,
                BigDecimal.valueOf(25.00),
                BigDecimal.valueOf(45.00),
                "Motor",
                true,
                dataAtual,
                dataAtual
        );

        // Assert
        assertEquals(2, pecaMultiplosModelos.getModelosCompativeis().size());
        assertTrue(pecaMultiplosModelos.getModelosCompativeis().contains(veiculoModelo1));
        assertTrue(pecaMultiplosModelos.getModelosCompativeis().contains(veiculoModelo2));
    }

    @DisplayName("Deve criar peça sem modelos compatíveis")
    @Test
    void deveCriarPecaSemModelosCompativeis() {
        // Arrange & Act
        var pecaSemModelos = new PecaInsumo(
                "Óleo Motor",
                "Óleo lubrificante universal",
                "OM003",
                "Castrol",
                Collections.emptyList(),
                50,
                BigDecimal.valueOf(20.00),
                BigDecimal.valueOf(35.00),
                "Lubrificantes",
                true,
                dataAtual,
                dataAtual
        );

        // Assert
        assertNotNull(pecaSemModelos.getModelosCompativeis());
        assertTrue(pecaSemModelos.getModelosCompativeis().isEmpty());
    }

    @DisplayName("Deve verificar disponibilidade quando tem estoque")
    @Test
    void deveVerificarDisponibilidadeQuandoTemEstoque() {
        // Assert
        assertTrue(pecaInsumo.isDisponivel());
    }

    @DisplayName("Deve verificar indisponibilidade quando estoque é zero")
    @Test
    void deveVerificarIndisponibilidadeQuandoEstoqueEZero() {
        // Arrange
        var pecaEstoqueZero = new PecaInsumo(
                "Peça Zerada",
                "Descrição",
                "PZ001",
                "Marca",
                Arrays.asList(veiculoModelo1),
                0,
                BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(80.00),
                "Categoria",
                true,
                dataAtual,
                dataAtual
        );

        // Act & Assert
        assertFalse(pecaEstoqueZero.isDisponivel());
    }

    @DisplayName("Deve verificar indisponibilidade quando estoque é negativo")
    @Test
    void deveVerificarIndisponibilidadeQuandoEstoqueENegativo() {
        // Arrange
        var pecaEstoqueNegativo = new PecaInsumo(
                "Peça Negativa",
                "Descrição",
                "PN001",
                "Marca",
                Arrays.asList(veiculoModelo1),
                -5,
                BigDecimal.valueOf(30.00),
                BigDecimal.valueOf(50.00),
                "Categoria",
                true,
                dataAtual,
                dataAtual
        );

        // Act & Assert
        assertFalse(pecaEstoqueNegativo.isDisponivel());
    }

    @DisplayName("Deve verificar indisponibilidade quando estoque é nulo")
    @Test
    void deveVerificarIndisponibilidadeQuandoEstoqueENulo() {
        // Arrange
        var pecaEstoqueNulo = new PecaInsumo(
                "Peça Nula",
                "Descrição",
                "PN002",
                "Marca",
                Arrays.asList(veiculoModelo1),
                null,
                BigDecimal.valueOf(40.00),
                BigDecimal.valueOf(70.00),
                "Categoria",
                true,
                dataAtual,
                dataAtual
        );

        // Act & Assert
        assertFalse(pecaEstoqueNulo.isDisponivel());
    }

    @DisplayName("Deve retirar quantidade do estoque corretamente")
    @Test
    void deveRetirarQuantidadeDoEstoqueCorretamente() {
        // Arrange
        var estoqueInicial = pecaInsumo.getQuantidadeEstoque();
        var quantidadeRetirar = 3;

        // Act
        pecaInsumo.retirar(quantidadeRetirar);

        // Assert
        assertEquals(estoqueInicial - quantidadeRetirar, pecaInsumo.getQuantidadeEstoque());
        assertEquals(7, pecaInsumo.getQuantidadeEstoque());
        assertTrue(pecaInsumo.isDisponivel());
    }

    @DisplayName("Deve zerar estoque ao retirar quantidade igual ao disponível")
    @Test
    void deveZerarEstoqueAoRetirarQuantidadeIgualAoDisponivel() {
        // Arrange
        var quantidadeRetirar = pecaInsumo.getQuantidadeEstoque();

        // Act
        pecaInsumo.retirar(quantidadeRetirar);

        // Assert
        assertEquals(0, pecaInsumo.getQuantidadeEstoque());
        assertFalse(pecaInsumo.isDisponivel());
    }

    @DisplayName("Deve deixar estoque negativo ao retirar mais que disponível")
    @Test
    void deveDeixarEstoqueNegativoAoRetirarMaisQueDisponivel() {
        // Arrange
        var quantidadeRetirar = 15; // Maior que 10

        // Act
        pecaInsumo.retirar(quantidadeRetirar);

        // Assert
        assertEquals(-5, pecaInsumo.getQuantidadeEstoque());
        assertFalse(pecaInsumo.isDisponivel());
    }

    @DisplayName("Deve retirar quantidade zero sem alterar estoque")
    @Test
    void deveRetirarQuantidadeZeroSemAlterarEstoque() {
        // Arrange
        var estoqueInicial = pecaInsumo.getQuantidadeEstoque();

        // Act
        pecaInsumo.retirar(0);

        // Assert
        assertEquals(estoqueInicial, pecaInsumo.getQuantidadeEstoque());
        assertTrue(pecaInsumo.isDisponivel());
    }

    @DisplayName("Deve manter disponibilidade após retirar quantidade pequena")
    @Test
    void deveManterDisponibilidadeAposRetirarQuantidadePequena() {
        // Arrange
        var quantidadeRetirar = 1;

        // Act
        pecaInsumo.retirar(quantidadeRetirar);

        // Assert
        assertEquals(9, pecaInsumo.getQuantidadeEstoque());
        assertTrue(pecaInsumo.isDisponivel());
    }

    @DisplayName("Deve permitir múltiplas retiradas consecutivas")
    @Test
    void devePermitirMultiplasRetiradasConsecutivas() {
        // Act
        pecaInsumo.retirar(2);
        pecaInsumo.retirar(3);
        pecaInsumo.retirar(1);

        // Assert
        assertEquals(4, pecaInsumo.getQuantidadeEstoque());
        assertTrue(pecaInsumo.isDisponivel());
    }

    @DisplayName("Deve criar peça inativa")
    @Test
    void deveCriarPecaInativa() {
        // Arrange & Act
        var pecaInativa = new PecaInsumo(
                "Peça Inativa",
                "Peça descontinuada",
                "PI001",
                "Marca",
                Arrays.asList(veiculoModelo1),
                5,
                BigDecimal.valueOf(60.00),
                BigDecimal.valueOf(90.00),
                "Categoria",
                false, // Inativa
                dataAtual,
                dataAtual
        );

        // Assert
        assertFalse(pecaInativa.getAtivo());
        assertTrue(pecaInativa.isDisponivel()); // Ainda tem estoque
    }

    @DisplayName("Deve criar peça com valores monetários altos")
    @Test
    void deveCriarPecaComValoresMonetariosAltos() {
        // Arrange & Act
        var pecaCara = new PecaInsumo(
                "Motor Completo",
                "Motor completo recondicionado",
                "MC001",
                "Marca Premium",
                Arrays.asList(veiculoModelo1),
                2,
                BigDecimal.valueOf(8000.00),
                BigDecimal.valueOf(12000.00),
                "Motor",
                true,
                dataAtual,
                dataAtual
        );

        // Assert
        assertEquals(BigDecimal.valueOf(8000.00), pecaCara.getPrecoCusto());
        assertEquals(BigDecimal.valueOf(12000.00), pecaCara.getPrecoVenda());
        assertTrue(pecaCara.isDisponivel());
    }

    @DisplayName("Deve criar peça com valores monetários zero")
    @Test
    void deveCriarPecaComValoresMonetariosZero() {
        // Arrange & Act
        var pecaGratis = new PecaInsumo(
                "Brinde",
                "Item promocional",
                "BR001",
                "Marca",
                Arrays.asList(veiculoModelo1),
                100,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                "Promocional",
                true,
                dataAtual,
                dataAtual
        );

        // Assert
        assertEquals(BigDecimal.ZERO, pecaGratis.getPrecoCusto());
        assertEquals(BigDecimal.ZERO, pecaGratis.getPrecoVenda());
        assertTrue(pecaGratis.isDisponivel());
    }
}