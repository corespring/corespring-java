package org.corespring.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MetadataTest {

  @Test
  public void testMetadataSerialization() throws IOException {
    Metadata metadata = new Metadata("title", "course", "note");
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"title\":\"title\",\"course\":\"course\",\"note\":\"note\"}",
        objectMapper.writeValueAsString(metadata)
    );
  }

  @Test
  public void testMetadataDeserialization() throws IOException {
    String json = "{\"title\":\"title\",\"course\":\"course\",\"note\":\"note\"}";
    ObjectMapper objectMapper = new ObjectMapper();
    Metadata deserialized = objectMapper.readValue(json, Metadata.class);

    assertEquals("title", deserialized.getTitle());
    assertEquals("course", deserialized.getCourse());
    assertEquals("note", deserialized.getNote());
  }

}
