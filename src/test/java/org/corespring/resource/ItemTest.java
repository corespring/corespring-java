package org.corespring.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.corespring.resource.question.ItemType;
import org.corespring.resource.question.Subject;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ItemTest {

  @Test
  public void testSerialization() throws JsonProcessingException {
    Collection<String> gradeLevel = new ArrayList<String>();
    gradeLevel.add("04");
    Collection<String> keySkills = new ArrayList<String>();
    keySkills.add("Identify");
    Subject primarySubject = new Subject("527d2f4aa81fbc1839792a21", "category", "subject");
    String[] grades = new String[] { "K", "1", "2" };
    Standard standard = new Standard("527d2c81a81fbc1839792a1d", "category", "subCategory", "standard", "subject",
        Arrays.asList(grades), "dotNotation", "CC");
    Collection<Standard> standards = new ArrayList<Standard>();
    standards.add(standard);

    Item item = new Item("527d2ed9a81fbc1839792a1f", "527d2edaa81fbc1839792a20", "title", "author",
        ItemType.MULTI_CHOICE, gradeLevel, keySkills, primarySubject, standards, false);

    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"id\":\"527d2ed9a81fbc1839792a1f\",\"title\":\"title\",\"author\":\"author\",\"itemType\":\"Multiple Choice\",\"gradeLevel\":[\"04\"],\"keySkills\":[\"Identify\"],\"primarySubject\":{\"id\":\"527d2f4aa81fbc1839792a21\",\"category\":\"category\",\"subject\":\"subject\"},\"standards\":[{\"id\":\"527d2c81a81fbc1839792a1d\",\"category\":\"category\",\"subCategory\":\"subCategory\",\"standard\":\"standard\",\"subject\":\"subject\",\"grades\":[\"K\",\"1\",\"2\"],\"dotNotation\":\"dotNotation\",\"categoryAbbreviation\":\"CC\"}],\"published\":false,\"collectionId\":\"527d2edaa81fbc1839792a20\"}",
        objectMapper.writeValueAsString(item)
    );

  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"id\":\"527d2ed9a81fbc1839792a1f\",\"title\":\"title\",\"author\":\"author\",\"itemType\":\"Multiple Choice\",\"gradeLevel\":[\"04\"],\"keySkills\":[\"Identify\"],\"primarySubject\":{\"id\":\"527d2f4aa81fbc1839792a21\",\"category\":\"category\",\"subject\":\"subject\"},\"standards\":[{\"id\":\"527d2c81a81fbc1839792a1d\",\"category\":\"category\",\"subCategory\":\"subCategory\",\"standard\":\"standard\",\"subject\":\"subject\",\"grades\":[\"K\",\"1\",\"2\"],\"dotNotation\":\"dotNotation\",\"categoryAbbreviation\":\"CC\"}],\"published\":false,\"collectionId\":\"527d2edaa81fbc1839792a20\"}";
    ObjectMapper objectMapper = new ObjectMapper();

    Item deserialized = objectMapper.readValue(json, Item.class);

    assertEquals("527d2ed9a81fbc1839792a1f", deserialized.getId());
    assertEquals("527d2edaa81fbc1839792a20", deserialized.getCollectionId());
    assertEquals("title", deserialized.getTitle());
    assertEquals("author", deserialized.getAuthor());
    assertEquals(ItemType.MULTI_CHOICE, deserialized.getItemType());
    assertEquals("04", deserialized.getGradeLevel().iterator().next());
    assertEquals("Identify", deserialized.getKeySkills().iterator().next());
    assertEquals("527d2f4aa81fbc1839792a21", deserialized.getPrimarySubject().getId());
    assertEquals("category", deserialized.getPrimarySubject().getCategory());
    assertEquals("subject", deserialized.getPrimarySubject().getSubject());
    assertEquals(false, deserialized.getPublished());

    assertEquals("527d2c81a81fbc1839792a1d", deserialized.getStandards().iterator().next().getId());
    assertEquals("category", deserialized.getStandards().iterator().next().getCategory());
    assertEquals("subCategory", deserialized.getStandards().iterator().next().getSubCategory());
    assertEquals("standard", deserialized.getStandards().iterator().next().getStandard());
    assertEquals("subject", deserialized.getStandards().iterator().next().getSubject());
    assertEquals("dotNotation", deserialized.getStandards().iterator().next().getDotNotation());
    assertTrue(deserialized.getStandards().iterator().next().getGrades().contains("K"));
    assertTrue(deserialized.getStandards().iterator().next().getGrades().contains("1"));
    assertTrue(deserialized.getStandards().iterator().next().getGrades().contains("2"));
  }

}
