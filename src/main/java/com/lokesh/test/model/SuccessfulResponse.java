package com.lokesh.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@ApiModel(description = "a success response")
@Validated
@Setter
@Getter
@NoArgsConstructor
public class SuccessfulResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("statusCode")
  private Integer statusCode = null;

  @JsonProperty("description")
  private String description = null;
}
