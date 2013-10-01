package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.corespring.CorespringRestClient;

import java.util.ArrayList;
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

  private Organization(Builder builder) {
    this.name = builder.name;
    this.id = builder.id;
    this.path = builder.path;
  }

  public static class Builder {

    private String name;
    private String id;
    private Collection<String> path = new ArrayList<String>();

    public Builder() {
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder path(String path) {
      this.path.add(path);
      return this;
    }

    public Organization build() {
      return new Organization(this);
    }

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
