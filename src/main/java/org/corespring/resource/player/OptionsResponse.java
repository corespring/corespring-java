package org.corespring.resource.player;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.corespring.resource.CorespringResource;

public class OptionsResponse extends CorespringResource {

  private final String clientId;
  private final String options;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @JsonCreator
  public OptionsResponse(@JsonProperty("client_id") String clientId,
                         @JsonProperty("options") String options) {
    this.clientId = clientId;
    this.options = options;
  }

  public String getClientId() {
    return clientId;
  }

  public String getOptions() {
    return options;
  }

  @JsonIgnore
  public String getId() {
    return null;
  }

}
