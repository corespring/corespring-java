package org.corespring.rest;

import org.junit.Test;

import static org.corespring.rest.ItemQuery.*;
import static org.junit.Assert.assertEquals;

public class ItemQueryTest {

  private static final String SEARCH_STRING = "bd";
  private static final String BLOOMS_TAXONOMY = "Remembering";
  private static final String CONTRIBUTOR = "Corespring Assessment Professionals";
  private static final String DEMONSTRATED_KNOWLEDGE = "Factual";
  private static final String GRADE_LEVEL = "04";
  private static final String ITEM_TYPE = "Multiple Choice";
  private static final String KEY_SKILL = "Categorize";
  private static final String SUBJECT = "Mathematics";
  private static final String STANDARD = "RL.K.2";
  private static final String COLLECTION = "4ff2e4cae4b077b9e31689fd";


  @Test
  public void testSearchString() {
    String query = new ItemQuery.Builder().searchString(SEARCH_STRING).build().toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedSearchStringClause(SEARCH_STRING, stringBuilder).append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  @Test
  public void testBloomsTaxonomy() {
    String query = new ItemQuery.Builder().bloomsTaxonomy(BLOOMS_TAXONOMY).build().toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedInClause(BLOOMS_TAXONOMY_KEY, BLOOMS_TAXONOMY, stringBuilder).append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  @Test
  public void testContributors() {
    String query = new ItemQuery.Builder().contributor(CONTRIBUTOR).build().toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedInClause(CONTRIBUTORS_KEY, CONTRIBUTOR, stringBuilder).append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  @Test
  public void testDemonstratedKnowledge() {
    String query = new ItemQuery.Builder().demonstratedKnowledge(DEMONSTRATED_KNOWLEDGE).build().toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedInClause(DEMONSTRATED_KNOWLEDGE_KEY, DEMONSTRATED_KNOWLEDGE, stringBuilder).append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  @Test
  public void testGradeLevels() {
    String query = new ItemQuery.Builder().gradeLevel(GRADE_LEVEL).build().toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedInClause(GRADE_LEVEL_KEY, GRADE_LEVEL, stringBuilder).append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  @Test
  public void testItemType() {
    String query = new ItemQuery.Builder().itemType(ITEM_TYPE).build().toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedInClause(ITEM_TYPE_KEY, ITEM_TYPE, stringBuilder).append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  @Test
  public void testKeySkills() {
    String query = new ItemQuery.Builder().keySkill(KEY_SKILL).build().toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedInClause(KEY_SKILLS_KEY, KEY_SKILL, stringBuilder).append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  @Test
  public void testSubjects() {
    String query = new ItemQuery.Builder().subject(SUBJECT).build().toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedInClause(PRIMARY_SUBJECT_KEY, SUBJECT, stringBuilder).append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  @Test
  public void testStandards() {
    String query = new ItemQuery.Builder().standard(STANDARD).build().toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedInClause(STANDARDS_KEY, STANDARD, stringBuilder).append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  @Test
  public void testCollections() {
    String query = new ItemQuery.Builder().collection(COLLECTION).build().toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedInClause(COLLECTIONS_KEY, COLLECTION, stringBuilder).append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  @Test
  public void testAll() {
    String query = new ItemQuery.Builder().searchString(SEARCH_STRING).bloomsTaxonomy(BLOOMS_TAXONOMY)
        .contributor(CONTRIBUTOR).demonstratedKnowledge(DEMONSTRATED_KNOWLEDGE).gradeLevel(GRADE_LEVEL)
        .itemType(ITEM_TYPE).keySkill(KEY_SKILL).subject(SUBJECT).standard(STANDARD).collection(COLLECTION).build()
        .toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedSearchStringClause(SEARCH_STRING, stringBuilder).append(",");
    stringBuilder = expectedInClause(BLOOMS_TAXONOMY_KEY, BLOOMS_TAXONOMY, stringBuilder).append(",");
    stringBuilder = expectedInClause(CONTRIBUTORS_KEY, CONTRIBUTOR, stringBuilder).append(",");
    stringBuilder = expectedInClause(GRADE_LEVEL_KEY, GRADE_LEVEL, stringBuilder).append(",");
    stringBuilder = expectedInClause(DEMONSTRATED_KNOWLEDGE_KEY, DEMONSTRATED_KNOWLEDGE, stringBuilder).append(",");
    stringBuilder = expectedInClause(ITEM_TYPE_KEY, ITEM_TYPE, stringBuilder).append(",");
    stringBuilder = expectedInClause(KEY_SKILLS_KEY, KEY_SKILL, stringBuilder).append(",");
    stringBuilder = expectedInClause(PRIMARY_SUBJECT_KEY, SUBJECT, stringBuilder).append(",");
    stringBuilder = expectedInClause(STANDARDS_KEY, STANDARD, stringBuilder).append(",");
    stringBuilder = expectedInClause(COLLECTIONS_KEY, COLLECTION, stringBuilder);
    stringBuilder.append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  private StringBuilder expectedSearchStringClause(String searchString, StringBuilder stringBuilder) {
    return stringBuilder.append("\"$or\":[{\"title\":{\"$options\":\"i\",\"$regex\":\"\\\\").append(searchString).append("\"}},{\"standards.dotNotation\":{\"$options\":\"i\",\"$regex\":\"\\\\").append(searchString).append("\"}},{\"copyrightOwner\":{\"$options\":\"i\",\"$regex\":\"\\\\").append(searchString).append("\"}},{\"contributor\":{\"$options\":\"i\",\"$regex\":\"\\\\").append(searchString).append("\"}},{\"author\":{\"$options\":\"i\",\"$regex\":\"\\\\").append(searchString).append("\"}}]");
  }

  private StringBuilder expectedInClause(String key, String value, StringBuilder stringBuilder) {
    return stringBuilder.append("\"").append(key).append("\":{\"$in\":[\"").append(value).append("\"]}");
  }

}
