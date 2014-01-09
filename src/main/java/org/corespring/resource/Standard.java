package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class Standard {

  private final String id;
  private final String category;
  private final String subCategory;
  private final String standard;
  private final String subject;
  private final Collection<String> grades;
  private final String dotNotation;
  private final String categoryAbbreviation;

  @JsonCreator
  public Standard(@JsonProperty("id") String id,
                  @JsonProperty("category") String category,
                  @JsonProperty("subCategory") String subCategory,
                  @JsonProperty("standard") String standard,
                  @JsonProperty("subject") String subject,
                  @JsonProperty("grades") Collection<String> grades,
                  @JsonProperty("dotNotation") String dotNotation,
                  @JsonProperty("categoryAbbreviation") String categoryAbbreviation) {
    this.id = id;
    this.category = category;
    this.subCategory = subCategory;
    this.standard = standard;
    this.subject = subject;
    this.grades = grades;
    this.dotNotation = dotNotation;
    this.categoryAbbreviation = categoryAbbreviation;
  }

  public String getId() {
    return id;
  }

  public String getCategory() {
    return category;
  }

  public String getSubCategory() {
    return subCategory;
  }

  public String getStandard() {
    return standard;
  }

  public String getSubject() {
    return subject;
  }

  public Collection<String> getGrades() {
    return grades;
  }

  public String getDotNotation() {
    return dotNotation;
  }

  public String getCategoryAbbreviation() {
    return categoryAbbreviation;
  }

}
