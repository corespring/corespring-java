package org.corespring.resource;

import org.corespring.CorespringRestClient;

import java.util.Map;

public class Organization {

  private static final String RESOURCE_ROUTE = "organizations";

  private static final String ID_KEY = "id";
  private static final String NAME_KEY = "name";

  private final String name;
  private final String id;

  public Organization(String id, String name) {
    this.name = name;
    this.id = id;
  }

  public static String getResourceRoute(CorespringRestClient client) {
    return new StringBuilder(client.getEndpoint()).append("/").append(CorespringRestClient.API_VESRION).append("/")
        .append(RESOURCE_ROUTE).toString();
  }

  public static Organization fromObjectMap(Map<String, Object> objectMap) {
    if (objectMap.get(ID_KEY) == null) { throw new IllegalArgumentException("Response missing " + ID_KEY); }
    if (objectMap.get(NAME_KEY) == null) { throw new IllegalArgumentException("Response missing " + NAME_KEY); }
    return new Organization((String)objectMap.get(ID_KEY), (String)objectMap.get(NAME_KEY));
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }


}
