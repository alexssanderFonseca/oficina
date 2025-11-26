package br.com.alexsdm.postech.oficina.integrados.pecainsumo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
 class PecaInsumoControllerIntegrationTest {

    private String token;


    @LocalServerPort
    private int port;

    @BeforeEach
     void setup() {
        RestAssured.port = port;
        token = authenticateAndGetToken();
    }

    private String authenticateAndGetToken() {
        return RestAssured.given()
                .port(port)
                .contentType("application/json")
                .body(JsonPayloadsPecaInsumo.login())
                .when()
                .post("/logins")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    private String criarPecaERetornarId(String jsonPayload) {
        String location = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .when()
                .post("/pecas")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .extract()
                .header("Location");

        return location.substring(location.lastIndexOf("/") + 1);
    }

    // ================================
    // TESTES GET /pecas - CENÁRIOS DE SUCESSO
    // ================================

    @Test
     void deveListarTodasAsPecasComSucesso() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/pecas?pagina=0&quantidade=1")

                .then()
                .statusCode(200);
    }

    // ================================
    // TESTES GET /pecas - CENÁRIOS DE ERRO
    // ================================

    @Test
     void naoDeveListarPecasSemAutenticacao() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("/pecas")
                .then()
                .statusCode(401);
    }

    // ================================
    // TESTES POST /pecas - CENÁRIOS DE SUCESSO
    // ================================

    @Test
     void deveCadastrarPecaComSucesso() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.pecaValida())
                .when()
                .post("/pecas")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .header("Location", matchesPattern(".*/pecas/[0-9a-f-]{36}"));
    }

    // ================================
    // TESTES POST /pecas - CENÁRIOS DE ERRO
    // ================================

    @Test
     void naoDeveCadastrarPecaSemAutenticacao() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.pecaValida())
                .when()
                .post("/pecas")
                .then()
                .statusCode(401);
    }

    @Test
     void naoDeveCadastrarPecaComDadosInvalidos() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.pecaComDadosInvalidos())
                .when()
                .post("/pecas")
                .then()
                .statusCode(400);
    }

    @Test
     void naoDeveCadastrarPecaComCamposVazios() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.pecaComCamposVazios())
                .when()
                .post("/pecas")
                .then()
                .statusCode(400);
    }

    // ================================
    // TESTES PATCH /pecas/{id} - CENÁRIOS DE SUCESSO
    // ================================

    @Test
     void deveAtualizarPecaComSucesso() {
        String pecaId = criarPecaERetornarId(JsonPayloadsPecaInsumo.pecaParaAtualizar());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.atualizacaoCompleta())
                .when()
                .patch("/pecas/{id}", pecaId)
                .then()
                .statusCode(200);
    }

    // ================================
    // TESTES PATCH /pecas/{id} - CENÁRIOS DE ERRO
    // ================================

    @Test
     void naoDeveAtualizarPecaSemAutenticacao() {
        String pecaId = criarPecaERetornarId(JsonPayloadsPecaInsumo.pecaParaAtualizar());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.atualizacaoCompleta())
                .when()
                .patch("/pecas/{id}", pecaId)
                .then()
                .statusCode(401);
    }

    @Test
     void naoDeveAtualizarPecaInexistente() {

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.atualizacaoCompleta())
                .when()
                .patch("/pecas/{id}", UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
     void naoDeveAtualizarPecaComDadosInvalidos() {
        String pecaId = criarPecaERetornarId(JsonPayloadsPecaInsumo.pecaParaAtualizar());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.atualizacaoInvalida())
                .when()
                .patch("/pecas/{id}", pecaId)
                .then()
                .statusCode(400);
    }

    // ================================
    // TESTES DELETE /pecas/{id} - CENÁRIOS DE SUCESSO
    // ================================

    @Test
     void deveDeletarPecaExistenteComSucesso() {
        String pecaId = criarPecaERetornarId(JsonPayloadsPecaInsumo.pecaParaDeletar());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/pecas/{id}", pecaId)
                .then()
                .statusCode(204);
    }

    // ================================
    // TESTES DELETE /pecas/{id} - CENÁRIOS DE ERRO
    // ================================

    @Test
     void naoDeveDeletarPecaSemAutenticacao() {
        String pecaId = criarPecaERetornarId(JsonPayloadsPecaInsumo.pecaParaDeletar());

        RestAssured.given()
                .when()
                .delete("/pecas/{id}", pecaId)
                .then()
                .statusCode(401);
    }

    @Test
     void naoDeveDeletarPecaInexistente() {

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/pecas/{id}", UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    // ================================
    // TESTE DE CENÁRIO COMPLEXO
    // ================================

    @Test
     void devePermitirCicloCompletoDeOperacoes() {
        // 1. Cadastrar peça
        String pecaId = criarPecaERetornarId(JsonPayloadsPecaInsumo.pecaParaCicloCompleto());

        // 2. Listar todas (deve conter a peça criada)
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/pecas?pagina=0")
                .then()
                .statusCode(200)
                .body("data", hasSize(greaterThan(0)));

        // 3. Atualizar peça
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.atualizacaoCompleta())
                .when()
                .patch("/pecas/{id}", pecaId)
                .then()
                .statusCode(200);

        // 4. Deletar peça
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/pecas/{id}", pecaId)
                .then()
                .statusCode(204);
    }
}