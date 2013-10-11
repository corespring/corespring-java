package org.corespring.resource.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ExpirationDateTest extends EnumTest {

  @Test
  public void testExpirationDateSerialization() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Date date = new Date();

    assertEquals(quoted("*"), objectMapper.writeValueAsString(ExpirationDate.wildcard()));
    assertEquals(quoted(objectMapper.writeValueAsString(date)), objectMapper.writeValueAsString(new ExpirationDate(date)));
  }

  @Test
  public void testExpirationDateDeserialization() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Date date = new Date();

    assertEquals(ExpirationDate.wildcard(), objectMapper.readValue(quoted("*"), ExpirationDate.class));
    assertEquals(new ExpirationDate(date), objectMapper.readValue(quoted(Long.toString(date.getTime())),
        ExpirationDate.class));
  }

}
