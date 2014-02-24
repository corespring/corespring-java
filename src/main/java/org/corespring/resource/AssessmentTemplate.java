package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.corespring.rest.CorespringRestClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * An {@link AssessmentTemplate} contains many of the same attributes as an {@link Assessment}, the key difference
 * being that an {@link AssessmentTemplate} does not contain any of the details relevant to a single administration of
 * an {@link Assessment}. For example, an {@link AssessmentTemplate} does not include the details about particular
 * students' responses, when an {@link Assessment} starts, or when it ends. It is meant to be used, literally, as a
 * template of the item content from which to build an {@link Assessment}.
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class AssessmentTemplate extends CorespringResource {

  private static final String RESOURCE_ROUTE = "assessments/templates";

  private final String id;
  private final String orgId;
  private final String collectionId;
  private final Map<String, Object> metadata;
  private final Collection<Question> questions;

  @JsonCreator
  public AssessmentTemplate(@JsonProperty("id") String id,
                            @JsonProperty("orgId") String orgId,
                            @JsonProperty("collectionId") String collectionId,
                            @JsonProperty("metadata") @JsonDeserialize(as = HashMap.class) Map<String, Object> metadata,
                            @JsonProperty("questions") Collection<Question> questions) {
    this.id = id;
    this.orgId = orgId;
    this.collectionId = collectionId;
    this.metadata = metadata == null ? new HashMap<String, Object>() : metadata;
    this.questions = questions == null ? new ArrayList<Question>() : questions;
  }

  private AssessmentTemplate(Builder builder) {
    this.id = builder.id;
    this.orgId = builder.orgId;
    this.collectionId = builder.collectionId;
    this.metadata = builder.metadata == null ? new HashMap<String, Object>() : builder.metadata;
    this.questions = builder.questions == null ? new ArrayList<Question>() : builder.questions;
  }

  public static class Builder {

    private String id;
    private String orgId;
    private String collectionId;
    private Map<String, Object> metadata = new HashMap<String, Object>();
    private Collection<Question> questions = new ArrayList<Question>();

    public Builder() {
    }

    public Builder(AssessmentTemplate assessmentTemplate) {
      this.id = assessmentTemplate.id;
      this.orgId = assessmentTemplate.orgId;
      this.collectionId = assessmentTemplate.collectionId;
      this.metadata.putAll(assessmentTemplate.metadata);
      this.questions.addAll(assessmentTemplate.questions);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder orgId(String orgId) {
      this.orgId = orgId;
      return this;
    }

    public Builder collectionId(String collectionId) {
      this.collectionId = collectionId;
      return this;
    }

    public Builder addMetadata(String key, String value) {
      metadata.put(key, value);
      return this;
    }

    public Builder question(Question question) {
      questions.add(question);
      return this;
    }

    public AssessmentTemplate build() {
      return new AssessmentTemplate(this);
    }

  }

  public static String getResourcesRoute(CorespringRestClient client) {
    return client.baseUrl().append(RESOURCE_ROUTE).toString();
  }

  @Override
  public String getId() {
    return id;
  }

  public String getOrgId() {
    return orgId;
  }

  public String getCollectionId() {
    return collectionId;
  }

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  @JsonIgnore
  public Object getMetadataValue(String key) {
    return metadata.containsKey(key) ? metadata.get(key) : null;
  }

  public Collection<Question> getQuestions() {
    return questions;
  }

}
