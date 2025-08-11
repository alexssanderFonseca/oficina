package br.com.alexsdm.postech.oficina.integrados.orcamento;

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
public class OrcamentoControllerIntegrationTest {

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
                .body(JsonPayloadsOrcamento.login())
                .when()
                .post("/logins")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    private String criarOrcamentoERetornarId(String jsonPayload) {
        String location = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .when()
                .post("/orcamentos")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .extract()
                .header("Location");

        return location.substring(location.lastIndexOf("/") + 1);
    }

    // POST /orcamentos - sucesso
    @Test
    public void deveCriarOrcamentoComSucesso() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrcamento.criarOrcamentoValido())
                .when()
                .post("/orcamentos")
                .then()
                .statusCode(201)
                .header("Location", matchesPattern(".*/orcamentos/\\d+"));
    }

    // POST /orcamentos - sem autenticação
    @Test
    public void naoDeveCriarOrcamentoSemAutenticacao() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrcamento.criarOrcamentoValido())
                .when()
                .post("/orcamentos")
                .then()
                .statusCode(401);
    }

    // POST /orcamentos - dados inválidos
    @Test
    public void naoDeveCriarOrcamentoComDadosInvalidos() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrcamento.criarOrcamentoInvalido())
                .when()
                .post("/orcamentos")
                .then()
                .statusCode(400);
    }

    // POST /orcamentos - cliente não encontrado
    @Test
    public void naoDeveCriarOrcamentoComClienteInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsOrcamento.criarOrcamentoClienteInexistente())
                .when()
                .post("/orcamentos")
                .then()
                .statusCode(400);
    }

    // POST /orcamentos/{id}/aceitos - sucesso
    @Test
    public void deveAceitarOrcamentoComSucesso() {
        String id = criarOrcamentoERetornarId(JsonPayloadsOrcamento.criarOrcamentoValido());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/orcamentos/{id}/aceitos", id)
                .then()
                .statusCode(204);
    }

    // POST /orcamentos/{id}/aceitos - orçamento não encontrado
    @Test
    public void naoDeveAceitarOrcamentoInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/orcamentos/{id}/aceitos", 99999)
                .then()
                .statusCode(404);
    }

    // POST /orcamentos/{id}/aceitos - sem autenticação
    @Test
    public void naoDeveAceitarOrcamentoSemAutenticacao() {
        String id = criarOrcamentoERetornarId(JsonPayloadsOrcamento.criarOrcamentoValido());

        RestAssured.given()
                .when()
                .post("/orcamentos/{id}/aceitos", id)
                .then()
                .statusCode(401);
    }

    // POST /orcamentos/{id}/recusados - sucesso
    @Test
    public void deveRecusarOrcamentoComSucesso() {
        String id = criarOrcamentoERetornarId(JsonPayloadsOrcamento.criarOrcamentoValido2());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/orcamentos/{id}/recusados", id)
                .then()
                .statusCode(204);
    }

    // POST /orcamentos/{id}/recusados - orçamento não encontrado
    @Test
    public void naoDeveRecusarOrcamentoInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/orcamentos/{id}/recusados", 99999)
                .then()
                .statusCode(404);
    }

    // POST /orcamentos/{id}/recusados - sem autenticação
    @Test
    public void naoDeveRecusarOrcamentoSemAutenticacao() {
        String id = criarOrcamentoERetornarId(JsonPayloadsOrcamento.criarOrcamentoValido2());

        RestAssured.given()
                .when()
                .post("/orcamentos/{id}/recusados", id)
                .then()
                .statusCode(401);
    }

    // GET /orcamentos/{id} - sucesso
    @Test
    public void deveBuscarOrcamentoPorIdComSucesso() {
        String id = criarOrcamentoERetornarId(JsonPayloadsOrcamento.criarOrcamentoValido());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/orcamentos/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(Integer.parseInt(id)))
                .body("valorTotal", notNullValue())
                .body("valorTotalEmPecas", notNullValue())
                .body("valorTotalEmServicos", notNullValue())
                .body("status", notNullValue())
                .body("cliente", notNullValue())
                .body("veiculoResponse", notNullValue())
                .body("pecas", notNullValue())
                .body("servicos", notNullValue());
    }

    // GET /orcamentos/{id} - não encontrado
    @Test
    public void naoDeveBuscarOrcamentoInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/orcamentos/{id}", 99999)
                .then()
                .statusCode(404);
    }

    // GET /orcamentos/{id} - sem autenticação
    @Test
    public void naoDeveBuscarOrcamentoSemAutenticacao() {
        String id = criarOrcamentoERetornarId(JsonPayloadsOrcamento.criarOrcamentoValido());

        RestAssured.given()
                .when()
                .get("/orcamentos/{id}", id)
                .then()
                .statusCode(401);
    }

    // GET /orcamentos/{id}/envios - sucesso (PDF)
    @Test
    public void deveEnviarOrcamentoComSucesso() {
        String id = criarOrcamentoERetornarId(JsonPayloadsOrcamento.criarOrcamentoValido());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/orcamentos/{id}/envios", id)
                .then()
                .statusCode(200)
                .contentType("application/pdf")
                .header("Content-Disposition", containsString("attachment"))
                .header("Content-Disposition", containsString("orcamento.pdf"));
    }

    // GET /orcamentos/{id}/envios - orçamento não encontrado
    @Test
    public void naoDeveEnviarOrcamentoInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/orcamentos/{id}/envios", 99999)
                .then()
                .statusCode(404);
    }

    // GET /orcamentos/{id}/envios - sem autenticação
    @Test
    public void naoDeveEnviarOrcamentoSemAutenticacao() {
        String id = criarOrcamentoERetornarId(JsonPayloadsOrcamento.criarOrcamentoValido());

        RestAssured.given()
                .when()
                .get("/orcamentos/{id}/envios", id)
                .then()
                .statusCode(401);
    }
}