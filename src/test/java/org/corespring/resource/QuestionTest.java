package org.corespring.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.corespring.resource.question.Settings;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuestionTest {

  @Test
  public void testSerialization() throws IOException {
    List<String> standards = new ArrayList<String>() { { add("test"); } };
    Question question = new Question("503c2e91e4b00f3f0a9a7a6a", Settings.standard(), "title", standards);
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"itemId\":\"503c2e91e4b00f3f0a9a7a6a\",\"settings\":{\"highlightUserResponse\":true,\"highlightCorrectResponse\":false,\"showFeedback\":true,\"allowEmptyResponses\":false,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":1},\"title\":\"title\",\"standards\":[\"test\"]}",
        objectMapper.writeValueAsString(question)
    );
  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"itemId\":\"503c2e91e4b00f3f0a9a7a6a\",\"settings\":{\"highlightUserResponse\":true,\"highlightCorrectResponse\":false,\"showFeedback\":true,\"allowEmptyResponses\":false,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":1},\"title\":\"title\",\"standards\":[\"test\"]}";
    ObjectMapper objectMapper = new ObjectMapper();
    Question deserailized = objectMapper.readValue(json, Question.class);

    assertEquals("503c2e91e4b00f3f0a9a7a6a", deserailized.getItemId());
    assertEquals("title", deserailized.getTitle());
    assertEquals(Settings.standard().getMaxNumberOfAttempts(), deserailized.getSettings().getMaxNumberOfAttempts());
    assertEquals(Settings.standard().getHighlightCorrectResponse(), deserailized.getSettings().getHighlightCorrectResponse());
    assertEquals(Settings.standard().getShowFeedback(), deserailized.getSettings().getShowFeedback());
    assertEquals(Settings.standard().getAllowEmptyResponses(), deserailized.getSettings().getAllowEmptyResponses());
    assertEquals(Settings.standard().getSubmitCompleteMessage(), deserailized.getSettings().getSubmitCompleteMessage());
    assertEquals(Settings.standard().getSubmitIncompleteMessage(), deserailized.getSettings().getSubmitIncompleteMessage());
    assertEquals(Settings.standard().getSubmitIncorrectMessage(), deserailized.getSettings().getSubmitIncorrectMessage());
    assertEquals("test", deserailized.getStandards().iterator().next());
  }

}
