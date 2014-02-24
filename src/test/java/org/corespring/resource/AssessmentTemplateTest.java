package org.corespring.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.corespring.resource.question.Settings;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class AssessmentTemplateTest {

  private final Map<String, Object> metadata = new HashMap<String, Object>();
  private final List<String> standards = new ArrayList<String>() { { add("test"); } };
  private final Question question = new Question("503c2e91e4b00f3f0a9a7a6a", Settings.standard(), "title", standards);
  private final List<Question> questions = new ArrayList<Question>() { { add(question); } };

  {
    metadata.put("title", "title");
    metadata.put("description", "description");
    metadata.put("instructions", "instructions");
    metadata.put("classroom", "1034");
  }

  @Test
  public void testSerialization() throws IOException {
    AssessmentTemplate assessmentTemplate = new AssessmentTemplate("5307c2925f22905a72479280", "51114b307fc1eaa866444648", "51114b127fc1eaa866444647", metadata, questions);
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
      "{\"id\":\"5307c2925f22905a72479280\",\"orgId\":\"51114b307fc1eaa866444648\",\"collectionId\":\"51114b127fc1eaa866444647\",\"metadata\":{\"classroom\":\"1034\",\"title\":\"title\",\"instructions\":\"instructions\",\"description\":\"description\"},\"questions\":[{\"itemId\":\"503c2e91e4b00f3f0a9a7a6a\",\"settings\":{\"highlightUserResponse\":true,\"highlightCorrectResponse\":false,\"showFeedback\":true,\"allowEmptyResponses\":false,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":1},\"title\":\"title\",\"standards\":[\"test\"]}]}",
      objectMapper.writeValueAsString(assessmentTemplate)
    );
  }

  @Test
  public void testNullIdSerialization() throws IOException {
    AssessmentTemplate assessmentTemplate = new AssessmentTemplate(null, "51114b307fc1eaa866444648", "51114b127fc1eaa866444647", metadata, questions);
    ObjectMapper objectMapper = new ObjectMapper();

    String json = objectMapper.writeValueAsString(assessmentTemplate);

    assertFalse(json.contains("null"));
  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"id\":\"5307c2925f22905a72479280\",\"orgId\":\"51114b307fc1eaa866444648\",\"collectionId\":\"51114b127fc1eaa866444647\",\"metadata\":{\"classroom\":\"1034\",\"title\":\"title\",\"instructions\":\"instructions\",\"description\":\"description\"},\"questions\":[{\"itemId\":\"503c2e91e4b00f3f0a9a7a6a\",\"settings\":{\"highlightUserResponse\":true,\"highlightCorrectResponse\":false,\"showFeedback\":true,\"allowEmptyResponses\":false,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":1},\"title\":\"title\",\"standards\":[\"test\"]}]}";
    ObjectMapper objectMapper = new ObjectMapper();
    AssessmentTemplate deserialized = objectMapper.readValue(json, AssessmentTemplate.class);

    assertEquals("5307c2925f22905a72479280", deserialized.getId());
    assertEquals("51114b307fc1eaa866444648", deserialized.getOrgId());
    assertEquals("51114b127fc1eaa866444647", deserialized.getCollectionId());
    assertEquals("1034", deserialized.getMetadataValue("classroom"));
    assertEquals("503c2e91e4b00f3f0a9a7a6a", deserialized.getQuestions().iterator().next().getItemId());
    assertEquals(Settings.standard().getMaxNumberOfAttempts(), deserialized.getQuestions().iterator().next().getSettings().getMaxNumberOfAttempts());
    assertEquals(Settings.standard().getHighlightCorrectResponse(), deserialized.getQuestions().iterator().next().getSettings().getHighlightCorrectResponse());
    assertEquals(Settings.standard().getShowFeedback(), deserialized.getQuestions().iterator().next().getSettings().getShowFeedback());
    assertEquals(Settings.standard().getAllowEmptyResponses(), deserialized.getQuestions().iterator().next().getSettings().getAllowEmptyResponses());
    assertEquals(Settings.standard().getSubmitCompleteMessage(), deserialized.getQuestions().iterator().next().getSettings().getSubmitCompleteMessage());
    assertEquals(Settings.standard().getSubmitIncompleteMessage(), deserialized.getQuestions().iterator().next().getSettings().getSubmitIncompleteMessage());
    assertEquals(Settings.standard().getSubmitIncorrectMessage(), deserialized.getQuestions().iterator().next().getSettings().getSubmitIncorrectMessage());
  }

}
