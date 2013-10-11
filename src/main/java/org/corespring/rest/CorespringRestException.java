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

  public static final int EXPIRED_ACCESS_TOKEN = 108;

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

}
