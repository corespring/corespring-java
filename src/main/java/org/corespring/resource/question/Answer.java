package org.corespring.resource.question;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Answer {

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

    public Builder sessionid(String sessionId) {
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
      return new Answer(this);
    }

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
