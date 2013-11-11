package org.corespring.resource.question;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.codehaus.jackson.annotate.JsonCreator;

public class Subject {

  private final String id;
  private final String category;
  private final String subject;

  @JsonCreator
  public Subject(@JsonProperty("id") String id,
                 @JsonProperty("category") String category,
                 @JsonProperty("subject") String subject) {
    this.id = id;
    this.category = category;
    this.subject = subject;
  }

  public String getId() {
    return id;
  }

  public String getCategory() {
    return category;
  }

  public String getSubject() {
    return subject;
  }

}
