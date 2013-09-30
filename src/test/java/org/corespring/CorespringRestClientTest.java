package org.corespring;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class CorespringRestClientTest {

  @Test
  public void testValidAccessTokenDoesNotThrowException() {

    // Success with valid ObjectId
    try {
      new CorespringRestClient("52498773a9c98a782be5b739");
      assertTrue(true);
    } catch (IllegalArgumentException e) {
      assertTrue(false);
    }

  }

  @Test
  public void testInvalidAccessTokenThrowsException() {

    // Fails with invalid ObjectId
    try {
      new CorespringRestClient("abc");
      assertTrue(false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

  }

}
