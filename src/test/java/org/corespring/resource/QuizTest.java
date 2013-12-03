package org.corespring.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.corespring.resource.question.Answer;
import org.corespring.resource.question.Participant;
import org.corespring.resource.question.Settings;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class QuizTest {

  private final Date yesterday = new Date(1384550263228L);
  private final Date today = new Date(1384550763228L);
  private final Date tomorrow = new Date(1384550863228L);

  /**
   * Dummy Quiz test data
   */
  private final Map<String, Object> metadata = new HashMap<String, Object>();
  private final List<String> standards = new ArrayList<String>() { { add("test"); } };
  private final Question question = new Question("503c2e91e4b00f3f0a9a7a6a", Settings.standard(), "title", standards);
  private final List<Question> questions = new ArrayList<Question>() { { add(question); } };
  private final Answer answer = new Answer("itemId", "sessionId", 1, new Date(1380649346485L), true);
  private final Participant participant = new Participant(new ArrayList<Answer>() { { add(answer); } }, "externalUid", null);
  private final List<Participant> participants = new ArrayList<Participant>() {{ add(participant); }};

  {
    metadata.put("title", "title");
    metadata.put("description", "description");
    metadata.put("instructions", "instructions");
    metadata.put("classroom", "1034");
  }

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testSerialization() throws IOException {
    Quiz quiz = new Quiz("000000000000000000000002", "51114b307fc1eaa866444648", today, tomorrow, metadata, questions, participants);
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"id\":\"000000000000000000000002\",\"orgId\":\"51114b307fc1eaa866444648\",\"start\":1384550763228,\"end\":1384550863228,\"metadata\":{\"classroom\":\"1034\",\"title\":\"title\",\"instructions\":\"instructions\",\"description\":\"description\"},\"questions\":[{\"itemId\":\"503c2e91e4b00f3f0a9a7a6a\",\"settings\":{\"highlightUserResponse\":true,\"highlightCorrectResponse\":false,\"showFeedback\":true,\"allowEmptyResponses\":false,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":1},\"title\":\"title\",\"standards\":[\"test\"]}],\"participants\":[{\"answers\":[{\"itemId\":\"itemId\",\"sessionId\":\"sessionId\",\"score\":1,\"lastResponse\":1380649346485,\"complete\":true}],\"externalUid\":\"externalUid\"}]}",
        objectMapper.writeValueAsString(quiz)
    );

  }

  @Test
  public void testNullIdSerialization() throws IOException {
    Quiz quiz = new Quiz(null, "51114b307fc1eaa866444648", today, tomorrow, metadata, questions, participants);
    ObjectMapper objectMapper = new ObjectMapper();

    String json = objectMapper.writeValueAsString(quiz);

    assertFalse(json.contains("null"));
  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"id\":\"000000000000000000000002\",\"orgId\":\"51114b307fc1eaa866444648\",\"start\":1384550763228,\"end\":1384550863228,\"metadata\":{\"classroom\":\"1034\",\"title\":\"title\",\"instructions\":\"instructions\",\"description\":\"description\"},\"questions\":[{\"itemId\":\"503c2e91e4b00f3f0a9a7a6a\",\"settings\":{\"highlightUserResponse\":true,\"highlightCorrectResponse\":false,\"showFeedback\":true,\"allowEmptyResponses\":false,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":1},\"title\":\"title\",\"standards\":[\"test\"]}],\"participants\":[{\"answers\":[{\"itemId\":\"itemId\",\"sessionId\":\"sessionId\",\"score\":1,\"lastResponse\":1380649346485,\"complete\":true}],\"externalUid\":\"externalUid\"}]}";
    ObjectMapper objectMapper = new ObjectMapper();
    Quiz deserialized = objectMapper.readValue(json, Quiz.class);

    assertEquals("000000000000000000000002", deserialized.getId());
    assertEquals("51114b307fc1eaa866444648", deserialized.getOrgId());
    assertEquals("title", deserialized.getTitle());
    assertEquals("description", deserialized.getDescription());
    assertEquals("instructions", deserialized.getInstructions());
    assertEquals(today, deserialized.getStart());
    assertEquals(tomorrow, deserialized.getEnd());
    assertEquals("1034", deserialized.getMetadataValue("classroom"));
    assertEquals("503c2e91e4b00f3f0a9a7a6a", deserialized.getQuestions().iterator().next().getItemId());
    assertEquals(Settings.standard().getMaxNumberOfAttempts(), deserialized.getQuestions().iterator().next().getSettings().getMaxNumberOfAttempts());
    assertEquals(Settings.standard().getHighlightCorrectResponse(), deserialized.getQuestions().iterator().next().getSettings().getHighlightCorrectResponse());
    assertEquals(Settings.standard().getShowFeedback(), deserialized.getQuestions().iterator().next().getSettings().getShowFeedback());
    assertEquals(Settings.standard().getAllowEmptyResponses(), deserialized.getQuestions().iterator().next().getSettings().getAllowEmptyResponses());
    assertEquals(Settings.standard().getSubmitCompleteMessage(), deserialized.getQuestions().iterator().next().getSettings().getSubmitCompleteMessage());
    assertEquals(Settings.standard().getSubmitIncompleteMessage(), deserialized.getQuestions().iterator().next().getSettings().getSubmitIncompleteMessage());
    assertEquals(Settings.standard().getSubmitIncorrectMessage(), deserialized.getQuestions().iterator().next().getSettings().getSubmitIncorrectMessage());
    assertEquals("test", deserialized.getQuestions().iterator().next().getStandards().iterator().next());
    assertEquals("itemId", deserialized.getParticipants().iterator().next().getAnswers().iterator().next().getItemId());
    assertEquals("sessionId", deserialized.getParticipants().iterator().next().getAnswers().iterator().next().getSessionId());
    assertEquals("externalUid", deserialized.getParticipants().iterator().next().getExternalUid());
  }

  @Test
  public void testStartDateAfterEndDateThrowsException() {
    exception.expect(IllegalArgumentException.class);
    new Quiz.Builder().start(tomorrow).end(today);
  }

  @Test
  public void testEndDateBeforeStartDateThrowsException() {
    exception.expect(IllegalArgumentException.class);
    new Quiz.Builder().end(today).start(tomorrow);
  }

  @Test
  public void testEndDateWithoutStartDateThrowsException() {
    exception.expect(IllegalStateException.class);
    new Quiz.Builder().end(tomorrow).build();
  }

  @Test
  public void testStartDateWithoutEndDateThrowsException() {
    exception.expect(IllegalStateException.class);
    new Quiz.Builder().start(today).build();
  }

  @Test
  public void testIsActive() {
    assertTrue(new Quiz.Builder().build().isActive(today));
    assertTrue(new Quiz.Builder().start(yesterday).end(tomorrow).build().isActive(today));
    assertFalse(new Quiz.Builder().start(today).end(tomorrow).build().isActive(yesterday));
    assertFalse(new Quiz.Builder().start(yesterday).end(today).build().isActive(tomorrow));
  }

  @Test
  public void testGetTitle() {
    assertNull(new Quiz.Builder().build().getTitle());
  }

  @Test
  public void testGetDescription() {
    assertNull(new Quiz.Builder().build().getDescription());
  }

  @Test
  public void testGetInstructions() {
    assertNull(new Quiz.Builder().build().getInstructions());
  }

  @Test
  public void testGetMetadataValue() {
    assertNull(new Quiz.Builder().build().getMetadataValue("random value"));
  }

}