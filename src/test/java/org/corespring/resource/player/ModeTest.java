package org.corespring.resource.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ModeTest extends EnumTest {

  @Test
  public void testModeSerialization() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(quoted("*"), objectMapper.writeValueAsString(Mode.ALL));
    assertEquals(quoted("administer"), objectMapper.writeValueAsString(Mode.ADMINISTER));
    assertEquals(quoted("aggregate"), objectMapper.writeValueAsString(Mode.AGGREGATE));
    assertEquals(quoted("preview"), objectMapper.writeValueAsString(Mode.PREVIEW));
    assertEquals(quoted("render"), objectMapper.writeValueAsString(Mode.RENDER));
  }

  @Test
  public void testModeDeserialization() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(Mode.ALL, objectMapper.readValue(quoted("*"), Mode.class));
    assertEquals(Mode.ADMINISTER, objectMapper.readValue(quoted("administer"), Mode.class));
    assertEquals(Mode.AGGREGATE, objectMapper.readValue(quoted("aggregate"), Mode.class));
    assertEquals(Mode.PREVIEW, objectMapper.readValue(quoted("preview"), Mode.class));
    assertEquals(Mode.RENDER, objectMapper.readValue(quoted("render"), Mode.class));
  }

}
