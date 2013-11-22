package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.corespring.resource.question.ItemType;
import org.corespring.resource.question.Subject;
import org.corespring.rest.CorespringRestClient;

import java.util.Collection;

/**
 * An assessment item within the Corespring platform. An {@link Item} represents the metadata information related to the
 * item, which can be subsequently accessed through URLs based on the {@link Item}'s id.
 */
public class Item {

  private static final String RESOURCE_NAME = "items";

  private final String id;
  private final String collectionId;
  private final String title;
  private final String author;
  private final ItemType itemType;
  private final Collection<String> gradeLevel;
  private final Collection<String> keySkills;
  private final Subject primarySubject;
  private final Collection<Standard> standards;
  private final Boolean published;

  @JsonCreator
  public Item(@JsonProperty("id") String id,
              @JsonProperty("collectionid") String collectionId,
              @JsonProperty("title") String title,
              @JsonProperty("author") String author,
              @JsonProperty("itemType") ItemType itemType,
              @JsonProperty("gradeLevel") Collection<String> gradeLevel,
              @JsonProperty("keySkills") Collection<String> keySkills,
              @JsonProperty("primarySubject") Subject primarySubject,
              @JsonProperty("standards") Collection<Standard> standards,
              @JsonProperty("published") Boolean published) {
    this.id = id;
    this.collectionId = collectionId;
    this.title = title;
    this.author = author;
    this.itemType = itemType;
    this.gradeLevel = gradeLevel;
    this.keySkills = keySkills;
    this.primarySubject = primarySubject;
    this.standards = standards;
    this.published = published;
  }

  public static String getResourceRoute(CorespringRestClient client) {
    return client.baseUrl().append(RESOURCE_NAME).toString();
  }

  public String getId() {
    return id;
  }

  public String getCollectionId() {
    return collectionId;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public ItemType getItemType() {
    return itemType;
  }

  public Collection<String> getGradeLevel() {
    return gradeLevel;
  }

  public Collection<String> getKeySkills() {
    return keySkills;
  }

  public Subject getPrimarySubject() {
    return primarySubject;
  }

  public Collection<Standard> getStandards() {
    return standards;
  }

  public Boolean getPublished() {
    return published;
  }
}
