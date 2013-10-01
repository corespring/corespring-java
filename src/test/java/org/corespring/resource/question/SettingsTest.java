package org.corespring.resource.question;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SettingsTest {

  @Test
  public void testSerialization() throws IOException {
    int maxNumberOfAttempts = 5;
    boolean highlightUserResponse = true;
    boolean highlightCorrectResponse = true;
    boolean showFeedback = true;
    boolean allowEmptyResponses = true;

    Settings settings = new Settings(maxNumberOfAttempts, highlightUserResponse, highlightCorrectResponse, showFeedback, allowEmptyResponses,
        Settings.DEFAULT_COMPLETE_MESSAGE, Settings.DEFAULT_INCOMPLETE_MESSAGE, Settings.DEFAULT_INCORRECT_MESSAGE);
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"highlightUserResponse\":true,\"highlightCorrectResponse\":true,\"showFeedback\":true,\"allowEmptyResponses\":true,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":5}",
        objectMapper.writeValueAsString(settings)
    );
  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"highlightUserResponse\":true,\"highlightCorrectResponse\":true,\"showFeedback\":true,\"allowEmptyResponses\":true,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":5}";
    ObjectMapper objectMapper = new ObjectMapper();
    Settings deserialized = objectMapper.readValue(json, Settings.class);

    assertEquals(new Integer(5), deserialized.getMaxNumberOfAttempts());
    assertEquals(true, deserialized.getHighlightUserResponse());
    assertEquals(true, deserialized.getHighlightCorrectResponse());
    assertEquals(true, deserialized.getShowFeedback());
    assertEquals(true, deserialized.getAllowEmptyResponses());
    assertEquals(Settings.DEFAULT_COMPLETE_MESSAGE, deserialized.getSubmitCompleteMessage());
    assertEquals(Settings.DEFAULT_INCOMPLETE_MESSAGE, deserialized.getSubmitIncompleteMessage());
    assertEquals(Settings.DEFAULT_INCORRECT_MESSAGE, deserialized.getSubmitIncorrectMessage());
  }

}
