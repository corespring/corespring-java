package org.corespring.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenResponse {

  private final String accessToken;

  @JsonCreator
  public AccessTokenResponse(@JsonProperty("access_token") String accessToken) {
    this.accessToken = accessToken;
  }

  public String toString() {
    return accessToken;
  }

}
