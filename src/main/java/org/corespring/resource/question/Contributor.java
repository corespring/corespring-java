package org.corespring.resource.question;

import org.corespring.CorespringClient;

/**
 * This is a placeholder class for contributors. It may be used for something in the future.
 */
public class Contributor {

  public static final String FIELD_NAME = "contributor";
  private static final String FIELD_VALUES_ROUTE = "field_values/contributor/:contributor";

  public static String getFieldValuesRoute(CorespringClient client, String contributor) {
    return client.baseUrl().append(FIELD_VALUES_ROUTE.replace(":contributor", contributor.replaceAll(" ", "%20"))).toString();
  }

}
