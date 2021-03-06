package org.corespring.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.corespring.resource.ContentCollection;
import org.corespring.resource.Item;

import java.io.Serializable;
import java.util.*;

import static org.apache.commons.lang.StringEscapeUtils.escapeJavaScript;

/**
 * An {@link ItemQuery} is a query object which is passed to the CoreSpring API in order to return a subset of available
 * {@link Item}s.
 */
public class ItemQuery implements Serializable {

  private static final long serialVersionUID = 1L;

  static final String BLOOMS_TAXONOMY_KEY = "bloomsTaxonomy";
  static final String CONTRIBUTORS_KEY = "contributor";
  static final String GRADE_LEVEL_KEY = "gradeLevel";
  static final String DEPTH_OF_KNOWLEDGE_KEY = "depthOfKnowledge";
  static final String ITEM_TYPE_KEY = "itemType";
  static final String KEY_SKILLS_KEY = "keySkills";
  static final String PRIMARY_SUBJECT_KEY = "primarySubject.category";
  static final String STANDARDS_KEY = "standards.dotNotation";
  static final String COLLECTIONS_KEY = "collectionId";
  static final String PUBLISHED_KEY = "published";

  private final String searchString;
  private final Collection<String> bloomsTaxonomies;
  private final Collection<String> contributors;
  private final Collection<String> depthOfKnowledge;
  private final Collection<String> gradeLevels;
  private final Collection<String> itemTypes;
  private final Collection<String> keySkills;
  private final Collection<String> subjects;
  private final Collection<String> standards;
  private final Collection<String> collections;
  private final Boolean published;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private ItemQuery(Builder builder) {
    this.searchString = builder.searchString;
    this.bloomsTaxonomies = builder.bloomsTaxonomies;
    this.contributors = builder.contributors;
    this.depthOfKnowledge = builder.depthOfKnowledge;
    this.gradeLevels = builder.gradeLevels;
    this.itemTypes = builder.itemTypes;
    this.keySkills = builder.keySkills;
    this.subjects = builder.subjects;
    this.standards = builder.standards;
    this.collections = builder.collections;
    this.published = builder.published;
  }

  @Override
  public String toString() {
    try {
      List<String> clauses = new ArrayList<String>();
      addIfNotEmpty(clauses, searchString());
      addIfNotEmpty(clauses, asInJson(BLOOMS_TAXONOMY_KEY, this.bloomsTaxonomies));
      addIfNotEmpty(clauses, asInJson(CONTRIBUTORS_KEY, this.contributors));
      addIfNotEmpty(clauses, asInJson(GRADE_LEVEL_KEY, this.gradeLevels));
      addIfNotEmpty(clauses, asInJson(DEPTH_OF_KNOWLEDGE_KEY, this.depthOfKnowledge));
      addIfNotEmpty(clauses, asInJson(ITEM_TYPE_KEY, this.itemTypes));
      addIfNotEmpty(clauses, asInJson(KEY_SKILLS_KEY, this.keySkills));
      addIfNotEmpty(clauses, asInJson(PRIMARY_SUBJECT_KEY, this.subjects));
      addIfNotEmpty(clauses, asInJson(STANDARDS_KEY, this.standards));
      addIfNotEmpty(clauses, asInJson(COLLECTIONS_KEY, this.collections));
      addIfNotEmpty(clauses, addIfNotNull(PUBLISHED_KEY, this.published));

      StringBuilder stringBuilder = new StringBuilder("{");
      for (int i = 0; i < clauses.size(); i++) {
        stringBuilder.append(clauses.get(i));
        if (i != clauses.size() - 1) {
          stringBuilder.append(",");
        }
      }

      return stringBuilder.append("}").toString();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private String searchString() throws JsonProcessingException {
    if (searchString != null) {
      List<Map<String, Map<String, String>>> or = new ArrayList<Map<String, Map<String, String>>>() {
        {
          String[] fields = new String[]
            {"title", "description", "standards.dotNotation", "copyrightOwner", "contributor", "author"};

          for (final String field : fields) {
            this.add(new HashMap<String, Map<String, String>>() {
              {
                this.put(field, new HashMap<String, String>() {
                  {
                    /**
                     * searchString must be escaped here and then later escaped again by Jackson because Mongo utilizes
                     * the string as a regular expression, which requires the string literal to be escaped.
                     */
                    this.put("$regex", "\\b" + escapeJavaScript(searchString));
                    this.put("$options", "i");
                  }
                });
              }
            });
          }
        }
      };
      return new StringBuilder("\"$or\":").append(objectMapper.writeValueAsString(or)).toString();
    } else {
      return null;
    }
  }

  private void addIfNotEmpty(Collection<String> clauses, String newClause) {
    if (!(newClause == null || newClause.isEmpty())) {
      clauses.add(newClause);
    }
  }


  /**
   * Converts Strings to a JSON representation of that collection embedded in object keyed by "$in".
   *
   * For example, given Strings "test1", "test2", the result would be "{\"$in\":[\"\test1",\"test2\"]}".
   */
  private String asInJson(String key, Collection<String> strings) throws JsonProcessingException {
    List<String> nonNullStrings = new ArrayList<String>();

    String clause = null;
    String emptyClause = null;

    for (String string : strings) {
      if (string != null) {
        nonNullStrings.add(string);
      }
    }

    if (!strings.isEmpty()) {
      if (strings.contains(null)) {
        emptyClause = "\"$not\":{\"$exists\":true}";
      }
      if (!nonNullStrings.isEmpty()) {
        clause = new StringBuilder("\"$in\":")
            .append(objectMapper.writeValueAsString(nonNullStrings)).toString();
      }
    }

    StringBuilder stringBuilder = new StringBuilder("\"").append(key).append("\":{");

    if (clause != null && emptyClause != null) {
      return stringBuilder.append(clause).append(",\"$or\":{").append(emptyClause).append("}}").toString();
    } else if (clause != null && emptyClause == null) {
      return stringBuilder.append(clause).append("}").toString();
    } else if (clause == null && emptyClause != null) {
      return stringBuilder.append(emptyClause).append("}").toString();
    } else {
      return null;
    }
  }

  private String addIfNotNull(String key, Object object) {
    if (object != null) {
      if (object instanceof Boolean || object instanceof Integer) {
        return "\"" + key + "\":" + object.toString();
      } else {
        return "\"" + key + "\":\"" + object.toString() + "\"";
      }
    } else {
      return null;
    }
  }

  public static class Builder {

    private String searchString;
    private Collection<String> bloomsTaxonomies = new HashSet<String>();
    private Collection<String> contributors = new HashSet<String>();
    private Collection<String> depthOfKnowledge = new HashSet<String>();
    private Collection<String> gradeLevels = new HashSet<String>();
    private Collection<String> itemTypes = new HashSet<String>();
    private Collection<String> keySkills = new HashSet<String>();
    private Collection<String> subjects = new HashSet<String>();
    private Collection<String> standards = new HashSet<String>();
    private Collection<String> collections = new HashSet<String>();
    private Boolean published;

    public Builder() {
    }

    public Builder(ItemQuery itemQuery) {
      this.searchString = itemQuery.searchString;
      this.bloomsTaxonomies = new HashSet<String>(itemQuery.bloomsTaxonomies.size());
      this.bloomsTaxonomies.addAll(itemQuery.bloomsTaxonomies);
      this.contributors = new HashSet<String>(itemQuery.contributors.size());
      this.contributors.addAll(itemQuery.contributors);
      this.depthOfKnowledge = new HashSet<String>(itemQuery.depthOfKnowledge.size());
      this.depthOfKnowledge.addAll(itemQuery.depthOfKnowledge);
      this.gradeLevels = new HashSet<String>(itemQuery.gradeLevels.size());
      this.gradeLevels.addAll(itemQuery.gradeLevels);
      this.itemTypes = new HashSet<String>(itemQuery.itemTypes.size());
      this.itemTypes.addAll(itemQuery.itemTypes);
      this.keySkills = new HashSet<String>(itemQuery.keySkills.size());
      this.keySkills.addAll(itemQuery.keySkills);
      this.subjects = new HashSet<String>(itemQuery.subjects.size());
      this.subjects.addAll(itemQuery.subjects);
      this.standards = new HashSet<String>(itemQuery.standards.size());
      this.standards.addAll(itemQuery.standards);
      this.collections = new HashSet<String>(itemQuery.collections.size());
      this.collections.addAll(itemQuery.collections);
      this.published = itemQuery.published;
    }

    public Builder searchString(String searchString) {
      this.searchString = searchString;
      return this;
    }

    public Builder bloomsTaxonomy(String bloomsTaxonomy) {
      this.bloomsTaxonomies.add(bloomsTaxonomy);
      return this;
    }

    public Builder contributor(String contributor) {
      this.contributors.add(contributor);
      return this;
    }

    public Builder depthOfKnowledge(String depthOfKnowledge) {
      this.depthOfKnowledge.add(depthOfKnowledge);
      return this;
    }

    public Builder gradeLevel(String gradeLevel) {
      this.gradeLevels.add(gradeLevel);
      return this;
    }

    public Builder itemType(String itemType) {
      this.itemTypes.add(itemType);
      return this;
    }

    public Builder keySkill(String keySkill) {
      this.keySkills.add(keySkill);
      return this;
    }

    public Builder subject(String subjectCategory) {
      if (subjectCategory == null) {
        throw new NullPointerException("subject cannot be null");
      } else {
        this.subjects.add(subjectCategory);
      }
      return this;
    }

    public Builder standard(String standard) {
      this.standards.add(standard);
      return this;
    }

    public Builder collection(String collectionId) {
      this.collections.add(collectionId);
      return this;
    }

    public Builder collection(ContentCollection collection) {
      this.collections.add(collection.getId());
      return this;
    }

    public Builder published(Boolean published) {
      this.published = published;
      return this;
    }

    public ItemQuery build() {
      return new ItemQuery(this);
    }

  }

}
