package org.corespring.resource;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
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
