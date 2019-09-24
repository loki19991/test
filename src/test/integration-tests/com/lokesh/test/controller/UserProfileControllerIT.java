package com.lokesh.test.controller;

import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.http.ContentType;
import com.lokesh.test.BaseIT;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import static com.jayway.restassured.RestAssured.given;
import static com.lokesh.test.util.FileUtil.loadJsonFile;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.JsonAssert.when;
import static net.javacrumbs.jsonunit.core.Option.TREATING_NULL_AS_ABSENT;

public class UserProfileControllerIT extends BaseIT {

  @Value("${authServer-sample-client.clientId}")
  private String clientId;

  @Value("${authServer-sample-client.clientSecret}")
  private String clientSecret;

  private static String accessToken = null;

  @Before
  public void setup() throws UnsupportedEncodingException {
   defaultSetUpBeforeEach();
   getAccessToken();
  }

  @Test
  public void testCrudOperations() throws Exception {
    testCreateUser();
    testGetUserProfile();
    testUpdateUserProfile();
    testDeleteUser();
    testCreateUserForBadRequest();
  }

  public void testCreateUser() throws Exception {
    given()
      .contentType(ContentType.JSON)
      .body( loadJsonFile("create-user-profile.json"))
      .header("Authorization", "Bearer " + accessToken)
      .when()
      .post("/api/v1/users")
      .then()
      .statusCode(HttpStatus.CREATED.value());
  }


  public void testGetUserProfile() throws Exception {
    String response = given()
      .contentType(ContentType.JSON)
      .header("Authorization", "Bearer " + accessToken)
      .when()
      .get("/api/v1/users/1/profile")
      .then()
      .statusCode(HttpStatus.OK.value())
      .extract()
      .asString();
    assertJsonEquals(loadJsonFile("get-user-profile.json"), response, when(TREATING_NULL_AS_ABSENT));
  }


  public void testUpdateUserProfile() throws Exception {
    given()
      .contentType(ContentType.JSON)
      .body( loadJsonFile("update-user-profile.json"))
      .header("Authorization", "Bearer " + accessToken)
      .when()
      .put("/api/v1/users/1/profile")
      .then()
      .statusCode(HttpStatus.NO_CONTENT.value());
  }


  public void testDeleteUser() throws Exception {
    given()
      .contentType(ContentType.JSON)
      .header("Authorization", "Bearer " + accessToken)
      .when()
      .delete("/api/v1/users/1")
      .then()
      .statusCode(HttpStatus.OK.value());

    given()
      .contentType(ContentType.JSON)
      .header("Authorization", "Bearer " + accessToken)
      .when()
      .get("/api/v1/users/1/profile")
      .then()
      .statusCode(HttpStatus.NOT_FOUND.value());
  }

  public void testCreateUserForBadRequest() {
    given()
            .contentType(ContentType.JSON)
            .body( loadJsonFile("bad-user-object.json"))
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .post("/api/v1/users")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
  }


  private void getAccessToken() throws UnsupportedEncodingException {

    String basicAuthHeader =
      Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes("UTF-8"));

    String responsJson =given()
      .contentType(ContentType.JSON)
      .header("Authorization", "Basic " + basicAuthHeader)
      .when()
      .post("/oauth/token?grant_type=client_credentials")
      .then()
      .statusCode(HttpStatus.OK.value())
      .and()
      .extract().response().asString();

    accessToken = JsonPath.read(responsJson, "$.access_token");
  }

}
