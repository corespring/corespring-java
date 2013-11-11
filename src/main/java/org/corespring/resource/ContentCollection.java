package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.corespring.CorespringClient;
import org.corespring.rest.CorespringRestClient;
import org.corespring.rest.ItemQuery;

/**
 * A {@link ContentCollection} represents a set of {@link Item}s within the CoreSpring platform. You can access the
 * items associated with a content collection by passing a content collection's id into an {@link ItemQuery} object and
 * using {@link CorespringClient}'s findItems method.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentCollection {

  private static final String RESOURCE_ROUTE = "collections";

  private final String id;
  private final String name;
  private final String description;
  private final Boolean isPublic;

  @JsonCreator
  public ContentCollection(@JsonProperty("id") String id,
                           @JsonProperty("name") String name,
                           @JsonProperty("description") String description,
                           @JsonProperty("isPublic") Boolean isPublic) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.isPublic = isPublic;
  }

  public static String getResourcesRoute(CorespringRestClient client) {
    return client.baseUrl().append(RESOURCE_ROUTE).toString();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  @JsonGetter("isPublic")
  public Boolean isPublic() {
    return isPublic;
  }

}
