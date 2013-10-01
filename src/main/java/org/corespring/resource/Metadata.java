package org.corespring.resource;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class Metadata {

  private final String title;
  private final String course;
  private final String note;

  @JsonCreator
  public Metadata(@JsonProperty("title") String title,
                  @JsonProperty("course") String course,
                  @JsonProperty("note") String note) {
    this.title = title;
    this.course = course;
    this.note = note;
  }

  public String getTitle() {
    return title;
  }

  public String getCourse() {
    return course;
  }

  public String getNote() {
    return note;
  }

}
