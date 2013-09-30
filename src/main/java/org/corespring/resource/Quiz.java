package org.corespring.resource;

import org.corespring.CorespringRestClient;
import org.corespring.resource.question.Participant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class Quiz {

  private static final String RESOURCE_ROUTE = "quizzes";

  private static final String TITLE_KEY = "title";
  private static final String COURSE_KEY = "course";
  private static final String NOTE_KEY = "note";

  private static final String METADATA_KEY = "metadata";
  private static final String QUESTIONS_KEY = "questions";
  private static final String PARTICIPANTS_KEY = "participants";

  private final String title;
  private final String course;
  private final String note;
  private final Collection<Question> questions;
  private final Collection<Participant> participants;

  public Quiz(String title, String course, String note, Collection<Question> questions,
              Collection<Participant> participants) {
    this.title = title;
    this.course = course;
    this.note = note;
    this.questions = questions;
    this.participants = participants;
  }

  public static String getResourceRoute(CorespringRestClient client) {
    return client.baseUrl().append(RESOURCE_ROUTE).toString();
  }

  public static Quiz fromObjectMap(Map<String, Object> objectMap) {
    if (objectMap.get(METADATA_KEY) == null) { throw new IllegalArgumentException("Missing " + METADATA_KEY); }

    Map<String, Object> metadata = (Map<String, Object>) objectMap.get(METADATA_KEY);
    String title = (metadata == null) ? null : (String) metadata.get(TITLE_KEY);
    String course = (metadata == null) ? null : (String) metadata.get(COURSE_KEY);
    String note = (metadata == null) ? null : (String) metadata.get(NOTE_KEY);

    Collection<Question> questions = new ArrayList<Question>();
    if (objectMap.get(QUESTIONS_KEY) != null && objectMap.get(QUESTIONS_KEY) instanceof Collection) {

      Collection<Map<String, Object>> questionMaps = (Collection<Map<String, Object>>) objectMap.get(QUESTIONS_KEY);

      for (Map<String, Object> questionMap : questionMaps) {
        questions.add(Question.fromObjectMap(questionMap));
      }
    }

    Collection<Participant> participants = new ArrayList<Participant>();
    if (objectMap.get(PARTICIPANTS_KEY) != null && objectMap.get(PARTICIPANTS_KEY) instanceof Collection) {
      Collection<Map<String, Object>> participantsMaps = (Collection<Map<String, Object>>) objectMap.get(PARTICIPANTS_KEY);

      for (Map<String, Object> participantMap : participantsMaps) {
        participants.add(Participant.fromObjectMap(participantMap));
      }
    }

    return new Quiz(title, course, note, questions, participants);
  }

  public String getTitle() {
    return this.title;
  }

  public String getCourse() {
    return this.course;
  }

  public String getNote() {
    return this.note;
  }

  public Collection<Question> getQuestions() {
    return this.questions;
  }

  public Collection<Participant> getParticipants() {
    return this.participants;
  }

}
