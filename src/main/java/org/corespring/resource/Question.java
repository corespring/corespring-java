package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.corespring.resource.question.Settings;

import java.util.ArrayList;
import java.util.Collection;

public class Question {

  private final String itemId;
  private final Settings settings;
  private final String title;
  private final Collection<String> standards;

  public Question(String itemId, String title) {
    this(itemId, Settings.standard(), title, new ArrayList<String>());
  }

  @JsonCreator
  public Question(@JsonProperty("itemId") String itemId,
                  @JsonProperty("settings") Settings settings,
                  @JsonProperty("title") String title,
                  @JsonProperty("standards") Collection<String> standards) {
    this.itemId = itemId;
    this.title = title;
    this.settings = settings;
    this.standards = standards;
  }

  public Question(Question question) {
    this.itemId = question.itemId;
    this.title = question.title;
    this.settings = new Settings(question.settings);
    this.standards = new ArrayList<String>(question.standards.size());
    this.standards.addAll(question.standards);
  }

  private Question(Builder builder) {
    this.itemId = builder.itemId;
    this.title = builder.title;
    this.settings = builder.settings;
    this.standards = builder.standards;
  }

  public static class Builder {

    private String itemId;
    private String title;
    private Settings settings;
    private Collection<String> standards = new ArrayList<String>();

    public Builder() {
    }

    public Builder itemId(String itemId) {
      this.itemId = itemId;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder settings(Settings settings) {
      this.settings = settings;
      return this;
    }

    public Builder standard(String standard) {
      this.standards.add(standard);
      return this;
    }

    public Question build() {
      return new Question(this);
    }

  }

  public String getItemId() {
    return this.itemId;
  }

  public String getTitle() {
    return this.title;
  }

  public Settings getSettings() {
    return this.settings;
  }

  public Collection<String> getStandards() {
    return this.standards;
  }

}
