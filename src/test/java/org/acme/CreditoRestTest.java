package org.acme;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CreditoRestTest{

    @Test
    @TestTransaction
    public void testCredito(){
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
    }

}