package br.com.alexsdm.postech.oficina.admin.veiculomodelo.service;

import br.com.alexsdm.postech.oficina.admin.veiculomodelo.controller.request.AtualizarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.admin.veiculomodelo.controller.request.CadastrarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.admin.veiculomodelo.exception.VeiculoModeloNaoEncontradoException;
import br.com.alexsdm.postech.oficina.admin.veiculomodelo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.admin.veiculomodelo.repository.VeiculoModeloRepository;
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
class VeiculoModeloServiceTest {

    @Mock
    private VeiculoModeloRepository repository;

    @InjectMocks
    private VeiculoApplicationModeloService veiculoModeloService;

    private VeiculoModelo veiculoModelo;
    private CadastrarVeiculoModeloRequest cadastrarRequest;
    private AtualizarVeiculoModeloRequest atualizarRequest;
    private Long modeloId;
    private String marca;
    private String modelo;
    private Integer anoInicio;
    private Integer anoFim;
    private String tipo;

    @BeforeEach
    void setUp() {
        modeloId = 1L;
        marca = "Toyota";
        modelo = "Corolla";
        anoInicio = 2015;
        anoFim = 2025;
        tipo = "Sedan";

        veiculoModelo = new VeiculoModelo(
                marca,
                modelo,
                anoInicio,
                anoFim,
                tipo
        );
        ReflectionTestUtils.setField(veiculoModelo, "id", modeloId);

        cadastrarRequest = new CadastrarVeiculoModeloRequest(
                marca,
                modelo,
                anoInicio,
                anoFim,
                tipo
        );

        atualizarRequest = new AtualizarVeiculoModeloRequest(
                "Honda",
                "Civic",
                2018,
                2028,
                "Hatch"
        );
    }

    @Test
    @DisplayName("Deve cadastrar veículo modelo com sucesso")
    void deveCadastrarVeiculoModeloComSucesso() {
        // Arrange
        when(repository.save(any(VeiculoModelo.class))).thenReturn(veiculoModelo);

        // Act
        var resultado = veiculoModeloService.cadastrar(cadastrarRequest);

        //Assert
        verify(repository).save(any(VeiculoModelo.class));
    }

    @Test
    @DisplayName("Deve buscar veículo modelo por ID com sucesso")
    void deveBuscarVeiculoModeloPorIdComSucesso() {
        // Arrange
        when(repository.findById(modeloId)).thenReturn(Optional.of(veiculoModelo));

        // Act
        var resultado = veiculoModeloService.buscar(modeloId);

        // Assert
        assertNotNull(resultado);
        assertEquals(modeloId, resultado.getId());
        assertEquals(marca, resultado.getMarca());
        assertEquals(modelo, resultado.getModelo());
        assertEquals(anoInicio, resultado.getAnoInicio());
        assertEquals(anoFim, resultado.getAnoFim());
        assertEquals(tipo, resultado.getTipo());
        verify(repository).findById(modeloId);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar veículo modelo inexistente")
    void deveLancarExcecaoAoBuscarVeiculoModeloInexistente() {
        // Arrange
        when(repository.findById(modeloId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VeiculoModeloNaoEncontradoException.class, () -> {
            veiculoModeloService.buscar(modeloId);
        });
        verify(repository).findById(modeloId);
    }

    @Test
    @DisplayName("Deve atualizar veículo modelo com sucesso")
    void deveAtualizarVeiculoModeloComSucesso() {
        // Arrange
        var veiculoModeloAtualizado = new VeiculoModelo(
                atualizarRequest.marca(),
                atualizarRequest.modelo(),
                atualizarRequest.anoInicio(),
                atualizarRequest.anoFim(),
                atualizarRequest.tipo()
        );

        when(repository.findById(modeloId)).thenReturn(Optional.of(veiculoModelo));
        when(repository.save(any(VeiculoModelo.class))).thenReturn(veiculoModeloAtualizado);

        // Act
        var resultado = veiculoModeloService.atualizar(modeloId, atualizarRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals("Honda", resultado.getMarca());
        assertEquals("Civic", resultado.getModelo());
        assertEquals(2018, resultado.getAnoInicio());
        assertEquals(2028, resultado.getAnoFim());
        assertEquals("Hatch", resultado.getTipo());
        verify(repository).findById(modeloId);
        verify(repository).save(any(VeiculoModelo.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar veículo modelo inexistente")
    void deveLancarExcecaoAoAtualizarVeiculoModeloInexistente() {
        // Arrange
        when(repository.findById(modeloId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VeiculoModeloNaoEncontradoException.class, () -> {
            veiculoModeloService.atualizar(modeloId, atualizarRequest);
        });
        verify(repository).findById(modeloId);
        verify(repository, never()).save(any(VeiculoModelo.class));
    }

    @Test
    @DisplayName("Deve deletar veículo modelo com sucesso")
    void deveDeletarVeiculoModeloComSucesso() {
        // Arrange
        when(repository.existsById(modeloId)).thenReturn(true);

        // Act
        veiculoModeloService.deletar(modeloId);

        // Assert
        verify(repository).existsById(modeloId);
        verify(repository).deleteById(modeloId);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar veículo modelo inexistente")
    void deveLancarExcecaoAoDeletarVeiculoModeloInexistente() {
        // Arrange
        when(repository.existsById(modeloId)).thenReturn(false);

        // Act & Assert
        assertThrows(VeiculoModeloNaoEncontradoException.class, () -> {
            veiculoModeloService.deletar(modeloId);
        });
        verify(repository).existsById(modeloId);
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve cadastrar veículo modelo com dados mínimos")
    void deveCadastrarVeiculoModeloComDadosMinimos() {
        // Arrange
        var requestMinimo = new CadastrarVeiculoModeloRequest(
                "Ford",
                "Ka",
                2010,
                2020,
                "Hatch"
        );
        var veiculoModeloMinimo = new VeiculoModelo(
                "Ford",
                "Ka",
                2010,
                2020,
                "Hatch"
        );

        when(repository.save(any(VeiculoModelo.class))).thenReturn(veiculoModeloMinimo);

        // Act
        veiculoModeloService.cadastrar(requestMinimo);

        verify(repository).save(any(VeiculoModelo.class));
    }

    @Test
    @DisplayName("Deve atualizar todos os campos do veículo modelo")
    void deveAtualizarTodosOsCamposDoVeiculoModelo() {
        // Arrange
        var requestCompleto = new AtualizarVeiculoModeloRequest(
                "Volkswagen",
                "Golf",
                2020,
                2030,
                "SUV"
        );
        var veiculoModeloCompleto = new VeiculoModelo(
                "Volkswagen",
                "Golf",
                2020,
                2030,
                "SUV"
        );

        when(repository.findById(modeloId)).thenReturn(Optional.of(veiculoModelo));
        when(repository.save(any(VeiculoModelo.class))).thenReturn(veiculoModeloCompleto);

        // Act
        var resultado = veiculoModeloService.atualizar(modeloId, requestCompleto);

        // Assert
        assertEquals("Volkswagen", resultado.getMarca());
        assertEquals("Golf", resultado.getModelo());
        assertEquals(2020, resultado.getAnoInicio());
        assertEquals(2030, resultado.getAnoFim());
        assertEquals("SUV", resultado.getTipo());
        verify(repository).findById(modeloId);
        verify(repository).save(any(VeiculoModelo.class));
    }


    @Test
    @DisplayName("Deve manter integridade ao buscar múltiplos modelos")
    void deveManterIntegridadeAoBuscarMultiplosModelos() {
        // Arrange
        var modeloId2 = 2L;
        var veiculoModelo2 = new VeiculoModelo("BMW", "X1", 2019, 2029, "SUV");
        ReflectionTestUtils.setField(veiculoModelo2, "id", modeloId2);

        when(repository.findById(modeloId)).thenReturn(Optional.of(veiculoModelo));
        when(repository.findById(modeloId2)).thenReturn(Optional.of(veiculoModelo2));

        // Act
        var resultado1 = veiculoModeloService.buscar(modeloId);
        var resultado2 = veiculoModeloService.buscar(modeloId2);

        // Assert
        assertNotEquals(resultado1.getId(), resultado2.getId());
        assertEquals("Toyota", resultado1.getMarca());
        assertEquals("BMW", resultado2.getMarca());
        assertEquals("Corolla", resultado1.getModelo());
        assertEquals("X1", resultado2.getModelo());
        verify(repository).findById(modeloId);
        verify(repository).findById(modeloId2);
    }

    @Test
    @DisplayName("Deve validar comportamento com IDs diferentes na deleção")
    void deveValidarComportamentoComIdsDiferentesNaDelecao() {
        // Arrange
        var idExistente = 1L;
        var idInexistente = 999L;

        when(repository.existsById(idExistente)).thenReturn(true);
        when(repository.existsById(idInexistente)).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> veiculoModeloService.deletar(idExistente));
        assertThrows(VeiculoModeloNaoEncontradoException.class, () -> {
            veiculoModeloService.deletar(idInexistente);
        });

        verify(repository).existsById(idExistente);
        verify(repository).existsById(idInexistente);
        verify(repository).deleteById(idExistente);
        verify(repository, never()).deleteById(idInexistente);
    }
}