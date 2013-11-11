package org.corespring.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.corespring.resource.question.Subject;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class ItemTest {

  @Test
  public void testSerialization() throws JsonProcessingException {
    Collection<String> gradeLevel = new ArrayList<String>();
    gradeLevel.add("04");
    Collection<String> keySkills = new ArrayList<String>();
    keySkills.add("Identify");
    Subject primarySubject = new Subject("527d2f4aa81fbc1839792a21", "category", "subject");

    Item item = new Item("527d2ed9a81fbc1839792a1f", "527d2edaa81fbc1839792a20", "title", "author", "itemType",
        gradeLevel, keySkills, primarySubject, false);

    ObjectMapper objectMapper = new ObjectMapper();

    assertEquals(
        "{\"id\":\"527d2ed9a81fbc1839792a1f\",\"title\":\"title\",\"author\":\"author\",\"itemType\":\"itemType\",\"gradeLevel\":[\"04\"],\"keySkills\":[\"Identify\"],\"primarySubject\":{\"id\":\"527d2f4aa81fbc1839792a21\",\"category\":\"category\",\"subject\":\"subject\"},\"published\":false,\"collectionId\":\"527d2edaa81fbc1839792a20\"}",
        objectMapper.writeValueAsString(item)
    );

  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"id\":\"527d2ed9a81fbc1839792a1f\",\"title\":\"title\",\"author\":\"author\",\"itemType\":\"itemType\",\"gradeLevel\":[\"04\"],\"keySkills\":[\"Identify\"],\"primarySubject\":{\"id\":\"527d2f4aa81fbc1839792a21\",\"category\":\"category\",\"subject\":\"subject\"},\"published\":false,\"collectionId\":\"527d2edaa81fbc1839792a20\"}";
    ObjectMapper objectMapper = new ObjectMapper();

    Item deserialized = objectMapper.readValue(json, Item.class);

    assertEquals("527d2ed9a81fbc1839792a1f", deserialized.getId());
    assertEquals("527d2edaa81fbc1839792a20", deserialized.getCollectionId());
    assertEquals("title", deserialized.getTitle());
    assertEquals("author", deserialized.getAuthor());
    assertEquals("itemType", deserialized.getItemType());
    assertEquals("04", deserialized.getGradeLevel().iterator().next());
    assertEquals("Identify", deserialized.getKeySkills().iterator().next());
    assertEquals("527d2f4aa81fbc1839792a21", deserialized.getPrimarySubject().getId());
    assertEquals("category", deserialized.getPrimarySubject().getCategory());
    assertEquals("subject", deserialized.getPrimarySubject().getSubject());
    assertEquals(false, deserialized.getPublished());
  }

}
