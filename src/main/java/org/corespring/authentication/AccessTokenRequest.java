package org.corespring.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a request to the CoreSpring API to generate an authentication token for signing subsequent requests to the
 * CoreSpring API.
 */
public class AccessTokenRequest {

  private final String clientId;
  private final String clientSecret;

  @JsonCreator
  public AccessTokenRequest(@JsonProperty("client_id") String clientId,
                            @JsonProperty("client_secret") String clientSecret) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  @JsonProperty("client_id")
  public String getClientId() {
    return clientId;
  }

  @JsonProperty("client_secret")
  public String getClientSecret() {
    return clientSecret;
  }

}
