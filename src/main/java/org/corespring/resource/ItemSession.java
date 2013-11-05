package org.corespring.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.corespring.resource.question.Settings;
import org.corespring.rest.CorespringRestClient;

import java.util.Date;

/**
 * An {@link ItemSession} represents an instance of a student responding to a particular CoreSpring assessment item.
 * It includes {@link Settings} which specify how the item is to be rendered, as well as start and finish times for
 * the student's response to the item.
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class ItemSession extends CorespringResource {

  private static final String RESOURCE_ROUTE = "items/:itemId/sessions";

  private final String id;
  private final String itemId;
  private final Integer attempts;
  private final Date start;
  private final Date finish;
  private final Settings settings;

  @JsonCreator
  public ItemSession(@JsonProperty("id") String id,
                     @JsonProperty("itemId") String itemId,
                     @JsonProperty("attempts") Integer attempts,
                     @JsonProperty("start") Date start,
                     @JsonProperty("finish") Date finish,
                     @JsonProperty("settings") Settings settings) {
    this.id = id;
    this.itemId = itemId;
    this.attempts = attempts;
    this.start = start;
    this.finish = finish;
    this.settings = settings;
  }

  private ItemSession(Builder builder) {
    this.id = builder.id;
    this.itemId = builder.itemId;
    this.attempts = builder.attempts;
    this.start = builder.start;
    this.finish = builder.finish;
    this.settings = builder.settings;
  }

  public String getResourcesRoute(CorespringRestClient client) {
    return client.baseUrl().append(RESOURCE_ROUTE.replace(":itemId", this.getItemId())).toString();
  }

  public static String getResourceRoute(CorespringRestClient client, String id) {
    return client.baseUrl().append(RESOURCE_ROUTE).append("/").append(id).toString();
  }

  @Override
  public String getId() {
    return id;
  }

  public String getItemId() {
    return itemId;
  }

  public Integer getAttempts() {
    return attempts;
  }

  public Date getStart() {
    return start;
  }

  public Date getFinish() {
    return finish;
  }

  public Settings getSettings() {
    return settings;
  }

  public static class Builder {
    private String id;
    private String itemId;
    private Integer attempts;
    private Date start;
    private Date finish;
    private Settings settings;

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder itemId(String itemId) {
      this.itemId = itemId;
      return this;
    }

    public Builder attempts(Integer attempts) {
      this.attempts = attempts;
      return this;
    }

    public Builder start(Date start) {
      this.start = start;
      return this;
    }

    public Builder finish(Date finish) {
      this.finish = finish;
      return this;
    }

    public Builder settings(Settings settings) {
      this.settings = settings;
      return this;
    }

    public ItemSession build() {
      return new ItemSession(this);
    }

  }

}
