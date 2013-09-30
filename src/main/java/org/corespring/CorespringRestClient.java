package org.corespring;

import org.bson.types.ObjectId;

public class CorespringRestClient {

  private final String accessToken;

  public CorespringRestClient(String accessToken) {
    validateAccessToken(accessToken);
    this.accessToken = accessToken;
  }

  private void validateAccessToken(String accessToken) {
    if (!ObjectId.isValid(accessToken)) {
      throw new IllegalArgumentException(
          new StringBuilder("Access token ").append(accessToken).append(" is not valid").toString());
    }
  }

}