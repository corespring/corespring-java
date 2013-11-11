package org.corespring.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ContentCollectionTest {

  @Test
  public void testSerialize() throws JsonProcessingException {
    ContentCollection collection = new ContentCollection("4ff2e4cae4b077b9e31689fd", "CoreSpring ELA (Public)", "Corespring Literacy Items", true);
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"id\":\"4ff2e4cae4b077b9e31689fd\",\"name\":\"CoreSpring ELA (Public)\",\"description\":\"Corespring Literacy Items\",\"isPublic\":true}",
        objectMapper.writeValueAsString(collection)
    );
  }

  @Test
  public void testDeserialize() throws IOException {
    String json = "{\"id\":\"4ff2e4cae4b077b9e31689fd\",\"name\":\"CoreSpring ELA (Public)\",\"description\":\"Corespring Literacy Items\",\"isPrivate\":false,\"isPublic\":true}";
    ObjectMapper objectMapper = new ObjectMapper();
    ContentCollection deserialized = objectMapper.readValue(json, ContentCollection.class);

    assertEquals("4ff2e4cae4b077b9e31689fd", deserialized.getId());
    assertEquals("CoreSpring ELA (Public)", deserialized.getName());
    assertEquals("Corespring Literacy Items", deserialized.getDescription());
    assertEquals(true, deserialized.isPublic());
  }

}
