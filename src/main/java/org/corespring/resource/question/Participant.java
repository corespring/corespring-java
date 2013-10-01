package org.corespring.resource.question;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

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

  public Collection<Answer> getAnswers() {
    return answers;
  }

  public String getExternalUid() {
    return externalUid;
  }

}
