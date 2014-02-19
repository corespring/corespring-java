package org.corespring.resource.question;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link Participant} defines a student by an external user id, as well as a set of {@link Answer}s to items within
 * a {@link org.corespring.resource.Assessment}. A {@link Participant}'s {@link Answer}s are unique by item id (i.e., the
 * {@link Builder} for {@link Participant} will not allow duplicate itemIds for {@link Answer}s, and will override
 * existing {@link Answer}s with matching itemId).
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class Participant {

  private final Collection<Answer> answers;
  private final String externalUid;
  private final Date lastModified;

  @JsonCreator
  public Participant(@JsonProperty("answers") Collection<Answer> answers,
                     @JsonProperty("externalUid") String externalUid,
                     @JsonProperty("lastModified") Date lastModified) {
    this.answers = answers;
    this.externalUid = externalUid;
    this.lastModified = lastModified;
  }

  private Participant(Builder builder) {
    this.answers = builder.answers.values();
    this.externalUid = builder.externalUid;
    this.lastModified = builder.lastModified;
  }

  public static class Builder {

    private Map<String, Answer> answers = new HashMap<String, Answer>();
    private String externalUid;
    private Date lastModified;

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

    public Builder lastModified(Date lastModified) {
      this.lastModified = lastModified;
      return this;
    }

    public Participant build() {
      validate();
      return new Participant(this);
    }

    private void validate() {
      if (externalUid == null) { throw new IllegalStateException("Participant must have an externalUid"); }
    }

  }

  public Collection<Answer> getAnswers() {
    return answers;
  }

  public String getExternalUid() {
    return externalUid;
  }

  public Date getLastModified() {
    return lastModified;
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
