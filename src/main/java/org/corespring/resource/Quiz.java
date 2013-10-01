package org.corespring.resource;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.corespring.CorespringRestClient;
import org.corespring.resource.question.Participant;

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
