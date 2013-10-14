package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.corespring.rest.CorespringRestClient;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A CoreSpring {@link Organization} represents a development partner utilizing the CoreSpring platform. NOTE:
 * Organizations do not map to groups within educational institutions (districts, schools, etc.). The following JSON
 * structure describes a typical Organization:
 *
 * <pre>
 *
 *   {
 *     "id" : "51114b307fc1eaa866444648",
 *     "name" : "Demo Organization"
 *   }
 *
 * </pre>
 *
 */
public class Organization extends CorespringResource {

  private static final String RESOURCE_ROUTE = "organizations";

  private final String name;
  private final String id;
  private final Collection<String> path;
  private final Boolean root;

  @JsonCreator
  public Organization(@JsonProperty("id") String id,
                      @JsonProperty("name") String name,
                      @JsonProperty("path") Collection<String> path,
                      @JsonProperty("isRoot") Boolean root) {
    this.name = name;
    this.id = id;
    this.path = path;
    this.root = root;
  }

  private Organization(Builder builder) {
    this.name = builder.name;
    this.id = builder.id;
    this.path = builder.path;
    this.root = builder.root;
  }

  public static class Builder {

    private String name;
    private String id;
    private Collection<String> path = new ArrayList<String>();
    private Boolean root;

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

    public Builder root(Boolean root) {
      this.root = root;
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

  @Override
  public String getId() {
    return id;
  }

  public Collection<String> getPath() {
    return path;
  }

  @JsonProperty("isRoot")
  public Boolean isRoot() {
    return root;
  }


}
