package org.corespring.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class StandardTest {

  @Test
  public void testSerialization() throws JsonProcessingException {
    Standard standard = new Standard("527d2c81a81fbc1839792a1d", "category", "subCategory", "standard", "subject", "dotNotation");
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"id\":\"527d2c81a81fbc1839792a1d\",\"category\":\"category\",\"subCategory\":\"subCategory\",\"standard\":\"standard\",\"subject\":\"subject\",\"dotNotation\":\"dotNotation\"}",
        objectMapper.writeValueAsString(standard)
    );
  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"id\":\"527d2c81a81fbc1839792a1d\",\"category\":\"category\",\"subCategory\":\"subCategory\",\"standard\":\"standard\",\"subject\":\"subject\",\"dotNotation\":\"dotNotation\"}";
    ObjectMapper objectMapper = new ObjectMapper();

    Standard deserialized = objectMapper.readValue(json, Standard.class);

    assertEquals("527d2c81a81fbc1839792a1d", deserialized.getId());
    assertEquals("category", deserialized.getCategory());
    assertEquals("subCategory", deserialized.getSubCategory());
    assertEquals("standard", deserialized.getStandard());
    assertEquals("subject", deserialized.getSubject());
    assertEquals("dotNotation", deserialized.getDotNotation());
  }

}
