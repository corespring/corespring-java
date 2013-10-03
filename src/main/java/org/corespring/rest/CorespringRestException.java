package org.corespring.rest;

import org.corespring.resource.Error;

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

  public String getErrorMessage() {
    return errorMessage;
  }

  public String getMessage() {
    return errorCode == null ? errorMessage : errorCode + " - " + errorMessage;
  }

}
