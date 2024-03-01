package org.acme;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExtratoRestTest{

    @Test
    @TestTransaction
    public void testExtrato(){

        given().contentType("application/json")
                .body("""
                        {"valor": 1000,
                         "tipo": "c",
                         "descricao": "descricao"}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(200)
                .body("limite", is(100000))
                .body("saldo", is(1000));

        given().contentType("application/json")
                .body("""
                        {"valor": 5000,
                         "tipo": "c",
                         "descricao": "descricao"}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(200)
                .body("limite", is(100000))
                .body("saldo", is(6000));

        given().contentType("application/json")
                .body("""
                        {"valor": 5000,
                         "tipo": "d",
                         "descricao": "descricao"}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(200)
                .body("limite", is(100000))
                .body("saldo", is(1000));

        given().contentType("application/json")
                .when()
                .get("/clientes/1/extrato")
                .then()
                .statusCode(200)
                .body("saldo.total", is(1000))
                .body("saldo.limite", is(100000))
                .body("ultimas_transacoes[0].valor", is(5000))
                .body("ultimas_transacoes[0].tipo", is("d"))
                .body("ultimas_transacoes[0].descricao", is("descricao"))
                .extract().path("ultimas_transacoes[0].realizada_em");

    }

}
