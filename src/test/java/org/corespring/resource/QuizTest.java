package org.corespring.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.corespring.CorespringClient;
import org.corespring.resource.question.Answer;
import org.corespring.resource.question.Participant;
import org.corespring.resource.question.Settings;
import org.corespring.rest.CorespringRestClient;
import org.corespring.rest.CorespringRestException;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class QuizTest {

  /**
   * Dummy Quiz test data
   */
  private final Map<String, String> metadata = new HashMap<String, String>();
  private final List<String> standards = new ArrayList<String>() { { add("test"); } };
  private final Question question = new Question("503c2e91e4b00f3f0a9a7a6a", Settings.standard(), "title", standards);
  private final List<Question> questions = new ArrayList<Question>() { { add(question); } };
  private final Answer answer = new Answer("itemId", "sessionId", 1, new Date(1380649346485L), true);
  private final Participant participant = new Participant(new ArrayList<Answer>() { { add(answer); } }, "externalUid");
  private final List<Participant> participants = new ArrayList<Participant>() {{ add(participant); }};

  {
    metadata.put("title", "title");
    metadata.put("description", "description");
    metadata.put("instructions", "instructions");
    metadata.put("classroom", "1034");
  }

  @Test
  public void testSerialization() throws IOException {
    Quiz quiz = new Quiz("000000000000000000000002", "51114b307fc1eaa866444648", metadata, questions, participants);
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"id\":\"000000000000000000000002\",\"orgId\":\"51114b307fc1eaa866444648\",\"metadata\":{\"classroom\":\"1034\",\"title\":\"title\",\"instructions\":\"instructions\",\"description\":\"description\"},\"questions\":[{\"itemId\":\"503c2e91e4b00f3f0a9a7a6a\",\"settings\":{\"highlightUserResponse\":true,\"highlightCorrectResponse\":false,\"showFeedback\":true,\"allowEmptyResponses\":false,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":1},\"title\":\"title\",\"standards\":[\"test\"]}],\"participants\":[{\"answers\":[{\"itemId\":\"itemId\",\"sessionId\":\"sessionId\",\"score\":1,\"lastResponse\":1380649346485,\"complete\":true}],\"externalUid\":\"externalUid\"}]}",
        objectMapper.writeValueAsString(quiz)
    );

  }


  @Test
  public void testNullIdSerialization() throws IOException {
    Quiz quiz = new Quiz(null, "51114b307fc1eaa866444648", metadata, questions, participants);
    ObjectMapper objectMapper = new ObjectMapper();

    String json = objectMapper.writeValueAsString(quiz);

    assertFalse(json.contains("null"));
  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"id\":\"000000000000000000000002\",\"orgId\":\"51114b307fc1eaa866444648\",\"metadata\":{\"classroom\":\"1034\",\"title\":\"title\",\"instructions\":\"instructions\",\"description\":\"description\"},\"questions\":[{\"itemId\":\"503c2e91e4b00f3f0a9a7a6a\",\"settings\":{\"highlightUserResponse\":true,\"highlightCorrectResponse\":false,\"showFeedback\":true,\"allowEmptyResponses\":false,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":1},\"title\":\"title\",\"standards\":[\"test\"]}],\"participants\":[{\"answers\":[{\"itemId\":\"itemId\",\"sessionId\":\"sessionId\",\"score\":1,\"lastResponse\":1380649346485,\"complete\":true}],\"externalUid\":\"externalUid\"}]}";
    ObjectMapper objectMapper = new ObjectMapper();
    Quiz deserialized = objectMapper.readValue(json, Quiz.class);

    assertEquals("000000000000000000000002", deserialized.getId());
    assertEquals("51114b307fc1eaa866444648", deserialized.getOrgId());
    assertEquals("title", deserialized.getTitle());
    assertEquals("description", deserialized.getDescription());
    assertEquals("instructions", deserialized.getInstructions());
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
  public void testUniqueParticipantsByExternalUid() {
    String sharedExternalUid = "rjelcjdi4";
    Participant participant = new Participant.Builder().externalUid(sharedExternalUid).build();
    Participant anotherParticipant = new Participant.Builder().externalUid(sharedExternalUid).build();

    Quiz quiz = new Quiz.Builder().participant(participant).participant(anotherParticipant).build();

    assertEquals(1, quiz.getParticipants().size());
    assertEquals(anotherParticipant, quiz.getParticipants().iterator().next());
  }

}
