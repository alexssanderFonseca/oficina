package br.com.alexsdm.postech.oficina.integrados.cliente;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ClienteControllerIntegrationTest {

    private String token;

    @LocalServerPort
    private int port;


    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        token = authenticateAndGetToken();


    }

    private String authenticateAndGetToken() {
        return RestAssured.given()
                .port(port)
                .contentType("application/json")
                .body(JsonPayloads.login())
                .when()
                .post("/logins")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    private Long criarVeiculoModeloERetornarId() {
        String location = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloads.veiculoModelo())
                .when()
                .post("/veiculos-modelos")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .extract()
                .header("Location");

        String idString = location.substring(location.lastIndexOf("/") + 1);
        return Long.parseLong(idString);
    }

    private String criarClienteERetornarId(String jsonPayload) {
        String location = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .when()
                .post("/clientes")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .extract()

                .header("Location");

        return location.substring(location.lastIndexOf("/") + 1);
    }


    // ================================
    // TESTES POST /clientes - CENÁRIOS DE SUCESSO
    // ================================


    // ================================
    // TESTES POST /clientes - CENÁRIOS DE ERRO
    // ================================

    @Test
    public void naoDeveCadastrarClienteSemAutenticacao() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(JsonPayloads.clienteValido())
                .when()
                .post("/clientes")
                .then()
                .statusCode(401);
    }

    @Test
    public void naoDeveCadastrarClienteComDadosInvalidos() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloads.clienteComDadosInvalidos())
                .when()
                .post("/clientes")
                .then()
                .statusCode(400);
    }


    @Test
    public void naoDeveCadastrarClienteComCpfInvalido() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloads.clienteComCpfInvalido())
                .when()
                .post("/clientes")
                .then()
                .statusCode(400);
    }


    // ================================
    // TESTES GET /clientes/{id} - CENÁRIOS DE SUCESSO
    // ================================

    @Test
    public void deveBuscarClienteExistenteComSucesso() {
        String clienteId = criarClienteERetornarId(JsonPayloads.clienteParaBusca());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/clientes/{id}", clienteId)
                .then()
                .statusCode(200)
                .body("id", equalTo(clienteId))
                .body("nome", notNullValue())
                .body("sobrenome", notNullValue())
                .body("cpfCnpj", notNullValue())
                .body("email", notNullValue())
                .body("telefone", notNullValue())
                .body("endereco", notNullValue())
                .body("veiculos", notNullValue());
    }

    // ================================
    // TESTES GET /clientes/{id} - CENÁRIOS DE ERRO
    // ================================


    @Test
    public void naoDeveBuscarClienteInexistente() {
        String clienteIdInexistente = "550e8400-e29b-41d4-a716-446655440000";

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/clientes/{id}", clienteIdInexistente)
                .then()
                .statusCode(404);
    }

    @Test
    public void naoDeveBuscarClienteComIdInvalido() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/clientes/{id}", "id-invalido")
                .then()
                .statusCode(400);
    }

    // ================================
    // TESTES DELETE /clientes/{id} - CENÁRIOS DE SUCESSO
    // ================================

    @Test
    public void deveDeletarClienteExistenteComSucesso() {
        String clienteId = criarClienteERetornarId(JsonPayloads.clienteParaDeletar());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/clientes/{id}", clienteId)
                .then()
                .statusCode(204);

        // Verifica que não existe mais
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/clientes/{id}", clienteId)
                .then()
                .statusCode(404);
    }

    // ================================
    // TESTES DELETE /clientes/{id} - CENÁRIOS DE ERRO
    // ================================


    @Test
    public void naoDeveDeletarClienteInexistente() {
        String clienteIdInexistente = "550e8400-e29b-41d4-a716-446655440000";

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/clientes/{id}", clienteIdInexistente)
                .then()
                .statusCode(404);
    }

    @Test
    public void naoDeveDeletarClienteComIdInvalido() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/clientes/{id}", "id-invalido")
                .then()
                .statusCode(400);
    }

    // ================================
    // TESTES PATCH /clientes/{id} - CENÁRIOS DE SUCESSO
    // ================================

    @Test
    public void deveAtualizarClienteComSucesso() {
        String id = criarClienteERetornarId(JsonPayloads.clienteParaAtualizar());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloads.atualizacaoCompleta())
                .when()
                .patch("/clientes/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("email", containsString("email.atualizado"))
                .body("telefone", containsString("34988"));
    }


    // ================================
    // TESTES PATCH /clientes/{id} - CENÁRIOS DE ERRO
    // ================================


    @Test
    public void naoDeveAtualizarClienteInexistente() {
        String clienteIdInexistente = "550e8400-e29b-41d4-a716-446655440000";

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloads.atualizacaoCompleta())
                .when()
                .patch("/clientes/{id}", clienteIdInexistente)
                .then()
                .statusCode(404);
    }


    // ================================
    // TESTES POST /clientes/{id}/veiculos - CENÁRIOS DE SUCESSO
    // ================================

    @Test
    public void deveAdicionarVeiculoAoClienteComSucesso() {
        String clienteId = criarClienteERetornarId(JsonPayloads.clienteSemVeiculo());
        var veiculoModeloId = criarVeiculoModeloERetornarId();

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloads.veiculoValido(veiculoModeloId))
                .when()
                .post("/clientes/{id}/veiculos", clienteId)
                .then()
                .statusCode(200)
                .body("veiculoId", notNullValue())
                .body("veiculoId", matchesPattern("[a-f0-9-]{36}"));
    }

    // ================================
    // TESTES POST /clientes/{id}/veiculos - CENÁRIOS DE ERRO
    // ================================


    @Test
    public void naoDeveAdicionarVeiculoAClienteInexistente() {
        String clienteIdInexistente = "550e8400-e29b-41d4-a716-446655440000";

        var veiculoModeloId = 100L;
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloads.veiculoValido(veiculoModeloId))
                .when()
                .post("/clientes/{id}/veiculos", clienteIdInexistente)
                .then()
                .statusCode(404);
    }


}

