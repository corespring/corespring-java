package org.corespring.resource.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AnswerTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testSerialization() throws IOException {
    Answer answer = new Answer("itemId", "sessionId", 1, new Date(1380648184309L), true);
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"itemId\":\"itemId\",\"sessionId\":\"sessionId\",\"score\":1,\"lastResponse\":1380648184309,\"complete\":true}",
        objectMapper.writeValueAsString(answer)
    );
  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"itemId\":\"itemId\",\"sessionId\":\"sessionId\",\"score\":1,\"lastResponse\":1380648184309,\"complete\":true}";
    ObjectMapper objectMapper = new ObjectMapper();
    Answer deserialized = objectMapper.readValue(json, Answer.class);

    assertEquals("itemId", deserialized.getItemId());
    assertEquals("sessionId", deserialized.getSessionId());
    assertEquals(new Integer(1), deserialized.getScore());
    assertEquals(new Date(1380648184309L), deserialized.getLastResponse());
    assertEquals(true, deserialized.isComplete());
  }

  @Test
  public void testRequiresItemId() {
    exception.expect(IllegalStateException.class);
    new Answer.Builder().sessionId("sessionId").build();
  }

  @Test
  public void testRequiresSessionId() {
    exception.expect(IllegalStateException.class);
    new Answer.Builder().itemId("itemId").build();
  }

  @Test
  public void testValidWithItemIdAndSessionId() {
    Answer answer = new Answer.Builder().itemId("itemId").sessionId("sessionId").build();
    assertEquals("itemId", answer.getItemId());
    assertEquals("sessionId", answer.getSessionId());
  }

}
