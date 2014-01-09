package org.corespring.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StandardTest {

  @Test
  public void testSerialization() throws JsonProcessingException {
    String[] grades = new String[] { "K", "1", "2" };
    Standard standard = new Standard("527d2c81a81fbc1839792a1d", "category", "subCategory", "standard", "subject", Arrays.asList(grades), "dotNotation", "CC");
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"id\":\"527d2c81a81fbc1839792a1d\",\"category\":\"category\",\"subCategory\":\"subCategory\",\"standard\":\"standard\",\"subject\":\"subject\",\"grades\":[\"K\",\"1\",\"2\"],\"dotNotation\":\"dotNotation\",\"categoryAbbreviation\":\"CC\"}",
        objectMapper.writeValueAsString(standard)
    );
  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"id\":\"527d2c81a81fbc1839792a1d\",\"category\":\"category\",\"subCategory\":\"subCategory\",\"standard\":\"standard\",\"subject\":\"subject\",\"grades\":[\"K\",\"1\",\"2\"],\"dotNotation\":\"dotNotation\",\"categoryAbbreviation\":\"CC\"}";
    ObjectMapper objectMapper = new ObjectMapper();

    Standard deserialized = objectMapper.readValue(json, Standard.class);

    assertEquals("527d2c81a81fbc1839792a1d", deserialized.getId());
    assertEquals("category", deserialized.getCategory());
    assertEquals("subCategory", deserialized.getSubCategory());
    assertEquals("standard", deserialized.getStandard());
    assertEquals("subject", deserialized.getSubject());
    assertEquals("dotNotation", deserialized.getDotNotation());
    assertTrue(deserialized.getGrades().contains("K"));
    assertTrue(deserialized.getGrades().contains("1"));
    assertTrue(deserialized.getGrades().contains("2"));
  }

}
