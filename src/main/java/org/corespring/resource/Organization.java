package org.corespring.resource;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.corespring.CorespringRestClient;

import java.util.Collection;

public class Organization {

  private static final String RESOURCE_ROUTE = "organizations";

  private final String name;
  private final String id;
  private final Collection<String> path;

  @JsonCreator
  public Organization(@JsonProperty("id") String id,
                      @JsonProperty("name") String name,
                      @JsonProperty("path") Collection<String> path) {
    this.name = name;
    this.id = id;
    this.path = path;
  }

  public static String getResourceRoute(CorespringRestClient client) {
    return client.baseUrl().append(RESOURCE_ROUTE).toString();
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public Collection<String> getPath() {
    return path;
  }


}
