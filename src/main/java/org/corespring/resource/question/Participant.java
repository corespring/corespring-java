package org.corespring.resource.question;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;

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
    this.answers = builder.answers;
    this.externalUid = builder.externalUid;
  }

  public static class Builder {

    private Collection<Answer> answers = new ArrayList<Answer>();
    private String externalUid;

    public Builder() {
    }

    public Builder(Participant participant) {
      this.answers = participant.answers;
      this.externalUid = participant.externalUid;
    }

    public Builder answer(Answer answer) {
      this.answers.add(answer);
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

}
