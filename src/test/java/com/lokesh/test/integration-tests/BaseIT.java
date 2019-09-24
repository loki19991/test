package com.lokesh.test.it;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.MatcherConfig;
import com.lokesh.test.Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    classes = {Application.class}, properties = {"spring.config.name=application"})
@RunWith(SpringRunner.class)
@ActiveProfiles("local, h2")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public abstract class BaseIT {

  private static Integer port = 8080;

  @Autowired
  public ObjectMapper objectMapper;

  protected void defaultSetUpBeforeEach() {
    setUpRestAssured();
    setUpDefaultJacksonObjectMapper();
  }

  private void setUpRestAssured() {
    RestAssured.port = port;
    RestAssured.config =
        RestAssured.config()
            .matcherConfig(new MatcherConfig(MatcherConfig.ErrorDescriptionType.HAMCREST));
  }

  private void setUpDefaultJacksonObjectMapper() {
    objectMapper = new ObjectMapper()
        .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setSerializationInclusion(Include.NON_NULL);
  }
}
