package org.corespring.resource.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RoleTest extends EnumTest {

  @Test
  public void testRoleSerialization() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    assertEquals(quoted("*"), objectMapper.writeValueAsString(Role.ALL));
    assertEquals(quoted("instructor"), objectMapper.writeValueAsString(Role.INSTRUCTOR));
    assertEquals(quoted("student"), objectMapper.writeValueAsString(Role.STUDENT));
  }

  @Test
  public void testRoleDeserialization() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    assertEquals(Role.ALL, objectMapper.readValue(quoted("*"), Role.class));
    assertEquals(Role.INSTRUCTOR, objectMapper.readValue(quoted("instructor"), Role.class));
    assertEquals(Role.STUDENT, objectMapper.readValue(quoted("student"), Role.class));
  }

}
