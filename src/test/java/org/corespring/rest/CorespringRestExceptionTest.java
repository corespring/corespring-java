package org.corespring.rest;

import org.corespring.resource.Error;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CorespringRestExceptionTest {

  @Test
  public void testIsInvalidAccessTokenReturnsTrueForInvalidAccessTokens() {
    assertTrue(exceptionWithCode(CorespringRestException.INVALID_OR_EXPIRED_ACCESS_TOKEN).isInvalidAccessToken());
    assertTrue(exceptionWithCode(CorespringRestException.EXPIRED_ACCESS_TOKEN).isInvalidAccessToken());
  }

  @Test
  public void testIsInvalidAccessTokenReturnsFalseForUnrelatedErrors() {
    assertFalse(exceptionWithCode(404).isInvalidAccessToken());
  }

  private CorespringRestException exceptionWithCode(int code) {
    return new CorespringRestException(new Error(code, ""));
  }

}
