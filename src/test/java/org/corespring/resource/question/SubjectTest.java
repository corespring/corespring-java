package org.corespring.resource.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SubjectTest {

  @Test
  public void testSerailization() throws JsonProcessingException {
    Subject subject = new Subject("527d2daaa81fbc1839792a1e", "category", "subject");

    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"id\":\"527d2daaa81fbc1839792a1e\",\"category\":\"category\",\"subject\":\"subject\"}",
        objectMapper.writeValueAsString(subject)
    );
  }

  @Test
  public void testDeserailization() throws IOException {
    String json = "{\"id\":\"527d2daaa81fbc1839792a1e\",\"category\":\"category\",\"subject\":\"subject\"}";
    ObjectMapper objectMapper = new ObjectMapper();
    Subject deserialized = objectMapper.readValue(json, Subject.class);

    assertEquals("527d2daaa81fbc1839792a1e", deserialized.getId());
    assertEquals("category", deserialized.getCategory());
    assertEquals("subject", deserialized.getSubject());

  }

}