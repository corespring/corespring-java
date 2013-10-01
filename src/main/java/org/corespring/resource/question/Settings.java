package org.corespring.resource.question;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect(fieldVisibility= Visibility.ANY)
public class Settings {

  static final String DEFAULT_COMPLETE_MESSAGE = "Ok! Your response was submitted.";
  static final String DEFAULT_INCOMPLETE_MESSAGE =
      "You may revise your work before you submit your final response.";
  static final String DEFAULT_INCORRECT_MESSAGE = "You may revise your work before you submit your final response.";

  private final Integer maxNumberOfAttempts;
  private final Boolean highlightUserResponse;
  private final Boolean highlightCorrectResponse;
  private final Boolean showFeedback;
  private final Boolean allowEmptyResponses;
  private final String submitCompleteMessage;
  private final String submitIncompleteMessage;
  private final String submitIncorrectMessage;

  @JsonCreator
  public Settings(@JsonProperty("maxNoOfAttempts") Integer maxNumberOfAttempts,
                  @JsonProperty("highlightUserResponse") Boolean highlightUserResponse,
                  @JsonProperty("highlightCorrectResponse") Boolean highlightCorrectResponse,
                  @JsonProperty("showFeedback") Boolean showFeedback,
                  @JsonProperty("allowEmptyResponses") Boolean allowEmptyResponses,
                  @JsonProperty("submitCompleteMessage") String submitCompleteMessage,
                  @JsonProperty("submitIncompleteMessage") String submitIncompleteMessage,
                  @JsonProperty("submitIncorrectMessage") String submitIncorrectMessage) {
    this.maxNumberOfAttempts = maxNumberOfAttempts;
    this.highlightUserResponse = highlightUserResponse;
    this.highlightCorrectResponse = highlightCorrectResponse;
    this.showFeedback = showFeedback;
    this.allowEmptyResponses = allowEmptyResponses;
    this.submitCompleteMessage = submitCompleteMessage;
    this.submitIncompleteMessage = submitIncompleteMessage;
    this.submitIncorrectMessage = submitIncorrectMessage;
  }

  public static Settings standard() {
    return
        new Settings(1, true, true, true, false, DEFAULT_COMPLETE_MESSAGE, DEFAULT_INCOMPLETE_MESSAGE,
            DEFAULT_INCORRECT_MESSAGE);
  }

  public Integer getMaxNumberOfAttempts() {
    return maxNumberOfAttempts;
  }

  public Boolean getHighlightUserResponse() {
    return highlightUserResponse;
  }

  public Boolean getHighlightCorrectResponse() {
    return highlightCorrectResponse;
  }

  public Boolean getShowFeedback() {
    return showFeedback;
  }

  public Boolean getAllowEmptyResponses() {
    return allowEmptyResponses;
  }

  public String getSubmitCompleteMessage() {
    return submitCompleteMessage;
  }

  public String getSubmitIncompleteMessage() {
    return submitIncompleteMessage;
  }

  public String getSubmitIncorrectMessage() {
    return submitIncorrectMessage;
  }
}