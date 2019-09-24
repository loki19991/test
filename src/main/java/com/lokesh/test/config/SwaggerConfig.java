package com.lokesh.test.config;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ClientCredentialsGrant;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
  @Bean
  public Docket defaultApiDocket() {

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .securitySchemes(
            Collections.singletonList(securityScheme()))
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build()
        .useDefaultResponseMessages(false);
  }

  protected ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Customer Profile API")
        .contact(new Contact("Lokesh Metla", "", "lokesh.metla@qantas.com.au"))
        .version("1.0")
        .build();
  }
  private AuthorizationScope[] scopes() {
    AuthorizationScope[] scopes = {
            new AuthorizationScope("read:profile", "for read operations"),
            new AuthorizationScope("write:profile ", "for write operations")
    };
    return scopes;
  }
  private SecurityScheme securityScheme() {
    GrantType grantType = new ClientCredentialsGrant("http://localhost:8080" + "/oauth/token");

    SecurityScheme oauth = new OAuthBuilder().name("profile_auth")
            .grantTypes(Arrays.asList(grantType))
            .scopes(Arrays.asList(scopes()))
            .build();
    return oauth;
  }
}
