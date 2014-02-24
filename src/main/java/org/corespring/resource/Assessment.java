package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.corespring.resource.question.Answer;
import org.corespring.resource.question.Participant;
import org.corespring.rest.CorespringRestClient;

import java.util.*;

/**
 * An {@link Assessment} represents a set of {@link Question}s, {@link Participant}s, and associated metadata. A JSON
 * representation of an {@link Assessment} is shown below:
 *
 * <pre>
 *
 *   {
 *     "id" : "000000000000000000000002",
 *     "orgId" : "51114b307fc1eaa866444648",
 *     "metadata" : {
 *       "title" : "Sample Assessment",
 *       "description": "This is a sample assessment",
 *       "instructions": "This assessment consists of questions to help users get up to speed using the CoreSpring platform",
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
public class Assessment extends CorespringResource {

  private static final String RESOURCE_ROUTE = "assessments";
  private static final String AUTHOR_KEY = "author";

  private static final String TITLE_KEY = "title";
  private static final String DESCRIPTION_KEY = "description";
  private static final String INSTRUCTIONS_KEY = "instructions";

  private final String id;
  private final String orgId;
  private final Date start;
  private final Date end;
  private final Map<String, Object> metadata;
  private final Collection<Question> questions;
  private final Collection<Participant> participants;

  @JsonCreator
  public Assessment(@JsonProperty("id") String id,
                    @JsonProperty("orgId") String orgId,
                    @JsonProperty("start") Date start,
                    @JsonProperty("end") Date end,
                    @JsonProperty("metadata") @JsonDeserialize(as = HashMap.class) Map<String, Object> metadata,
                    @JsonProperty("questions") Collection<Question> questions,
                    @JsonProperty("participants") Collection<Participant> participants) {
    this.id = id;
    this.orgId = orgId;
    this.start = start;
    this.end = end;
    this.metadata = metadata == null ? new HashMap<String, Object>() : metadata;
    this.questions = questions == null ? new ArrayList<Question>() : questions;
    this.participants = participants == null ? new ArrayList<Participant>() : participants;
  }

  private Assessment(Builder builder) {
    this.id = builder.id;
    this.orgId = builder.orgId;
    this.start = builder.start;
    this.end = builder.end;
    this.metadata = builder.metadata == null ? new HashMap<String, Object>() : builder.metadata;
    this.questions = builder.questions == null ? new ArrayList<Question>() : builder.questions;
    this.participants = builder.participants == null ? new ArrayList<Participant>() : builder.participants.values();
  }

  public static class Builder {

    private String id;
    private String orgId;
    private Date start;
    private Date end;
    private Map<String, Object> metadata = new HashMap<String, Object>();
    private Collection<Question> questions = new ArrayList<Question>();
    private Map<String, Participant> participants = new HashMap<String, Participant>();

    public Builder() {
    }

    public Builder(Assessment assessment) {
      this.id = assessment.id;
      this.orgId = assessment.orgId;
      this.start = assessment.start;
      this.end = assessment.end;
      this.metadata = new HashMap<String, Object>();
      this.metadata.putAll(assessment.metadata);
      this.questions.addAll(assessment.questions);
      this.participants = new HashMap<String, Participant>();
      for (Participant participant : assessment.participants) {
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

    public Builder start(Date start) {
      if (end != null && end.before(start)) {
        throw new IllegalArgumentException("Start date cannot come after end date.");
      }
      this.start = start;
      return this;
    }

    public Builder end(Date end) {
      if (start != null && end.before(start)) {
        throw new IllegalArgumentException("End date must come before start date.");
      }
      this.end = end;
      return this;
    }

    public Builder removeMetadata(String key) {
      metadata.remove(key);
      return this;
    }

    public Assessment build() {
      if ((start != null) ^ (end != null)) {
        if (start == null) {
          throw new IllegalStateException("Assessment with a start date must have an end date");
        } else {
          throw new IllegalStateException("Assessment with an end date must have a start date");
        }
      }
      return new Assessment(this);
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

  public static String getAuthorRoute(CorespringRestClient client, String authorId) {
    return client.baseUrl().append(RESOURCE_ROUTE).append("/").append(AUTHOR_KEY).append("/").append(authorId)
        .toString();
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

  public Date getStart() {
    return start;
  }

  public Date getEnd() {
    return end;
  }

  @JsonIgnore
  public String getTitle() {
    return metadata.containsKey(TITLE_KEY) ? metadata.get(TITLE_KEY).toString() : null;
  }

  @JsonIgnore
  public String getDescription() {
    return metadata.containsKey(DESCRIPTION_KEY) ? metadata.get(DESCRIPTION_KEY).toString() : null;
  }

  @JsonIgnore
  public String getInstructions() {
    return metadata.containsKey(INSTRUCTIONS_KEY) ? metadata.get(INSTRUCTIONS_KEY).toString() : null;
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
    return metadata.containsKey(key) ? metadata.get(key) : null;
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

  @JsonIgnore
  public boolean isFinished(Participant participant) {
    Participant existingParticipant = getExistingParticipant(participant);

    if (existingParticipant != null) {
      Collection<String> questionIds = new HashSet<String>(questions.size());
      for (Question question : this.getQuestions()) {
        questionIds.add(question.getItemId());
      }

      Collection<String> answerQuestionIds = new HashSet<String>(existingParticipant.getAnswers().size());
      for (Answer answer : existingParticipant.getAnswers()) {
        answerQuestionIds.add(answer.getItemId());
      }

      questionIds.removeAll(answerQuestionIds);
      return questionIds.isEmpty();

    } else {
      return false;
    }
  }

  private Participant getExistingParticipant(Participant participant) {
    for (Participant existingParticipant : participants) {
      if (existingParticipant.getExternalUid().equals(participant.getExternalUid())) {
        return existingParticipant;
      }
    }
    return null;
  }

}