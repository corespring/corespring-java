package org.corespring.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ErrorTest {

  @Test
  public void testErrorSerialization() throws IOException {
    Error error = new Error(108, "Your access token expired on 2013-03-19T20:39:55.135-04:00. Please request a new one");
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"code\":108,\"message\":\"Your access token expired on 2013-03-19T20:39:55.135-04:00. Please request a new one\"}",
        objectMapper.writeValueAsString(error)
    );
  }

  @Test
  public void testErrorDeserialization() throws IOException {
    String json = "{\"code\":108,\"message\":\"Your access token expired on 2013-03-19T20:39:55.135-04:00. Please request a new one\",\"moreInfo\":\"\"}";
    ObjectMapper objectMapper = new ObjectMapper();
    Error error = objectMapper.readValue(json, Error.class);

    assertEquals(108, error.getCode());
    assertEquals("Your access token expired on 2013-03-19T20:39:55.135-04:00. Please request a new one",
        error.getMessage());
  }

}
