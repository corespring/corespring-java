package org.corespring.rest;

import org.corespring.resource.Error;

/**
 * This {@link Exception} is designed to catch known errors that are returned from the CoreSpring API. CoreSpring API
 * errors come in the following JSON format:
 *
 * <pre>
 *
 *   {
 *     "code": 102,
 *     "message": "The Access Token is invalid or has expired",
 *     "moreInfo": ""
 *   }
 *
 * </pre>
 *
 */
public class CorespringRestException extends Exception {

  static final int INVALID_OR_EXPIRED_ACCESS_TOKEN = 102;
  static final int EXPIRED_ACCESS_TOKEN = 108;

  static final int[] INVALID_ACCESS_TOKEN = new int[] {
      INVALID_OR_EXPIRED_ACCESS_TOKEN,
      EXPIRED_ACCESS_TOKEN
  };

  private final Integer errorCode;
  private final String errorMessage;

  public CorespringRestException(Error error) {
    this.errorCode = error.getCode();
    this.errorMessage = error.getMessage();
  }

  public CorespringRestException(String responseBody) {
    this.errorMessage = responseBody;
    this.errorCode = null;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public boolean hasErrorCode() {
    return errorCode != null;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public String getMessage() {
    return errorCode == null ? errorMessage : errorCode + " - " + errorMessage;
  }

  /**
   * Returns true if the exception was generated as a result of an invalid access token, false otherwise.
   */
  public boolean isInvalidAccessToken() {
    for (int invalidCode : INVALID_ACCESS_TOKEN) {
      if (getErrorCode() == invalidCode) {
        return true;
      }
    }
    return false;
  }

}
