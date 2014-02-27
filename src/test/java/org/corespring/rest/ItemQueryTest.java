package org.corespring.rest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.apache.commons.lang.StringEscapeUtils.escapeJavaScript;
import static org.corespring.rest.ItemQuery.*;
import static org.junit.Assert.*;

public class ItemQueryTest {

  private static final String SEARCH_STRING = "string";
  private static final String BLOOMS_TAXONOMY = "Remembering";
  private static final String CONTRIBUTOR = "Corespring Assessment Professionals";
  private static final String DEMONSTRATED_KNOWLEDGE = "Factual";
  private static final String GRADE_LEVEL = "04";
  private static final String ITEM_TYPE = "Multiple Choice";
  private static final String KEY_SKILL = "Categorize";
  private static final String SUBJECT = "Mathematics";
  private static final String STANDARD = "RL.K.2";
  private static final String COLLECTION = "4ff2e4cae4b077b9e31689fd";
  private static final Boolean PUBLISHED = true;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testSearchString() {
    String query = new ItemQuery.Builder().searchString(SEARCH_STRING).build().toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedSearchStringClause(SEARCH_STRING, stringBuilder).append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  @Test
  public void testSearchStringEscapesCharacters() {
    String searchStringWithSlashes = "\\this\\is\\fantastic";
    String query = new ItemQuery.Builder().searchString(searchStringWithSlashes).build().toString();

    assertFalse(query.contains(searchStringWithSlashes));
    assertFalse(query.contains(escapeJavaScript(searchStringWithSlashes)));
    assertTrue(query.contains(escapeJavaScript(escapeJavaScript(searchStringWithSlashes))));
  }

  @Test
  public void testBloomsTaxonomy() {
    String query = new ItemQuery.Builder().bloomsTaxonomy(BLOOMS_TAXONOMY).build().toString();
    assertEquals(withBrackets(objectQueryForKey(BLOOMS_TAXONOMY_KEY, expectedInClause(BLOOMS_TAXONOMY))), query);
  }

  @Test
  public void testContributors() {
    String query = new ItemQuery.Builder().contributor(CONTRIBUTOR).build().toString();
    assertEquals(withBrackets(objectQueryForKey(CONTRIBUTORS_KEY, expectedInClause(CONTRIBUTOR))), query);
  }

  @Test
  public void testDemonstratedKnowledge() {
    String query = new ItemQuery.Builder().demonstratedKnowledge(DEMONSTRATED_KNOWLEDGE).build().toString();
    assertEquals(withBrackets(objectQueryForKey(DEMONSTRATED_KNOWLEDGE_KEY, expectedInClause(DEMONSTRATED_KNOWLEDGE))),
        query);
  }

  @Test
  public void testGradeLevels() {
    String query = new ItemQuery.Builder().gradeLevel(GRADE_LEVEL).build().toString();
    assertEquals(withBrackets(objectQueryForKey(GRADE_LEVEL_KEY, expectedInClause(GRADE_LEVEL))), query);
  }

  @Test
  public void testItemType() {
    String query = new ItemQuery.Builder().itemType(ITEM_TYPE).build().toString();
    assertEquals(withBrackets(objectQueryForKey(ITEM_TYPE_KEY, expectedInClause(ITEM_TYPE))), query);
  }

  @Test
  public void testKeySkills() {
    String query = new ItemQuery.Builder().keySkill(KEY_SKILL).build().toString();
    assertEquals(withBrackets(objectQueryForKey(KEY_SKILLS_KEY, expectedInClause(KEY_SKILL))), query);
  }

  @Test
  public void testSubject() {
    String query = new ItemQuery.Builder().subject(SUBJECT).build().toString();
    assertEquals(withBrackets(objectQueryForKey(PRIMARY_SUBJECT_KEY, expectedInClause(SUBJECT))), query);
  }

  @Test
  public void testNullSubject() {
    exception.expect(NullPointerException.class);
    new ItemQuery.Builder().subject(null);
  }

  @Test
  public void testStandards() {
    String query = new ItemQuery.Builder().standard(STANDARD).build().toString();
    assertEquals(withBrackets(objectQueryForKey(STANDARDS_KEY, expectedInClause(STANDARD))), query);
  }

  @Test
  public void testCollections() {
    String query = new ItemQuery.Builder().collection(COLLECTION).build().toString();
    assertEquals(withBrackets(objectQueryForKey(COLLECTIONS_KEY, expectedInClause(COLLECTION))), query);
  }

  @Test
  public void testPublished() {
    String query = new ItemQuery.Builder().published(PUBLISHED).build().toString();
    assertEquals(withBrackets(primitiveQueryForKey(PUBLISHED_KEY, PUBLISHED.toString())), query);
  }

  @Test
  public void testAll() {
    String query = new ItemQuery.Builder().searchString(SEARCH_STRING).bloomsTaxonomy(BLOOMS_TAXONOMY)
        .contributor(CONTRIBUTOR).demonstratedKnowledge(DEMONSTRATED_KNOWLEDGE).gradeLevel(GRADE_LEVEL)
        .itemType(ITEM_TYPE).keySkill(KEY_SKILL).subject(SUBJECT).standard(STANDARD).collection(COLLECTION)
        .published(PUBLISHED).build()
        .toString();

    StringBuilder stringBuilder = new StringBuilder("{");
    stringBuilder = expectedSearchStringClause(SEARCH_STRING, stringBuilder).append(",")
      .append(objectQueryForKey(BLOOMS_TAXONOMY_KEY, expectedInClause(BLOOMS_TAXONOMY))).append(",")
      .append(objectQueryForKey(CONTRIBUTORS_KEY, expectedInClause(CONTRIBUTOR))).append(",")
      .append(objectQueryForKey(GRADE_LEVEL_KEY, expectedInClause(GRADE_LEVEL))).append(",")
      .append(objectQueryForKey(DEMONSTRATED_KNOWLEDGE_KEY, expectedInClause(DEMONSTRATED_KNOWLEDGE))).append(",")
      .append(objectQueryForKey(ITEM_TYPE_KEY, expectedInClause(ITEM_TYPE))).append(",")
      .append(objectQueryForKey(KEY_SKILLS_KEY, expectedInClause(KEY_SKILL))).append(",")
      .append(objectQueryForKey(PRIMARY_SUBJECT_KEY, expectedInClause(SUBJECT))).append(",")
      .append(objectQueryForKey(STANDARDS_KEY, expectedInClause(STANDARD))).append(",")
      .append(objectQueryForKey(COLLECTIONS_KEY, expectedInClause(COLLECTION))).append(",")
      .append(primitiveQueryForKey(PUBLISHED_KEY, PUBLISHED.toString()))
      .append("}");

    assertEquals(stringBuilder.toString(), query);
  }

  private StringBuilder expectedSearchStringClause(String searchString, StringBuilder stringBuilder) {
    return stringBuilder.append("\"$or\":[{\"title\":{\"$options\":\"i\",\"$regex\":\"\\\\b").append(searchString).append("\"}},{\"standards.dotNotation\":{\"$options\":\"i\",\"$regex\":\"\\\\b").append(searchString).append("\"}},{\"copyrightOwner\":{\"$options\":\"i\",\"$regex\":\"\\\\b").append(searchString).append("\"}},{\"contributor\":{\"$options\":\"i\",\"$regex\":\"\\\\b").append(searchString).append("\"}},{\"author\":{\"$options\":\"i\",\"$regex\":\"\\\\b").append(searchString).append("\"}}]");
  }

  private String expectedInClause(String value) {
    return new StringBuilder("\"$in\":[\"").append(value).append("\"]").toString();
  }

  private String objectQueryForKey(String key, String query) {
    return new StringBuilder("\"").append(key).append("\":{").append(query).append("}").toString();
  }

  private String primitiveQueryForKey(String key, String primitive) {
    return new StringBuilder("\"").append(key).append("\":").append(primitive).toString();
  }

  private String withBrackets(String string) {
    return new StringBuilder("{").append(string).append("}").toString();
  }


}
