package org.corespring.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.corespring.resource.question.Settings;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ItemSessionTest {

  @Test
  public void testSerialization() throws JsonProcessingException {
    ItemSession itemSession = new ItemSession("51114b307fc1eaa866444648", "503c2e91e4b00f3f0a9a7a6a", 0, new Date(1383656312295L), new Date(1383656512295L), Settings.standard());
    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"id\":\"51114b307fc1eaa866444648\",\"itemId\":\"503c2e91e4b00f3f0a9a7a6a\",\"attempts\":0,\"start\":1383656312295,\"finish\":1383656512295,\"settings\":{\"highlightUserResponse\":true,\"highlightCorrectResponse\":false,\"showFeedback\":true,\"allowEmptyResponses\":false,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":1}}",
        objectMapper.writeValueAsString(itemSession)
    );
  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"id\":\"51114b307fc1eaa866444648\",\"itemId\":\"503c2e91e4b00f3f0a9a7a6a\",\"attempts\":0,\"start\":1383656312295,\"finish\":1383656512295,\"settings\":{\"highlightUserResponse\":true,\"highlightCorrectResponse\":false,\"showFeedback\":true,\"allowEmptyResponses\":false,\"submitCompleteMessage\":\"Ok! Your response was submitted.\",\"submitIncompleteMessage\":\"You may revise your work before you submit your final response.\",\"submitIncorrectMessage\":\"You may revise your work before you submit your final response.\",\"maxNumberOfAttempts\":1}}";
    ObjectMapper objectMapper = new ObjectMapper();
    ItemSession deserialized = objectMapper.readValue(json, ItemSession.class);

    assertEquals("51114b307fc1eaa866444648", deserialized.getId());
    assertEquals("503c2e91e4b00f3f0a9a7a6a", deserialized.getItemId());
    assertEquals(new Integer(0), deserialized.getAttempts());
    assertEquals(new Date(1383656312295L), deserialized.getStart());
    assertEquals(new Date(1383656512295L), deserialized.getFinish());
    assertEquals(Settings.standard().getMaxNumberOfAttempts(), deserialized.getSettings().getMaxNumberOfAttempts());
    assertEquals(Settings.standard().getHighlightCorrectResponse(), deserialized.getSettings().getHighlightCorrectResponse());
    assertEquals(Settings.standard().getShowFeedback(), deserialized.getSettings().getShowFeedback());
    assertEquals(Settings.standard().getAllowEmptyResponses(), deserialized.getSettings().getAllowEmptyResponses());
    assertEquals(Settings.standard().getSubmitCompleteMessage(), deserialized.getSettings().getSubmitCompleteMessage());
    assertEquals(Settings.standard().getSubmitIncompleteMessage(), deserialized.getSettings().getSubmitIncompleteMessage());
    assertEquals(Settings.standard().getSubmitIncorrectMessage(), deserialized.getSettings().getSubmitIncorrectMessage());
  }

}
