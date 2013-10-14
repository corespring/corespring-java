package org.corespring.resource.player;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.corespring.resource.CorespringResource;
import org.corespring.rest.CorespringRestClient;

import java.util.Date;

import static org.corespring.resource.player.Mode.ALL;

@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
public class Options extends CorespringResource {

  private static final String ENCRYPTION_ROUTE = "player/encrypt-options";

  private static final Date WILDCARD_DATE = new Date(0L);

  private final Mode mode;
  private final String sessionId;
  private final String itemId;
  private final Date expires;
  private final Role role;

  @JsonCreator
  public Options(@JsonProperty("mode") Mode mode,
                 @JsonProperty("sessionId") String sessionId,
                 @JsonProperty("itemId") String itemId,
                 @JsonProperty("expires") Date expires,
                 @JsonProperty("role") Role role) {
    this.mode = mode;
    this.sessionId = sessionId;
    this.itemId = itemId;
    this.expires = expires;
    this.role = role;
  }

  private Options(Builder builder) {
    this.mode = builder.mode;
    this.sessionId = builder.sessionId;
    this.itemId = builder.itemId;
    this.expires = builder.expires;
    this.role = builder.role;
  }

  public static class Builder {

    private Mode mode = ALL;
    private String sessionId;
    private String itemId;
    private Date expires;
    private Role role;

    public Builder() {
    }

    public Builder(Options options) {
      this.mode = options.mode;
      this.sessionId = options.sessionId;
      this.expires = options.expires;
      this.role = options.role;
    }

    public Builder mode(Mode mode) {
      this.mode = mode;
      return this;
    }

    public Builder sessionId(String sessionId) {
      this.sessionId = sessionId;
      return this;
    }

    public Builder itemId(String itemId) {
      this.itemId = itemId;
      return this;
    }

    public Builder expires(Date expires) {
      this.expires = expires;
      return this;
    }

    public Builder expiresNever() {
      this.expires = WILDCARD_DATE;
      return this;
    }

    public Builder role(Role role) {
      this.role = role;
      return this;
    }

    public Options build() {
      return new Options(this);
    }

  }

  public static String getEncryptionRoute(CorespringRestClient client) {
    return new StringBuilder(client.getEndpoint()).append("/").append(ENCRYPTION_ROUTE).toString();
  }

  public Mode getMode() {
    return mode;
  }

  public String getSessionId() {
    return sessionId;
  }

  public String getItemId() {
    return itemId;
  }

  public Date getExpires() {
    return expires;
  }

  public Role getRole() {
    return role;
  }

  @JsonIgnore
  public String getId() {
    return null;
  }

}