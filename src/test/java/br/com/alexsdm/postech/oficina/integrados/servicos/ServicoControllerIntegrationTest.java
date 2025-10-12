package br.com.alexsdm.postech.oficina.integrados.servicos;

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
public class ServicoControllerIntegrationTest {

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
                .body(JsonPayloadsServico.login())
                .when()
                .post("/logins")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    private String criarServicoERetornarId(String jsonPayload) {
        String location = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .when()
                .post("/servicos")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .extract()
                .header("Location");

        return location.substring(location.lastIndexOf("/") + 1);
    }

    // GET /servicos - sucesso
    @Test
    public void deveListarServicosComSucesso() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/servicos")
                .then()
                .statusCode(200)
                .body("$", isA(java.util.List.class));
    }

    // GET /servicos - sem autenticação
    @Test
    public void naoDeveListarServicosSemAutenticacao() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("/servicos")
                .then()
                .statusCode(401);
    }

    // POST /servicos - sucesso
    @Test
    public void deveCadastrarServicoComSucesso() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsServico.servicoValido())
                .when()
                .post("/servicos")
                .then()
                .statusCode(201)
                .header("Location", matchesPattern(".*/servicos/[0-9a-f-]{36}"));
    }

    // POST /servicos - sem autenticação
    @Test
    public void naoDeveCadastrarServicoSemAutenticacao() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(JsonPayloadsServico.servicoValido())
                .when()
                .post("/servicos")
                .then()
                .statusCode(401);
    }

    // GET /servicos/{id} - sucesso
    @Test
    public void deveBuscarServicoPorIdComSucesso() {
        String id = criarServicoERetornarId(JsonPayloadsServico.servicoValido());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/servicos/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id));
    }

    // GET /servicos/{id} - não encontrado
    @Test
    public void naoDeveBuscarServicoInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/servicos/{id}", UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    // PATCH /servicos/{id} - sucesso
    @Test
    public void deveAtualizarServicoComSucesso() {
        String id = criarServicoERetornarId(JsonPayloadsServico.servicoValido());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsServico.atualizacaoValida())
                .when()
                .patch("/servicos/{id}", id)
                .then()
                .statusCode(200);
    }

    // DELETE /servicos/{id} - sucesso
    @Test
    public void deveDeletarServicoComSucesso() {
        String id = criarServicoERetornarId(JsonPayloadsServico.servicoValido());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/servicos/{id}", id)
                .then()
                .statusCode(204);
    }
}
