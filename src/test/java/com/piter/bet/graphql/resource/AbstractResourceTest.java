package com.piter.bet.graphql.resource;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public abstract class AbstractResourceTest {

  protected Response send(String request) {
    return given()
        .contentType(ContentType.JSON)
        .body(request)
        .when()
        .post("/graphql")
        .then()
        .assertThat()
        .statusCode(200)
        .and()
        .extract()
        .response();
  }
}
