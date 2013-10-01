package org.corespring.resource.question;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

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
