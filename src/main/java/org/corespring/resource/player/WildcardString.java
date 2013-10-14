package org.corespring.resource.player;

public class WildcardString {

  private static final String WILDCARD_STRING = "*";

  public static boolean isWildcard(String string) {
    return string.equals(WILDCARD_STRING);
  }

  public static String wildcard() {
    return WILDCARD_STRING;
  }

}
