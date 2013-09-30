package org.corespring.resource;

import org.corespring.resource.question.Settings;

import java.util.Collection;
import java.util.Map;

public class Question {

  private static final String ITEM_ID_KEY = "itemId";
  private static final String SETTINGS_ID_KEY = "settings";
  private static final String TITLE_ID_KEY = "title";
  private static final String STANDARDS_ID_KEY = "standards";

  private final String itemId;
  private final Settings settings;
  private final String title;
  private final Collection<String> standards;

  public Question(String itemId,  Settings settings, String title, Collection<String> standards) {
    this.itemId = itemId;
    this.title = title;
    this.settings = settings;
    this.standards = standards;
  }

  public static Question fromObjectMap(Map<String, Object> objectMap) {
    String itemId = (String) objectMap.get(ITEM_ID_KEY);
    Settings settings = Settings.fromObjectMap((Map<String, Object>) objectMap.get(SETTINGS_ID_KEY));
    String title = (String) objectMap.get(TITLE_ID_KEY);
    Collection<String> standards = (Collection<String>) objectMap.get(STANDARDS_ID_KEY);

    return new Question(itemId, settings, title, standards);
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
