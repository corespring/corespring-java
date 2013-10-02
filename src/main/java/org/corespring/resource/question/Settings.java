package org.corespring.resource.question;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link Settings} contains basic information about how an item should be rendered and handled by the CoreSpring
 * platform. The following is a typical JSON representation:
 *
 * <pre>
 *   "settings" : {
 *     "maxNoOfAttempts" : 0,
 *     "highlightUserResponse" : true,
 *     "highlightCorrectResponse" : true,
 *     "showFeedback" : false,
 *     "allowEmptyResponses" : false,
 *     "submitCompleteMessage" : "Ok! Your response was submitted.",
 *     "submitIncorrectMessage" : "You may revise your work before you submit your final response."
 *   }
 * </pre>
 *
 */
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
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

  private Settings(Builder builder) {
    this.maxNumberOfAttempts = builder.maxNumberOfAttempts;
    this.highlightUserResponse = builder.highlightUserResponse;
    this.highlightCorrectResponse = builder.highlightCorrectResponse;
    this.showFeedback = builder.showFeedback;
    this.allowEmptyResponses = builder.allowEmptyResponses;
    this.submitCompleteMessage = builder.submitCompleteMessage;
    this.submitIncompleteMessage = builder.submitIncompleteMessage;
    this.submitIncorrectMessage = builder.submitIncorrectMessage;
  }

  public static class Builder {

    private Integer maxNumberOfAttempts;
    private Boolean highlightUserResponse;
    private Boolean highlightCorrectResponse;
    private Boolean showFeedback;
    private Boolean allowEmptyResponses;
    private String submitCompleteMessage;
    private String submitIncompleteMessage;
    private String submitIncorrectMessage;

    public Builder() {
    }

    public Builder maxNumberOfAttempts(Integer maxNumberOfAttempts) {
      this.maxNumberOfAttempts = maxNumberOfAttempts;
      return this;
    }

    public Builder highlightUserResponse(Boolean highlightUserResponse) {
      this.highlightUserResponse = highlightUserResponse;
      return this;
    }

    public Builder highlightCorrectResponse(Boolean highlightCorrectResponse) {
      this.highlightCorrectResponse = highlightCorrectResponse;
      return this;
    }

    public Builder showFeedback(Boolean showFeedback) {
      this.showFeedback = showFeedback;
      return this;
    }

    public Builder allowEmptyResponses(Boolean allowEmptyResponses) {
      this.allowEmptyResponses = allowEmptyResponses;
      return this;
    }

    public Builder submitCompleteMessage(String submitCompleteMessage) {
      this.submitCompleteMessage = submitCompleteMessage;
      return this;
    }

    public Builder submitIncompleteMessage(String submitIncompleteMessage) {
      this.submitIncompleteMessage = submitIncompleteMessage;
      return this;
    }

    public Builder submitIncorrectMessage(String submitIncorrectMessage) {
      this.submitIncorrectMessage = submitIncorrectMessage;
      return this;
    }

    public Settings build() {
      return new Settings(this);
    }

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