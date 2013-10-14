package org.corespring.resource.player;

import org.junit.Test;

import static org.junit.Assert.*;

public class WildcardStringTest {

  @Test
  public void testWildcardString() {
    assertEquals("*", WildcardString.wildcard());
  }

  @Test
  public void testIsWildcard() {
    assertTrue(WildcardString.isWildcard("*"));
    assertFalse(WildcardString.isWildcard("this is not the wildcard"));
  }

}
