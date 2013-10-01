package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.corespring.CorespringRestClient;
import org.corespring.resource.question.Participant;

import java.util.ArrayList;
import java.util.Collection;

public class Quiz {

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
    this.metadata = builder.metadata;
    this.questions = builder.questions;
    this.participants = builder.participants;
  }

  public static class Builder {

    private String id;
    private String orgId;
    private Metadata metadata;
    private Collection<Question> questions = new ArrayList<Question>();
    private Collection<Participant> participants = new ArrayList<Participant>();

    public Builder() {
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder orgId(String orgId) {
      this.orgId = orgId;
      return this;
    }

    public Builder metadata(Metadata metadata) {
      this.metadata = metadata;
      return this;
    }

    public Builder question(Question question) {
      this.questions.add(question);
      return this;
    }

    public Builder participant(Participant participant) {
      this.participants.add(participant);
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

  public String getId() {
    return id;
  }

  public String getOrgId() {
    return orgId;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public Collection<Question> getQuestions() {
    return questions;
  }

  public Collection<Participant> getParticipants() {
    return participants;
  }

}
