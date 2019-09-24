package com.lokesh.test.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * Error Object
 */
@ApiModel(description = "Error Object")
@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("statusCode")
  private Integer statusCode = null;

  @JsonProperty("errorMessage")
  private String errorMessage = null;
}
