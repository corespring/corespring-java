package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.corespring.CorespringClient;
import org.corespring.rest.CorespringRestClient;
import org.corespring.rest.ItemQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A {@link ContentCollection} represents a set of {@link Item}s within the CoreSpring platform. You can access the
 * items associated with a content collection by passing a content collection's id into an {@link ItemQuery} object and
 * using {@link CorespringClient}'s findItems method.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentCollection {

  private static final String RESOURCE_ROUTE = "collections";
  private static final String FIELD_VALUE_ROUTE = "collections/:ids/fieldValues/:field";
  private static final String FIELD_VALUES_ROUTE = "field_values/collection/:collectionId";

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

  public static String getResourcesRoute(CorespringClient client) {
    return client.baseUrl().append(RESOURCE_ROUTE).toString();
  }

  public static String getFieldValuesRoute(CorespringClient client, Collection<String> collectionIds,
                                           String field) {
    StringBuilder collectionIdBuilder = new StringBuilder();
    List<String> collectionIdsList = new ArrayList<String>(collectionIds);

    for (int i = 0; i < collectionIdsList.size(); i++) {
      collectionIdBuilder.append(collectionIdsList.get(i));
      if (i != collectionIdsList.size() - 1) {
        collectionIdBuilder.append(",");
      }
    }

    return client.baseUrl()
        .append(FIELD_VALUE_ROUTE.replace(":ids", collectionIdBuilder.toString()).replace(":field", field)).toString();
  }

  public static String getFieldValuesRoute(CorespringClient client, String collectionId) {
    return client.baseUrl()
        .append(FIELD_VALUES_ROUTE.replace(":collectionId", collectionId)).toString();
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
