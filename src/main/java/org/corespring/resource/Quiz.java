package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.corespring.resource.question.Participant;
import org.corespring.rest.CorespringRestClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link Quiz} represents a set of {@link Question}s, {@link Participant}s, and associated {@link Metadata}. A JSON
 * representation of a {@link Quiz} is shown below:
 *
 * <pre>
 *
 *   {
 *     "id" : "000000000000000000000002",
 *     "orgId" : "51114b307fc1eaa866444648",
 *     "metadata" : {
 *       "title" : "Sample Quiz",
 *       "course" : "Example Course",
 *       "note" : "This is an exmaple of a quiz"
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
public class Quiz extends CorespringResource {

  private static final String RESOURCE_ROUTE = "quizzes";

  private final String id;
  private final String orgId;
  private final Metadata metadata;
  private final Collection<Question> questions;
  private final Collection<Participant> participants;

  @JsonCreator
  public Quiz(@JsonProperty("id") String id,
              @JsonProperty("orgId") String orgId,
              @JsonProperty("metadata") Metadata metadata,
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
    this.metadata = builder.metadataBuilder.build();
    this.questions = builder.questions;
    this.participants = builder.participants.values();
  }

  public static class Builder {

    private String id;
    private String orgId;
    private Metadata.Builder metadataBuilder = new Metadata.Builder();
    private Collection<Question> questions = new ArrayList<Question>();
    private Map<String, Participant> participants = new HashMap<String, Participant>();

    public Builder() {
    }

    public Builder(Quiz quiz) {
      this.id = quiz.id;
      this.orgId = quiz.orgId;
      this.metadataBuilder = new Metadata.Builder(quiz.metadata);
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
      this.metadataBuilder.title(title);
      return this;
    }

    public Builder course(String course) {
      this.metadataBuilder.course(course);
      return this;
    }

    public Builder note(String note) {
      this.metadataBuilder.note(note);
      return this;
    }

    public Builder question(Question question) {
      this.questions.add(question);
      return this;
    }

    /**
     * This method will override existing {@link Participant} objects based on the participant's externalUid.
     */
    public Builder participant(Participant participant) {
      this.participants.put(participant.getExternalUid(), participant);
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

  @Override
  public String getId() {
    return id;
  }

  public String getOrgId() {
    return orgId;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  @JsonIgnore
  public String getTitle() {
    return metadata.getTitle();
  }

  @JsonIgnore
  public String getCourse() {
    return metadata.getCourse();
  }

  @JsonIgnore
  public String getNote() {
    return metadata.getNote();
  }

  public Collection<Question> getQuestions() {
    return questions;
  }

  public Collection<Participant> getParticipants() {
    return participants;
  }

}
