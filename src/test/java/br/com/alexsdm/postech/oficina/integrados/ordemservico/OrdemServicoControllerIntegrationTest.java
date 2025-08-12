package br.com.alexsdm.postech.oficina.integrados.ordemservico;

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
public class OrdemServicoControllerIntegrationTest {

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
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.login())
                .when()
                .post("/logins")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    private Long criarOrdemServicoERetornarId(String jsonPayload) {
        String location = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .when()
                .post("/ordens-servicos")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .extract()
                .header("Location");

        return Long.parseLong(location.substring(location.lastIndexOf("/") + 1));
    }

    // POST /ordens-servicos - sucesso
    @Test
    public void deveCriarOrdemServicoComSucesso() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.criarOrdemServicoValida())
                .when()
                .post("/ordens-servicos")
                .then()
                .statusCode(201)
                .header("Location", matchesPattern(".*/ordens-servicos/\\d+"));
    }

    // POST /ordens-servicos - sem autenticação
    @Test
    public void naoDeveCriarOrdemServicoSemAutenticacao() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.criarOrdemServicoValida())
                .when()
                .post("/ordens-servicos")
                .then()
                .statusCode(401);
    }

    // POST /ordens-servicos - dados inválidos
    @Test
    public void naoDeveCriarOrdemServicoComDadosInvalidos() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.criarOrdemServicoInvalida())
                .when()
                .post("/ordens-servicos")
                .then()
                .statusCode(400);
    }

    // POST /ordens-servicos/{id}/diagnosticos - sucesso
    @Test
    public void deveIniciarDiagnosticoComSucesso() {
        var id = criarOrdemServicoERetornarId(JsonPayloadsOrdemServico.criarOrdemServicoValida());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/ordens-servicos/{id}/diagnosticos", id)
                .then()
                .statusCode(204);
    }

    // POST /ordens-servicos/{id}/diagnosticos - ordem não encontrada
    @Test
    public void naoDeveIniciarDiagnosticoOrdemInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/ordens-servicos/{id}/diagnosticos", 99999)
                .then()
                .statusCode(404);
    }

    // POST /ordens-servicos/{id}/diagnosticos/finalizacoes - sucesso
    @Test
    public void deveFinalizarDiagnosticoComSucesso() {
        var id = criarOrdemServicoERetornarId(JsonPayloadsOrdemServico.criarOrdemServicoValida());

        // Iniciar diagnóstico primeiro
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/ordens-servicos/{id}/diagnosticos", id)
                .then()
                .statusCode(204);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.finalizarDiagnosticoValido())
                .when()
                .post("/ordens-servicos/{id}/diagnosticos/finalizacoes", id)
                .then()
                .statusCode(204);
    }

    // POST /ordens-servicos/{id}/diagnosticos/finalizacoes - dados inválidos
    @Test
    public void naoDeveFinalizarDiagnosticoComDadosInvalidos() {
        var id = criarOrdemServicoERetornarId(JsonPayloadsOrdemServico.criarOrdemServicoValida());

        // Iniciar diagnóstico primeiro
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/ordens-servicos/{id}/diagnosticos", id)
                .then()
                .statusCode(204);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.finalizarDiagnosticoInvalido())
                .when()
                .post("/ordens-servicos/{id}/diagnosticos/finalizacoes", id)
                .then()
                .statusCode(400);
    }

    // POST /ordens-servicos/{id}/execucoes - sucesso
    @Test
    public void deveExecutarOrdemServicoComSucesso() {
        var id = criarOrdemServicoERetornarId(JsonPayloadsOrdemServico.criarOrdemServicoValida());

        // Iniciar e finalizar diagnóstico primeiro
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/ordens-servicos/{id}/diagnosticos", id)
                .then()
                .statusCode(204);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.finalizarDiagnosticoValido())
                .when()
                .post("/ordens-servicos/{id}/diagnosticos/finalizacoes", id)
                .then()
                .statusCode(204);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.executarOrdemServicoValido())
                .when()
                .post("/ordens-servicos/{id}/execucoes", id)
                .then()
                .statusCode(204);
    }

    // POST /ordens-servicos/{id}/execucoes - ordem não encontrada
    @Test
    public void naoDeveExecutarOrdemServicoInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.executarOrdemServicoValido())
                .when()
                .post("/ordens-servicos/{id}/execucoes", 99999)
                .then()
                .statusCode(404);
    }

    // POST /ordens-servicos/{id}/finalizacoes - sucesso
    @Test
    public void deveFinalizarOrdemServicoComSucesso() {
        var id = criarOrdemServicoERetornarId(JsonPayloadsOrdemServico.criarOrdemServicoValida());

        // Executar fluxo completo até execução
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/ordens-servicos/{id}/diagnosticos", id)
                .then()
                .statusCode(204);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.finalizarDiagnosticoValido())
                .when()
                .post("/ordens-servicos/{id}/diagnosticos/finalizacoes", id)
                .then()
                .statusCode(204);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.executarOrdemServicoValido())
                .when()
                .post("/ordens-servicos/{id}/execucoes", id)
                .then()
                .statusCode(204);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/ordens-servicos/{id}/finalizacoes", id)
                .then()
                .statusCode(204);
    }

    // POST /ordens-servicos/{id}/finalizacoes - ordem não encontrada
    @Test
    public void naoDeveFinalizarOrdemServicoInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/ordens-servicos/{id}/finalizacoes", 99999)
                .then()
                .statusCode(404);
    }

    // POST /ordens-servicos/{id}/entregas - sucesso
    @Test
    public void deveEntregarOrdemServicoComSucesso() {
        var id = criarOrdemServicoERetornarId(JsonPayloadsOrdemServico.criarOrdemServicoValida2());

        // Executar fluxo completo até finalização
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/ordens-servicos/{id}/diagnosticos", id)
                .then()
                .statusCode(204);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.finalizarDiagnosticoValido())
                .when()
                .post("/ordens-servicos/{id}/diagnosticos/finalizacoes", id)
                .then()
                .statusCode(204);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrdemServico.executarOrdemServicoValido())
                .when()
                .post("/ordens-servicos/{id}/execucoes", id)
                .then()
                .statusCode(204);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/ordens-servicos/{id}/finalizacoes", id)
                .then()
                .statusCode(204);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/ordens-servicos/{id}/entregas", id)
                .then()
                .statusCode(204);
    }

    // POST /ordens-servicos/{id}/entregas - ordem não encontrada
    @Test
    public void naoDeveEntregarOrdemServicoInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/ordens-servicos/{id}/entregas", 99999)
                .then()
                .statusCode(404);
    }

    // GET /ordens-servicos/{id} - sucesso
    @Test
    public void deveBuscarOrdemServicoPorIdComSucesso() {
        var id = criarOrdemServicoERetornarId(JsonPayloadsOrdemServico.criarOrdemServicoValida());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/ordens-servicos/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()))
                .body("dadosCliente", notNullValue())
                .body("dadosVeiculo", notNullValue())
                .body("status", notNullValue());
    }

    // GET /ordens-servicos/{id} - não encontrada
    @Test
    public void naoDeveBuscarOrdemServicoInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/ordens-servicos/{id}", 99999)
                .then()
                .statusCode(404);
    }

    // GET /ordens-servicos/{id} - sem autenticação
    @Test
    public void naoDeveBuscarOrdemServicoSemAutenticacao() {
        var id = criarOrdemServicoERetornarId(JsonPayloadsOrdemServico.criarOrdemServicoValida());

        RestAssured.given()
                .when()
                .get("/ordens-servicos/{id}", id)
                .then()
                .statusCode(401);
    }
}