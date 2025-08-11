package br.com.alexsdm.postech.oficina.integrados.veiculomodelo;

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
public class VeiculoModeloControllerIntegrationTest {

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
                .body(JsonPayloadsVeiculoModelo.login())
                .when()
                .post("/logins")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    private String criarModeloERetornarId(String jsonPayload) {
        String location = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .when()
                .post("/veiculos-modelos")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .extract()
                .header("Location");

        return location.substring(location.lastIndexOf("/") + 1);
    }

    // POST /veiculos-modelos - sucesso
    @Test
    public void deveCadastrarModeloComSucesso() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsVeiculoModelo.modeloValido())
                .when()
                .post("/veiculos-modelos")
                .then()
                .statusCode(201)
                .header("Location", matchesPattern(".*/veiculos-modelos/\\d+"));
    }

    // POST /veiculos-modelos - sem autenticação
    @Test
    public void naoDeveCadastrarModeloSemAutenticacao() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(JsonPayloadsVeiculoModelo.modeloValido())
                .when()
                .post("/veiculos-modelos")
                .then()
                .statusCode(401);
    }

    // POST /veiculos-modelos - inválido
    @Test
    public void naoDeveCadastrarModeloComDadosInvalidos() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsVeiculoModelo.modeloInvalido())
                .when()
                .post("/veiculos-modelos")
                .then()
                .statusCode(400);
    }

    // GET /veiculos-modelos/{id} - sucesso
    @Test
    public void deveBuscarModeloPorIdComSucesso() {
        String id = criarModeloERetornarId(JsonPayloadsVeiculoModelo.modeloValido());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/veiculos-modelos/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(Integer.parseInt(id)));
    }

    // GET /veiculos-modelos/{id} - não encontrado
    @Test
    public void naoDeveBuscarModeloInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/veiculos-modelos/{id}", 99999)
                .then()
                .statusCode(404);
    }

    // PATCH /veiculos-modelos/{id} - sucesso
    @Test
    public void deveAtualizarModeloComSucesso() {
        String id = criarModeloERetornarId(JsonPayloadsVeiculoModelo.modeloValido());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsVeiculoModelo.atualizacaoValida())
                .when()
                .patch("/veiculos-modelos/{id}", id)
                .then()
                .statusCode(200);
    }

    // PATCH /veiculos-modelos/{id} - inválido
    @Test
    public void naoDeveAtualizarModeloComDadosInvalidos() {
        String id = criarModeloERetornarId(JsonPayloadsVeiculoModelo.modeloValido());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(JsonPayloadsVeiculoModelo.atualizacaoInvalida())
                .when()
                .patch("/veiculos-modelos/{id}", id)
                .then()
                .statusCode(400);
    }

    // DELETE /veiculos-modelos/{id} - sucesso
    @Test
    public void deveDeletarModeloComSucesso() {
        String id = criarModeloERetornarId(JsonPayloadsVeiculoModelo.modeloValido2());

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/veiculos-modelos/{id}", id)
                .then()
                .statusCode(204);
    }

    // DELETE /veiculos-modelos/{id} - não encontrado
    @Test
    public void naoDeveDeletarModeloInexistente() {
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/veiculos-modelos/{id}", 99999)
                .then()
                .statusCode(404);
    }
}
