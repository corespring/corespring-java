package org.corespring.resource.question;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link Participant} defines a student by an external user id, as well as a set of {@link Answer}s to items within
 * a {@link org.corespring.resource.Quiz}. A {@link Participant}'s {@link Answer}s are unique by item id (i.e., the
 * {@link Builder} for {@link Participant} will not allow duplicate itemIds for {@link Answer}s, and will override
 * existing {@link Answer}s with matching itemId).
 */
public class Participant {

  private final Collection<Answer> answers;
  private final String externalUid;

  @JsonCreator
  public Participant(@JsonProperty("answers") Collection<Answer> answers,
                     @JsonProperty("externalUid") String externalUid) {
    this.answers = answers;
    this.externalUid = externalUid;
  }

  private Participant(Builder builder) {
    this.answers = builder.answers.values();
    this.externalUid = builder.externalUid;
  }

  public static class Builder {

    private Map<String, Answer> answers = new HashMap<String, Answer>();
    private String externalUid;

    public Builder() {
    }

    public Builder(Participant participant) {
      this.answers = new HashMap<String, Answer>();
      for (Answer answer : participant.answers) {
        this.answers.put(answer.getItemId(), answer);
      }
      this.externalUid = participant.externalUid;
    }

    public Builder answer(Answer answer) {
      this.answers.put(answer.getItemId(), answer);
      return this;
    }

    public Builder externalUid(String externalUid) {
      this.externalUid = externalUid;
      return this;
    }

    public Participant build() {
      return new Participant(this);
    }

  }

  public Collection<Answer> getAnswers() {
    return answers;
  }

  public String getExternalUid() {
    return externalUid;
  }

  /**
   * Convenience method for looking up an {@link Answer} based on itemId.
   */
  @JsonIgnore
  public Answer getAnswer(String itemId) {
    for (Answer answer : answers) {
      if (answer.getItemId().equals(itemId)) {
        return answer;
      }
    }
    return null;
  }

}
