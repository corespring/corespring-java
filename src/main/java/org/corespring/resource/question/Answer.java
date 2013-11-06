package org.corespring.resource.question;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.corespring.resource.CorespringResource;
import org.corespring.resource.Quiz;
import org.corespring.rest.CorespringRestClient;

import java.util.Date;

/**
 * An {@link Answer} corresponds to a student's response to an item. At the minimum, an {@link Answer} must contain an
 * itemId value and a sessionId value.
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class Answer extends CorespringResource {

  private final String itemId;
  private final String sessionId;
  private final Integer score;
  private final Date lastResponse;
  private final Boolean complete;

  @JsonCreator
  public Answer(@JsonProperty("itemId") String itemId,
                @JsonProperty("sessionId") String sessionId,
                @JsonProperty("score") Integer score,
                @JsonProperty("lastResponse") Date lastResponse,
                @JsonProperty("isComplete") Boolean complete) {
    this.itemId = itemId;
    this.sessionId = sessionId;
    this.score = score;
    this.lastResponse = lastResponse;
    this.complete = complete;
  }

  private Answer(Builder builder) {
    this.itemId = builder.itemId;
    this.sessionId = builder.sessionId;
    this.score = builder.score;
    this.lastResponse = builder.lastResponse;
    this.complete = builder.complete;
  }

  public static class Builder {

    private String itemId;
    private String sessionId;
    private Integer score;
    private Date lastResponse;
    private Boolean complete;

    public Builder() {
    }

    public Builder itemId(String itemId) {
      this.itemId = itemId;
      return this;
    }

    public Builder sessionId(String sessionId) {
      this.sessionId = sessionId;
      return this;
    }

    public Builder score(Integer score) {
      this.score = score;
      return this;
    }

    public Builder lastResponse(Date lastResponse) {
      this.lastResponse = lastResponse;
      return this;
    }

    public Builder complete(Boolean complete) {
      this.complete = complete;
      return this;
    }

    public Answer build() {
      validate();
      return new Answer(this);
    }

    /**
     * Validates that an {@link Answer} can be built. If it cannot, an {@link IllegalStateException} is thrown.
     *
     * @throws IllegalStateException
     */
    private void validate() throws IllegalStateException {
      if (this.itemId == null) { throw new IllegalStateException("Answer must have an itemId"); }
      if (this.sessionId == null) { throw new IllegalStateException("Answer must have a sessionId"); }
    }

  }

  /**
   * Returns a route to for a {@link CorespringRestClient} to add the {@link Answer} to a provided {@link Quiz} and
   * external user id.
   */
  public static String getAddAnswerRoute(CorespringRestClient client, Quiz quiz, String externalUid) {
    return new StringBuilder(Quiz.getResourceRoute(client, quiz.getId())).append("/").append(externalUid)
        .append("/add-answer").toString();
  }

  public String getItemId() {
    return itemId;
  }

  public String getSessionId() {
    return sessionId;
  }

  public Integer getScore() {
    return score;
  }

  public Date getLastResponse() {
    return lastResponse;
  }

  public Boolean isComplete() {
    return complete;
  }

}
