package org.corespring.resource.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class OptionsTest {

  private String itemId = "5258575e30045200b68eb227";
  private String sessionId = "5258575e30045200b68eb226";
  private Date expirationDate = new Date(1381523869741L);

  @Test
  public void testOptionsWildcardSerialization() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    Options options = new Options(Mode.ALL, WildcardString.wildcard(), WildcardString.wildcard(),
        ExpirationDate.wildcard(), Role.ALL);

    assertEquals(
        "{\"mode\":\"*\",\"sessionId\":\"*\",\"itemId\":\"*\",\"expires\":\"*\",\"role\":\"*\"}",
        objectMapper.writeValueAsString(options)
    );
  }

  @Test
  public void testOptionsNonwildcardSerialization() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Options options = new Options(Mode.ADMINISTER, sessionId, itemId, new ExpirationDate(expirationDate), Role.INSTRUCTOR);

    assertEquals(
        "{\"mode\":\"administer\",\"sessionId\":\"5258575e30045200b68eb226\",\"itemId\":\"5258575e30045200b68eb227\",\"expires\":\"1381523869741\",\"role\":\"instructor\"}",
        objectMapper.writeValueAsString(options)
    );

  }

  @Test
  public void testOptionsWildcardDeserialization() throws IOException {
    String json = "{\"mode\":\"*\",\"itemId\":\"*\",\"expires\":\"*\",\"role\":\"*\",\"sessionId\":\"*\"}";

    ObjectMapper objectMapper = new ObjectMapper();
    Options options = objectMapper.readValue(json, Options.class);

    assertEquals(Mode.ALL, options.getMode());
    assertEquals(WildcardString.wildcard(), options.getItemId());
    assertEquals(WildcardString.wildcard(), options.getSessionId());
    assertEquals(ExpirationDate.wildcard(), options.getExpires());
    assertEquals(Role.ALL, options.getRole());
  }

  @Test
  public void testOptionsNonwildcardDeserialiation() throws IOException {
    String json = "{\"mode\":\"administer\",\"itemId\":\"5258575e30045200b68eb227\",\"expires\":\"1381523869741\",\"role\":\"instructor\",\"sessionId\":\"5258575e30045200b68eb226\"}";

    ObjectMapper objectMapper = new ObjectMapper();
    Options options = objectMapper.readValue(json, Options.class);

    assertEquals(Mode.ADMINISTER, options.getMode());
    assertEquals(itemId, options.getItemId());
    assertEquals(sessionId, options.getSessionId());
    assertEquals(expirationDate, options.getExpires().getDate());
    assertEquals(Role.INSTRUCTOR, options.getRole());
  }

}
