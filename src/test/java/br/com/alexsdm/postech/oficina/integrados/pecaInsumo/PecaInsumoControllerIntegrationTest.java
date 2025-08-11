package br.com.alexsdm.postech.oficina.integrados.pecaInsumo;

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
public class PecaInsumoControllerIntegrationTest {

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
    public void deveListarTodasAsPecasComSucesso() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/pecas")
                .then()
                .statusCode(200)
                .body("$", isA(java.util.List.class));
    }

    // ================================
    // TESTES GET /pecas - CENÁRIOS DE ERRO
    // ================================

    @Test
    public void naoDeveListarPecasSemAutenticacao() {
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
    public void deveCadastrarPecaComSucesso() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.pecaValida())
                .when()
                .post("/pecas")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .header("Location", matchesPattern(".*/pecas/\\d+"));
    }

    // ================================
    // TESTES POST /pecas - CENÁRIOS DE ERRO
    // ================================

    @Test
    public void naoDeveCadastrarPecaSemAutenticacao() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.pecaValida())
                .when()
                .post("/pecas")
                .then()
                .statusCode(401);
    }

    @Test
    public void naoDeveCadastrarPecaComDadosInvalidos() {
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
    public void naoDeveCadastrarPecaComCamposVazios() {
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
    public void deveAtualizarPecaComSucesso() {
        String pecaId = criarPecaERetornarId(JsonPayloadsPecaInsumo.pecaParaAtualizar());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.atualizacaoCompleta())
                .when()
                .patch("/pecas/{id}", pecaId)
                .then()
                .statusCode(204);
    }

    // ================================
    // TESTES PATCH /pecas/{id} - CENÁRIOS DE ERRO
    // ================================

    @Test
    public void naoDeveAtualizarPecaSemAutenticacao() {
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
    public void naoDeveAtualizarPecaInexistente() {
        Long pecaIdInexistente = 99999L;

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.atualizacaoCompleta())
                .when()
                .patch("/pecas/{id}", pecaIdInexistente)
                .then()
                .statusCode(404);
    }

    @Test
    public void naoDeveAtualizarPecaComDadosInvalidos() {
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
    public void deveDeletarPecaExistenteComSucesso() {
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
    public void naoDeveDeletarPecaSemAutenticacao() {
        String pecaId = criarPecaERetornarId(JsonPayloadsPecaInsumo.pecaParaDeletar());

        RestAssured.given()
                .when()
                .delete("/pecas/{id}", pecaId)
                .then()
                .statusCode(401);
    }

    @Test
    public void naoDeveDeletarPecaInexistente() {
        Long pecaIdInexistente = 99999L;

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/pecas/{id}", pecaIdInexistente)
                .then()
                .statusCode(404);
    }

    // ================================
    // TESTE DE CENÁRIO COMPLEXO
    // ================================

    @Test
    public void devePermitirCicloCompletoDeOperacoes() {
        // 1. Cadastrar peça
        String pecaId = criarPecaERetornarId(JsonPayloadsPecaInsumo.pecaParaCicloCompleto());

        // 2. Listar todas (deve conter a peça criada)
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/pecas")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)));

        // 3. Atualizar peça
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsPecaInsumo.atualizacaoCompleta())
                .when()
                .patch("/pecas/{id}", pecaId)
                .then()
                .statusCode(204);

        // 4. Deletar peça
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/pecas/{id}", pecaId)
                .then()
                .statusCode(204);
    }
}