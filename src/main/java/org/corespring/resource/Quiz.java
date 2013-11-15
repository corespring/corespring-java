package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.corespring.resource.question.Participant;
import org.corespring.rest.CorespringRestClient;

import java.util.*;

/**
 * A {@link Quiz} represents a set of {@link Question}s, {@link Participant}s, and associated metadata. A JSON
 * representation of a {@link Quiz} is shown below:
 *
 * <pre>
 *
 *   {
 *     "id" : "000000000000000000000002",
 *     "orgId" : "51114b307fc1eaa866444648",
 *     "metadata" : {
 *       "title" : "Sample Quiz",
 *       "description": "This is a sample quiz",
 *       "instructions": "This quiz consists of questions to help users get up to speed using the CoreSpring platform",
 *       "classroom": "1034"
 *     },
 *     "questions" : [
 *       {
 *         "itemId" : "503c2e91e4b00f3f0a9a7a6a",
 *         "settings" : {
 *           "maxNoOfAttempts" : 1,
 *           "highlightUserResponse" : true,
 *           "highlightCorrectResponse" : true,
 *           "showFeedback" : false,
 *           "allowEmptyResponses" : false,
 *           "submitCompleteMessage" : "Ok! Your response was submitted.",
 *           "submitIncorrectMessage" : "You may revise your work before you submit your final response."
 *         },
 *         "title" : "How many pancakes were eaten when Rodrigo made breakfast for his family and friends?",
 *         "standards" : [
 *           "2.0A.A.1"
 *         ]
 *       }
 *     ],
 *     "participants" : [
 *       {
 *         "answers" : [
 *           "sessionId" : "515425f33004e34080967bbb",
 *           "itemId" : "503c2e91e4b00f3f0a9a7a6a"
 *         ],
 *         "externalUid" : "my-platform-student-53234"
 *       }
 *     ]
 *   }
 *
 * </pre>
 *
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class Quiz extends CorespringResource {

  private static final String RESOURCE_ROUTE = "quizzes";

  private static final String TITLE_KEY = "title";
  private static final String DESCRIPTION_KEY = "description";
  private static final String INSTRUCTIONS_KEY = "instructions";
  static final String START_DATE_KEY = "startDate";
  static final String END_DATE_KEY = "endDate";

  private final String id;
  private final String orgId;
  private final Map<String, Object> metadata;
  private final Collection<Question> questions;
  private final Collection<Participant> participants;

  @JsonCreator
  public Quiz(@JsonProperty("id") String id,
              @JsonProperty("orgId") String orgId,
              @JsonProperty("metadata") @JsonDeserialize(as=HashMap.class) Map<String, Object> metadata,
              @JsonProperty("questions") Collection<Question> questions,
              @JsonProperty("participants") Collection<Participant> participants) {
    this.id = id;
    this.orgId = orgId;
    this.metadata = metadata;
    this.questions = questions;
    this.participants = participants;
  }

  private Quiz(Builder builder) {
    this.id = builder.id;
    this.orgId = builder.orgId;
    this.metadata = builder.metadata;
    this.questions = builder.questions;
    this.participants = builder.participants.values();
  }

  public static class Builder {

    private String id;
    private String orgId;
    private Map<String, Object> metadata = new HashMap<String, Object>();
    private Collection<Question> questions = new ArrayList<Question>();
    private Map<String, Participant> participants = new HashMap<String, Participant>();

    public Builder() {
    }

    public Builder(Quiz quiz) {
      this.id = quiz.id;
      this.orgId = quiz.orgId;
      this.metadata = new HashMap<String, Object>();
      this.metadata.putAll(quiz.metadata);
      this.questions = quiz.questions;
      this.participants = new HashMap<String, Participant>();
      for (Participant participant : quiz.participants) {
        this.participants.put(participant.getExternalUid(), participant);
      }
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder orgId(String orgId) {
      this.orgId = orgId;
      return this;
    }

    public Builder title(String title) {
      metadata.put(TITLE_KEY, title);
      return this;
    }

    public Builder description(String description) {
      metadata.put(DESCRIPTION_KEY, description);
      return this;
    }

    public Builder instructions(String instructions) {
      metadata.put(INSTRUCTIONS_KEY, instructions);
      return this;
    }

    public Builder question(Question question) {
      questions.add(question);
      return this;
    }

    public Builder addMetadata(String key, String value) {
      metadata.put(key, value);
      return this;
    }

    public Builder starts(Date startDate) {
      Date endDate = (Date) metadata.get(END_DATE_KEY);
      if (endDate != null && endDate.before(startDate)) {
        throw new IllegalArgumentException("Start date cannot come after end date.");
      }
      metadata.put(START_DATE_KEY, startDate);
      return this;
    }

    public Builder ends(Date endDate) {
      Date startDate = (Date) metadata.get(START_DATE_KEY);
      if (startDate != null && endDate.before(startDate)) {
        throw new IllegalArgumentException("End date must come before start date.");
      }
      metadata.put(END_DATE_KEY, endDate);
      return this;
    }

    public Builder removeMetadata(String key) {
      metadata.remove(key);
      return this;
    }

    public Quiz build() {
      if ((metadata.get(START_DATE_KEY) != null) ^ (metadata.get(END_DATE_KEY) != null)) {
        if (metadata.get(START_DATE_KEY) == null) {
          throw new IllegalStateException("Quiz with a start date must have an end date");
        } else {
          throw new IllegalStateException("Quiz with an end date must have a start date");
        }
      }
      return new Quiz(this);
    }

  }

  public static String getResourcesRoute(CorespringRestClient client) {
    return client.baseUrl().append(RESOURCE_ROUTE).toString();
  }

  public static String getResourceRoute(CorespringRestClient client, String id) {
    return client.baseUrl().append(RESOURCE_ROUTE).append("/").append(id).toString();
  }

  public String getParticipantsRoute(CorespringRestClient client) {
    return new StringBuilder(getResourceRoute(client, this.getId())).append("/add-participants").toString();
  }

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  @Override
  public String getId() {
    return id;
  }

  public String getOrgId() {
    return orgId;
  }

  @JsonIgnore
  public String getTitle() {
    return metadata.get(TITLE_KEY).toString();
  }

  @JsonIgnore
  public String getDescription() {
    return metadata.get(DESCRIPTION_KEY).toString();
  }

  @JsonIgnore
  public Date getStart() {
    return getDateFromKey(START_DATE_KEY);
  }

  @JsonIgnore
  public Date getEnd() {
    return getDateFromKey(END_DATE_KEY);
  }

  private Date getDateFromKey(String key) {
    if (metadata.containsKey(key)) {
      if (metadata.get(key) instanceof Date) {
        return (Date) metadata.get(key);
      } else if (metadata.get(key) instanceof Long) {
        return new Date((Long) metadata.get(key));
      } else {
        throw new IllegalStateException(
            "Value in metadata does not represent a valid date: " + metadata.get(key).toString());
      }
    } else {
      return null;
    }
  }

  @JsonIgnore
  public boolean isActive(Date date) {
    if (getStart() != null && getEnd() != null) {
      return getStart().before(date) && getEnd().after(date);
    } else {
      return true;
    }
  }

  @JsonIgnore
  public boolean isActive() {
    return isActive(new Date());
  }

  @JsonIgnore
  public Object getMetadataValue(String key) {
    return metadata.get(key);
  }

  @JsonIgnore
  public String getInstructions() {
    return metadata.get(INSTRUCTIONS_KEY).toString();
  }

  public Collection<Question> getQuestions() {
    return questions;
  }

  public Collection<Participant> getParticipants() {
    return participants;
  }

  /**
   * Convenience method for looking up a {@link Participant} based on their externalUid.
   */
  @JsonIgnore
  public Participant getParticipant(String externalUid) {
    for (Participant participant : participants) {
      if (participant.getExternalUid().equals(externalUid)) {
        return participant;
      }
    }
    return null;
  }

}