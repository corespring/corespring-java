package org.corespring.resource.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ParticipantTest {

  @Test
  public void testSerialization() throws IOException {
    final Answer answer = new Answer("itemId", "sessionId", 1, new Date(1380648451591L), true);
    Participant participant = new Participant(new ArrayList<Answer>() { { add(answer); } }, "externalUid");
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"answers\":[{\"itemId\":\"itemId\",\"sessionId\":\"sessionId\",\"score\":1,\"lastResponse\":1380648451591,\"complete\":true}],\"externalUid\":\"externalUid\"}",
        objectMapper.writeValueAsString(participant)
    );
  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"answers\":[{\"itemId\":\"itemId\",\"sessionId\":\"sessionId\",\"score\":1,\"lastResponse\":1380648451591,\"complete\":true}],\"externalUid\":\"externalUid\"}";
    ObjectMapper objectMapper = new ObjectMapper();
    Participant deserialized = objectMapper.readValue(json, Participant.class);

    assertEquals("itemId", deserialized.getAnswers().iterator().next().getItemId());
    assertEquals("sessionId", deserialized.getAnswers().iterator().next().getSessionId());
    assertEquals(new Integer(1), deserialized.getAnswers().iterator().next().getScore());
    assertEquals(new Date(1380648451591L), deserialized.getAnswers().iterator().next().getLastResponse());
    assertEquals(true, deserialized.getAnswers().iterator().next().isComplete());
    assertEquals("externalUid", deserialized.getExternalUid());
  }

}
