package org.corespring.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.corespring.resource.question.Answer;
import org.corespring.resource.question.Participant;
import org.corespring.resource.question.Settings;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuizTest {

  @Test
  public void testSerialization() throws IOException {
    Metadata metadata = new Metadata("title", "course", "note");
    List<String> standards = new ArrayList<String>() { { add("test"); } };
    final Question question = new Question("503c2e91e4b00f3f0a9a7a6a", Settings.standard(), "title", standards);
    List<Question> questions = new ArrayList<Question>() { { add(question); } };
    final Answer answer = new Answer("itemId", "sessionId", 1, new Date(1380649346485L), true);
    final Participant participant = new Participant(new ArrayList<Answer>() { { add(answer); } }, "externalUid");
    List<Participant> participants = new ArrayList<Participant>() {{ add(participant); }};
    Quiz quiz = new Quiz("000000000000000000000002", "51114b307fc1eaa866444648", metadata, questions, participants);

    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"id\":\"000000000000000000000002\",\"orgId\":\"51114b307fc1eaa866444648\",\"metadata\":{\"title\":\"title\",\"course\":\"course\",\"note\":\"note\"},\"questions\":[{\"itemId\":\"503c2e91e4b00f3f0a9a7a6a\",\"settings\":{\"highlightUserResponse\":true,\"highlightCorrectResponse\":false,\"showFeedback\":true,\"allowEmptyResponses\":false,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":1},\"title\":\"title\",\"standards\":[\"test\"]}],\"participants\":[{\"answers\":[{\"itemId\":\"itemId\",\"sessionId\":\"sessionId\",\"score\":1,\"lastResponse\":1380649346485,\"complete\":true}],\"externalUid\":\"externalUid\"}]}",
        objectMapper.writeValueAsString(quiz)
    );

  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"id\":\"000000000000000000000002\",\"orgId\":\"51114b307fc1eaa866444648\",\"metadata\":{\"title\":\"title\",\"course\":\"course\",\"note\":\"note\"},\"questions\":[{\"itemId\":\"503c2e91e4b00f3f0a9a7a6a\",\"settings\":{\"highlightUserResponse\":true,\"highlightCorrectResponse\":false,\"showFeedback\":true,\"allowEmptyResponses\":false,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":1},\"title\":\"title\",\"standards\":[\"test\"]}],\"participants\":[{\"answers\":[{\"itemId\":\"itemId\",\"sessionId\":\"sessionId\",\"score\":1,\"lastResponse\":1380649346485,\"complete\":true}],\"externalUid\":\"externalUid\"}]}";
    ObjectMapper objectMapper = new ObjectMapper();
    Quiz deserialized = objectMapper.readValue(json, Quiz.class);

    assertEquals("000000000000000000000002", deserialized.getId());
    assertEquals("51114b307fc1eaa866444648", deserialized.getOrgId());
    assertEquals("title", deserialized.getMetadata().getTitle());
    assertEquals("course", deserialized.getMetadata().getCourse());
    assertEquals("note", deserialized.getMetadata().getNote());
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

}
