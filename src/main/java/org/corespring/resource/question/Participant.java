package org.corespring.resource.question;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class Participant {

  private static final String ANSWERS_KEY = "answers";
  private static final String EXTERNAL_UID_KEY = "externalUid";

  private final Collection<Answer> answers;
  private final String externalUid;

  public Participant(Collection<Answer> answers, String externalUid) {
    this.answers = answers;
    this.externalUid = externalUid;
  }

  public static Participant fromObjectMap(Map<String, Object> objectMap) {
    Collection<Answer> answers = new ArrayList<Answer>();
    if (objectMap.get(ANSWERS_KEY) != null && objectMap.get(ANSWERS_KEY) instanceof Collection) {

      Collection<Map<String, Object>> answerMaps = (Collection<Map<String, Object>>) objectMap.get(ANSWERS_KEY);

      for (Map<String, Object> answerMap : answerMaps) {
        answers.add(Answer.fromObjectMap(answerMap));
      }
    }

    String externalUid = (String) objectMap.get(EXTERNAL_UID_KEY);

    return new Participant(answers, externalUid);
  }

  public Collection<Answer> getAnswers() {
    return this.answers;
  }

  public String getExternalUid() {
    return this.externalUid;
  }

}
