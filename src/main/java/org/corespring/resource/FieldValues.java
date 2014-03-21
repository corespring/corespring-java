package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.corespring.resource.question.ItemType;
import org.corespring.rest.CorespringRestClient;

import java.util.ArrayList;
import java.util.Collection;

/**
 * {@link FieldValues} represents a set of available fields for filtering items within the Corespring API.
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class FieldValues extends CorespringResource {

  private static final String RESOURCE_NAME = "field_values";

  private final Collection<String> contributors;
  private final Collection<String> subjects;
  private final Collection<String> standards;
  private final Collection<String> gradeLevels;
  private final Collection<String> bloomsTaxonomy;
  private final Collection<String> keySkills;
  private final Collection<ItemType> itemTypes;
  private final Collection<String> demonstratedKnowledge;

  @JsonCreator
  public FieldValues(@JsonProperty("contributor") Collection<String> contributors,
                     @JsonProperty("subject") Collection<String> subjects,
                     @JsonProperty("standard") Collection<String> standards,
                     @JsonProperty("gradeLevel") Collection<String> gradeLevels,
                     @JsonProperty("bloomsTaxonomy") Collection<String> bloomsTaxonomy,
                     @JsonProperty("keySkill") Collection<String> keySkills,
                     @JsonProperty("itemType") Collection<String> itemTypes,
                     @JsonProperty("demonstratedKnowledge") Collection<String> demonstratedKnowledge) {
    this.contributors = contributors;
    this.subjects = subjects;
    this.standards = standards;
    this.gradeLevels = gradeLevels;
    this.bloomsTaxonomy = bloomsTaxonomy;
    this.keySkills = keySkills;
    if (itemTypes != null) {
      this.itemTypes = new ArrayList<ItemType>(itemTypes.size());
      for (String itemTypeString: itemTypes) {
        ItemType itemType = ItemType.fromDescription(itemTypeString);
        if (itemType != null) {
          this.itemTypes.add(itemType);
        }
      }
    } else {
      this.itemTypes = new ArrayList<ItemType>();
    }
    this.demonstratedKnowledge = demonstratedKnowledge;
  }

  public Collection<String> getContributors() {
    return contributors;
  }

  public Collection<String> getSubjects() {
    return subjects;
  }

  public Collection<String> getStandards() {
    return standards;
  }

  public Collection<String> getGradeLevels() {
    return gradeLevels;
  }

  public Collection<String> getBloomsTaxonomy() {
    return bloomsTaxonomy;
  }

  public Collection<String> getKeySkills() {
    return keySkills;
  }

  public Collection<ItemType> getItemTypes() {
    return itemTypes;
  }

  public Collection<String> getDemonstratedKnowledge() {
    return demonstratedKnowledge;
  }

  public static String getResourceRoute(CorespringRestClient client) {
    return client.baseUrl().append(RESOURCE_NAME).toString();
  }

}
