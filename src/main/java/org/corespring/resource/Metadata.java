package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

  private Metadata(Builder builder) {
    this.title = builder.title;
    this.course = builder.course;
    this.note = builder.note;
  }

  public static class Builder {

    private String title = null;
    private String course = null;
    private String note = null;

    public Builder() {
    }

    public Builder(Metadata metadata) {
      this.title = metadata.title;
      this.course = metadata.course;
      this.note = metadata.note;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder course(String course) {
      this.course = course;
      return this;
    }

    public Builder note(String note) {
      this.note = note;
      return this;
    }

    public Metadata build() {
      return new Metadata(this);
    }

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
