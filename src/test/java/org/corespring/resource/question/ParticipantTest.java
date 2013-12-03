package org.corespring.resource.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ParticipantTest {

  private final Date today = new Date(1384550763228L);

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testSerialization() throws IOException {
    final Answer answer = new Answer("itemId", "sessionId", 1, new Date(1380648451591L), true);
    Participant participant = new Participant(new ArrayList<Answer>() { { add(answer); } }, "externalUid", today);
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"answers\":[{\"itemId\":\"itemId\",\"sessionId\":\"sessionId\",\"score\":1,\"lastResponse\":1380648451591,\"complete\":true}],\"externalUid\":\"externalUid\",\"lastModified\":1384550763228}",
        objectMapper.writeValueAsString(participant)
    );
  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"answers\":[{\"itemId\":\"itemId\",\"sessionId\":\"sessionId\",\"score\":1,\"lastResponse\":1380648451591,\"complete\":true}],\"externalUid\":\"externalUid\",\"lastModified\":1384550763228}";
    ObjectMapper objectMapper = new ObjectMapper();
    Participant deserialized = objectMapper.readValue(json, Participant.class);

    assertEquals("itemId", deserialized.getAnswers().iterator().next().getItemId());
    assertEquals("sessionId", deserialized.getAnswers().iterator().next().getSessionId());
    assertEquals(new Integer(1), deserialized.getAnswers().iterator().next().getScore());
    assertEquals(new Date(1380648451591L), deserialized.getAnswers().iterator().next().getLastResponse());
    assertEquals(true, deserialized.getAnswers().iterator().next().isComplete());
    assertEquals("externalUid", deserialized.getExternalUid());
    assertEquals(today, deserialized.getLastModified());
  }

  @Test
  public void testUniqueAnswersByItemId() {
    String sharedItemId = "527a3bbe8808335f66e168a5";
    Answer answer = new Answer.Builder().itemId(sharedItemId).sessionId("527a3bca8808335f66e168a6").build();
    Answer anotherAnswer = new Answer.Builder().itemId(sharedItemId).sessionId("527a3bca8808335f66e168a6").build();

    Participant participant =
        new Participant.Builder().externalUid("l23kjdf").answer(answer).answer(anotherAnswer).build();

    assertEquals(1, participant.getAnswers().size());
    assertEquals(anotherAnswer, participant.getAnswers().iterator().next());
  }

  @Test
  public void testGetAnswer() {
    String itemId = "527a3bbe8808335f66e168a5";
    String missingItemId = "527a455724f5ff49ee19d13b";

    Answer answer = new Answer.Builder().itemId(itemId).sessionId("527a3bca8808335f66e168a6").build();
    Participant participant = new Participant.Builder().externalUid("l23kjdf").answer(answer).build();

    assertEquals(answer, participant.getAnswer(itemId));
    assertNull(participant.getAnswer(missingItemId));
  }

  @Test
  public void testRequiresExternalUid() {
    exception.expect(IllegalStateException.class);
    new Participant.Builder().build();
  }

  @Test
  public void testValidWithExternalUid() {
    String externalUid = "l23kjdf";
    Participant participant = new Participant.Builder().externalUid(externalUid).build();

    assertEquals(externalUid, participant.getExternalUid());
  }

}
