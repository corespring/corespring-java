package org.corespring.resource.question;

import java.util.Map;

public class Settings {

  private static final String MAX_NUMBER_OF_ATTEMPTS_KEY = "maxNoOfAttempts";
  private static final String HIGHLIGHT_USER_RESPONSE_KEY = "highlightUserResponse";
  private static final String SHOW_FEEDBACK_KEY = "showFeedback";
  private static final String ALLOW_EMPTY_RESPONSES_KEY = "allowEmptyResponses";
  private static final String SUBMIT_COMPLETE_MESSAGE_KEY = "submitCompleteMessage";
  private static final String SUBMIT_INCOMPLETE_MESSAGE_KEY = "submitIncompleteMessage";

  private final Integer maxNumberOfAttempts;
  private final Boolean highlightUserResponse;
  private final Boolean showFeedback;
  private final Boolean allowEmptyResponses;
  private final String submitCompleteMessage;
  private final String submitIncompleteMessage;

  public Settings(Integer maxNumberOfAttempts, Boolean highlightUserResponse, Boolean showFeedback,
                  Boolean allowEmptyResponses, String submitCompleteMessage, String submitIncompleteMessage) {
    this.maxNumberOfAttempts = maxNumberOfAttempts;
    this.highlightUserResponse = highlightUserResponse;
    this.showFeedback = showFeedback;
    this.allowEmptyResponses = allowEmptyResponses;
    this.submitCompleteMessage = submitCompleteMessage;
    this.submitIncompleteMessage = submitIncompleteMessage;
  }

  public static Settings fromObjectMap(Map<String, Object> objectMap) {
    Integer maxNoOfAttempts = (Integer) objectMap.get(MAX_NUMBER_OF_ATTEMPTS_KEY);
    Boolean highlightUserResponse = (Boolean) objectMap.get(HIGHLIGHT_USER_RESPONSE_KEY);
    Boolean showFeedback = (Boolean) objectMap.get(SHOW_FEEDBACK_KEY);
    Boolean allowEmptyResponses = (Boolean) objectMap.get(ALLOW_EMPTY_RESPONSES_KEY);
    String submitCompleteMessage = (String) objectMap.get(SUBMIT_COMPLETE_MESSAGE_KEY);
    String submitIncompletedMessage = (String) objectMap.get(SUBMIT_INCOMPLETE_MESSAGE_KEY);

    return new Settings(maxNoOfAttempts, highlightUserResponse, showFeedback, allowEmptyResponses,
        submitCompleteMessage, submitIncompletedMessage);
  }

}