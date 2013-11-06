package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.corespring.resource.question.Participant;
import org.corespring.rest.CorespringRestClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

  private final String id;
  private final String orgId;
  private final Map<String, String> metadata;
  private final Collection<Question> questions;
  private final Collection<Participant> participants;

  @JsonCreator
  public Quiz(@JsonProperty("id") String id,
              @JsonProperty("orgId") String orgId,
              @JsonProperty("metadata") @JsonDeserialize(as=HashMap.class) Map<String, String> metadata,
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
    private Map<String, String> metadata = new HashMap<String, String>();
    private Collection<Question> questions = new ArrayList<Question>();
    private Map<String, Participant> participants = new HashMap<String, Participant>();

    public Builder() {
    }

    public Builder(Quiz quiz) {
      this.id = quiz.id;
      this.orgId = quiz.orgId;
      this.metadata = new HashMap<String, String>();
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
      metadata.put("title", title);
      return this;
    }

    public Builder description(String description) {
      metadata.put("description", description);
      return this;
    }

    public Builder instructions(String instructions) {
      metadata.put("instructions", instructions);
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

    public Builder removeMetadata(String key) {
      metadata.remove(key);
      return this;
    }

    public Quiz build() {
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

  public Map<String, String> getMetadata() {
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
    return metadata.get("title");
  }

  @JsonIgnore
  public String getDescription() {
    return metadata.get("description");
  }

  @JsonIgnore
  public String getMetadataValue(String key) {
    return metadata.get(key);
  }

  @JsonIgnore
  public String getInstructions() {
    return metadata.get("instructions");
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