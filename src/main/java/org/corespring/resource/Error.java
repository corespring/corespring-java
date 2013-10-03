package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {

  private final int code;
  private final String message;

  @JsonCreator
  public Error(@JsonProperty("code") int code,
               @JsonProperty("message") String message) {
    this.code = code;
    this.message = message;
  }

  public class Builder {

    private int code;
    private String message;

    public Builder() {
    }

    public Builder(Error error) {
      this.code = error.code;
      this.message = error.message;
    }

    public Builder code(int code) {
      this.code = code;
      return this;
    }

    public Builder message(String message) {
      this.message = message;
      return this;
    }

  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}
