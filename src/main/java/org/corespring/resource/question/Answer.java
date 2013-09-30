package org.corespring.resource.question;

import java.util.Map;

public class Answer {

  private static final String ITEM_ID_KEY = "itemId";
  private static final String SESSION_ID_KEY = "sessionId";

  private final String itemId;
  private final String sessionId;

  public Answer(String itemId, String sessionId) {
    this.itemId = itemId;
    this.sessionId = sessionId;
  }

  public static Answer fromObjectMap(Map<String, Object> objectMap) {
    String itemId = (String) objectMap.get(ITEM_ID_KEY);
    String sessionId = (String) objectMap.get(SESSION_ID_KEY);

    return new Answer(itemId, sessionId);
  }

  public String getItemId() {
    return this.itemId;
  }

  public String getSessionId() {
    return this.sessionId;
  }


}
