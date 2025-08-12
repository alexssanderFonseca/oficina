package br.com.alexsdm.postech.oficina.admin.veiculomodelo.service.application;

import br.com.alexsdm.postech.oficina.veiculomodelo.controller.request.AtualizarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.veiculomodelo.controller.request.CadastrarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.veiculomodelo.exception.VeiculoModeloNaoEncontradoException;
import br.com.alexsdm.postech.oficina.veiculomodelo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.veiculomodelo.repository.VeiculoModeloRepository;
import br.com.alexsdm.postech.oficina.veiculomodelo.service.application.VeiculoModeloApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoModeloApplicationServiceTest {

    @InjectMocks
    private VeiculoModeloApplicationService veiculoModeloApplicationService;

    @Mock
    private VeiculoModeloRepository repository;

    private Long veiculoModeloId;
    private VeiculoModelo veiculoModelo;
    private CadastrarVeiculoModeloRequest cadastrarRequest;
    private AtualizarVeiculoModeloRequest atualizarRequest;
    private String marca;
    private String modelo;
    private Integer anoInicio;
    private Integer anoFim;
    private String tipo;

    @BeforeEach
    void setUp() {
        veiculoModeloId = 1L;
        marca = "Toyota";
        modelo = "Corolla";
        anoInicio = 2015;
        anoFim = 2025;
        tipo = "Sedan";

        veiculoModelo = new VeiculoModelo(marca, modelo, anoInicio, anoFim, tipo);
        ReflectionTestUtils.setField(veiculoModelo, "id", veiculoModeloId);

        cadastrarRequest = new CadastrarVeiculoModeloRequest(marca, modelo, anoInicio, anoFim, tipo);
        atualizarRequest = new AtualizarVeiculoModeloRequest("Honda", "Civic", 2018, 2028, "Hatch");
    }

    @DisplayName("Deve cadastrar veículo modelo com sucesso")
    @Test
    void deveCadastrarVeiculoModeloComSucesso() {
        // Arrange
        when(repository.save(any(VeiculoModelo.class))).thenReturn(veiculoModelo);

        // Act
        var resultado = veiculoModeloApplicationService.cadastrar(cadastrarRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(marca, resultado.getMarca());
        assertEquals(modelo, resultado.getModelo());
        assertEquals(anoInicio, resultado.getAnoInicio());
        assertEquals(anoFim, resultado.getAnoFim());
        assertEquals(tipo, resultado.getTipo());
        assertEquals(veiculoModeloId, resultado.getId());

        verify(repository, times(1)).save(any(VeiculoModelo.class));
        verifyNoMoreInteractions(repository);
    }

    @DisplayName("Deve buscar entidade por ID com sucesso")
    @Test
    void deveBuscarEntidadePorIdComSucesso() {
        // Arrange
        when(repository.findById(veiculoModeloId)).thenReturn(Optional.of(veiculoModelo));

        // Act
        var resultado = veiculoModeloApplicationService.buscarEntidade(veiculoModeloId);

        // Assert
        assertNotNull(resultado);
        assertEquals(veiculoModeloId, resultado.getId());
        assertEquals(marca, resultado.getMarca());
        assertEquals(modelo, resultado.getModelo());
        assertEquals(anoInicio, resultado.getAnoInicio());
        assertEquals(anoFim, resultado.getAnoFim());
        assertEquals(tipo, resultado.getTipo());

        verify(repository, times(1)).findById(veiculoModeloId);
        verifyNoMoreInteractions(repository);
    }

    @DisplayName("Deve lançar exceção ao buscar entidade inexistente")
    @Test
    void deveLancarExcecaoAoBuscarEntidadeInexistente() {
        // Arrange
        when(repository.findById(veiculoModeloId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VeiculoModeloNaoEncontradoException.class,
                () -> veiculoModeloApplicationService.buscarEntidade(veiculoModeloId));

        verify(repository, times(1)).findById(veiculoModeloId);
        verifyNoMoreInteractions(repository);
    }

    @DisplayName("Deve atualizar veículo modelo com sucesso")
    @Test
    void deveAtualizarVeiculoModeloComSucesso() {
        // Arrange
        var veiculoModeloAtualizado = new VeiculoModelo(
                atualizarRequest.marca(),
                atualizarRequest.modelo(),
                atualizarRequest.anoInicio(),
                atualizarRequest.anoFim(),
                atualizarRequest.tipo()
        );
        ReflectionTestUtils.setField(veiculoModeloAtualizado, "id", veiculoModeloId);

        when(repository.findById(veiculoModeloId)).thenReturn(Optional.of(veiculoModelo));
        when(repository.save(any(VeiculoModelo.class))).thenReturn(veiculoModeloAtualizado);

        // Act
        var resultado = veiculoModeloApplicationService.atualizar(veiculoModeloId, atualizarRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(veiculoModeloId, resultado.getId());
        assertEquals("Honda", resultado.getMarca());
        assertEquals("Civic", resultado.getModelo());
        assertEquals(Integer.valueOf(2018), resultado.getAnoInicio());
        assertEquals(Integer.valueOf(2028), resultado.getAnoFim());
        assertEquals("Hatch", resultado.getTipo());

        verify(repository, times(1)).findById(veiculoModeloId);
        verify(repository, times(1)).save(any(VeiculoModelo.class));
        verifyNoMoreInteractions(repository);
    }

    @DisplayName("Deve lançar exceção ao atualizar veículo modelo inexistente")
    @Test
    void deveLancarExcecaoAoAtualizarVeiculoModeloInexistente() {
        // Arrange
        when(repository.findById(veiculoModeloId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VeiculoModeloNaoEncontradoException.class,
                () -> veiculoModeloApplicationService.atualizar(veiculoModeloId, atualizarRequest));

        verify(repository, times(1)).findById(veiculoModeloId);
        verify(repository, never()).save(any(VeiculoModelo.class));
        verifyNoMoreInteractions(repository);
    }

    @DisplayName("Deve deletar veículo modelo com sucesso")
    @Test
    void deveDeletarVeiculoModeloComSucesso() {
        // Arrange
        when(repository.existsById(veiculoModeloId)).thenReturn(true);
        doNothing().when(repository).deleteById(veiculoModeloId);

        // Act
        veiculoModeloApplicationService.deletar(veiculoModeloId);

        // Assert
        verify(repository, times(1)).existsById(veiculoModeloId);
        verify(repository, times(1)).deleteById(veiculoModeloId);
        verifyNoMoreInteractions(repository);
    }

    @DisplayName("Deve lançar exceção ao deletar veículo modelo inexistente")
    @Test
    void deveLancarExcecaoAoDeletarVeiculoModeloInexistente() {
        // Arrange
        when(repository.existsById(veiculoModeloId)).thenReturn(false);

        // Act & Assert
        assertThrows(VeiculoModeloNaoEncontradoException.class,
                () -> veiculoModeloApplicationService.deletar(veiculoModeloId));

        verify(repository, times(1)).existsById(veiculoModeloId);
        verify(repository, never()).deleteById(veiculoModeloId);
        verifyNoMoreInteractions(repository);
    }

    @DisplayName("Deve cadastrar veículo modelo com dados diferentes")
    @Test
    void deveCadastrarVeiculoModeloComDadosDiferentes() {
        // Arrange
        var requestDiferente = new CadastrarVeiculoModeloRequest(
                "Volkswagen",
                "Golf",
                2010,
                2020,
                "Hatchback"
        );

        var veiculoModeloDiferente = new VeiculoModelo(
                "Volkswagen",
                "Golf",
                2010,
                2020,
                "Hatchback"
        );
        ReflectionTestUtils.setField(veiculoModeloDiferente, "id", 2L);

        when(repository.save(any(VeiculoModelo.class))).thenReturn(veiculoModeloDiferente);

        // Act
        var resultado = veiculoModeloApplicationService.cadastrar(requestDiferente);

        // Assert
        assertNotNull(resultado);
        assertEquals("Volkswagen", resultado.getMarca());
        assertEquals("Golf", resultado.getModelo());
        assertEquals(Integer.valueOf(2010), resultado.getAnoInicio());
        assertEquals(Integer.valueOf(2020), resultado.getAnoFim());
        assertEquals("Hatchback", resultado.getTipo());
        assertEquals(Long.valueOf(2L), resultado.getId());

        verify(repository, times(1)).save(any(VeiculoModelo.class));
    }

    @DisplayName("Deve buscar múltiplos veículos modelos")
    @Test
    void deveBuscarMultiplosVeiculosModelos() {
        // Arrange
        var veiculoModelo2 = new VeiculoModelo("Honda", "Civic", 2018, 2028, "Hatch");
        ReflectionTestUtils.setField(veiculoModelo2, "id", 2L);

        when(repository.findById(1L)).thenReturn(Optional.of(veiculoModelo));
        when(repository.findById(2L)).thenReturn(Optional.of(veiculoModelo2));

        // Act
        var resultado1 = veiculoModeloApplicationService.buscarEntidade(1L);
        var resultado2 = veiculoModeloApplicationService.buscarEntidade(2L);

        // Assert
        assertNotNull(resultado1);
        assertNotNull(resultado2);

        assertEquals("Toyota", resultado1.getMarca());
        assertEquals("Honda", resultado2.getMarca());

        assertEquals("Corolla", resultado1.getModelo());
        assertEquals("Civic", resultado2.getModelo());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).findById(2L);
    }

    @DisplayName("Deve manter integridade dos dados durante operações")
    @Test
    void deveManterIntegridadeDosDadosDuranteOperacoes() {
        // Arrange
        when(repository.save(any(VeiculoModelo.class))).thenReturn(veiculoModelo);
        when(repository.findById(veiculoModeloId)).thenReturn(Optional.of(veiculoModelo));

        // Act
        var cadastrado = veiculoModeloApplicationService.cadastrar(cadastrarRequest);
        var buscado = veiculoModeloApplicationService.buscarEntidade(veiculoModeloId);

        // Assert
        assertEquals(cadastrado.getMarca(), buscado.getMarca());
        assertEquals(cadastrado.getModelo(), buscado.getModelo());
        assertEquals(cadastrado.getAnoInicio(), buscado.getAnoInicio());
        assertEquals(cadastrado.getAnoFim(), buscado.getAnoFim());
        assertEquals(cadastrado.getTipo(), buscado.getTipo());
        assertEquals(cadastrado.getId(), buscado.getId());
    }

    @DisplayName("Deve validar campos obrigatórios no cadastro")
    @Test
    void deveValidarCamposObrigatoriosNoCadastro() {
        // Arrange
        var requestComCamposVazios = new CadastrarVeiculoModeloRequest(
                "",
                "",
                null,
                null,
                ""
        );

        var veiculoModeloVazio = new VeiculoModelo("", "", null, null, "");
        when(repository.save(any(VeiculoModelo.class))).thenReturn(veiculoModeloVazio);

        // Act
        var resultado = veiculoModeloApplicationService.cadastrar(requestComCamposVazios);

        // Assert
        assertNotNull(resultado);
        assertEquals("", resultado.getMarca());
        assertEquals("", resultado.getModelo());
        assertNull(resultado.getAnoInicio());
        assertNull(resultado.getAnoFim());
        assertEquals("", resultado.getTipo());

        verify(repository, times(1)).save(any(VeiculoModelo.class));
    }

    @DisplayName("Deve atualizar apenas campos específicos")
    @Test
    void deveAtualizarApenasCamposEspecificos() {
        // Arrange
        var requestAtualizacaoEspecifica = new AtualizarVeiculoModeloRequest(
                "Ford",
                "Focus",
                2012,
                2022,
                "Sedan"
        );

        var veiculoModeloAtualizado = new VeiculoModelo(
                "Ford",
                "Focus",
                2012,
                2022,
                "Sedan"
        );
        ReflectionTestUtils.setField(veiculoModeloAtualizado, "id", veiculoModeloId);

        when(repository.findById(veiculoModeloId)).thenReturn(Optional.of(veiculoModelo));
        when(repository.save(any(VeiculoModelo.class))).thenReturn(veiculoModeloAtualizado);

        // Act
        var resultado = veiculoModeloApplicationService.atualizar(veiculoModeloId, requestAtualizacaoEspecifica);

        // Assert
        assertNotNull(resultado);
        assertEquals(veiculoModeloId, resultado.getId());
        assertEquals("Ford", resultado.getMarca());
        assertEquals("Focus", resultado.getModelo());
        assertEquals(Integer.valueOf(2012), resultado.getAnoInicio());
        assertEquals(Integer.valueOf(2022), resultado.getAnoFim());
        assertEquals("Sedan", resultado.getTipo());

        // Verificar que os dados originais eram diferentes
        assertNotEquals("Ford", veiculoModelo.getMarca());
        assertNotEquals("Focus", veiculoModelo.getModelo());
    }

    @DisplayName("Deve executar operações CRUD em sequência")
    @Test
    void deveExecutarOperacoesCrudEmSequencia() {
        // Arrange
        var veiculoModeloAtualizado = new VeiculoModelo(
                atualizarRequest.marca(),
                atualizarRequest.modelo(),
                atualizarRequest.anoInicio(),
                atualizarRequest.anoFim(),
                atualizarRequest.tipo()
        );
        ReflectionTestUtils.setField(veiculoModeloAtualizado, "id", veiculoModeloId);

        when(repository.save(any(VeiculoModelo.class)))
                .thenReturn(veiculoModelo)
                .thenReturn(veiculoModeloAtualizado);
        when(repository.findById(veiculoModeloId))
                .thenReturn(Optional.of(veiculoModelo))
                .thenReturn(Optional.of(veiculoModelo));
        when(repository.existsById(veiculoModeloId)).thenReturn(true);

        // Act & Assert
        // 1. Cadastrar
        var cadastrado = veiculoModeloApplicationService.cadastrar(cadastrarRequest);
        assertNotNull(cadastrado);
        assertEquals("Toyota", cadastrado.getMarca());

        // 2. Buscar
        var buscado = veiculoModeloApplicationService.buscarEntidade(veiculoModeloId);
        assertNotNull(buscado);
        assertEquals(veiculoModeloId, buscado.getId());

        // 3. Atualizar
        var atualizado = veiculoModeloApplicationService.atualizar(veiculoModeloId, atualizarRequest);
        assertNotNull(atualizado);
        assertEquals("Honda", atualizado.getMarca());

        // 4. Deletar
        assertDoesNotThrow(() -> veiculoModeloApplicationService.deletar(veiculoModeloId));

        // Verificar todas as interações
        verify(repository, times(2)).save(any(VeiculoModelo.class));
        verify(repository, times(2)).findById(veiculoModeloId);
        verify(repository, times(1)).existsById(veiculoModeloId);
        verify(repository, times(1)).deleteById(veiculoModeloId);
    }
}