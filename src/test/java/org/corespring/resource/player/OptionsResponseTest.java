package org.corespring.resource.player;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class OptionsResponseTest {

  @Test
  public void testOptionsResponseSerialization() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    Options request = new Options(Mode.ALL, WildcardString.wildcard(), WildcardString.wildcard(),
        new Date(0L), Role.ALL);

    OptionsResponse optionsResponse = new OptionsResponse("525bf17a92699001e6e81e6d", "a369de25bf73cf3479dbcfc76f8c1b4ad983f777fe4834c33e3e57c98d86d31fe9e46bc606a8e3b61c5f2c1b935a6725e9e3cf227f558d3724895ef84ce43107645baf8f53dd068eafc9759b63b1ad44--b4cee74cb43af0d6b652bb044e9f464d");

    assertEquals(
        "{\"options\":\"a369de25bf73cf3479dbcfc76f8c1b4ad983f777fe4834c33e3e57c98d86d31fe9e46bc606a8e3b61c5f2c1b935a6725e9e3cf227f558d3724895ef84ce43107645baf8f53dd068eafc9759b63b1ad44--b4cee74cb43af0d6b652bb044e9f464d\",\"clientId\":\"525bf17a92699001e6e81e6d\"}",
        objectMapper.writeValueAsString(optionsResponse)
    );
  }

  @Test
  public void testOptionsResponseDeserialization() throws IOException {
    String json = "{\"clientId\":\"525bf17a92699001e6e81e6d\",\"options\":\"a369de25bf73cf3479dbcfc76f8c1b4ad983f777fe4834c33e3e57c98d86d31fe9e46bc606a8e3b61c5f2c1b935a6725e9e3cf227f558d3724895ef84ce43107645baf8f53dd068eafc9759b63b1ad44--b4cee74cb43af0d6b652bb044e9f464d\",\"request\":\"{\\\"mode\\\":\\\"*\\\",\\\"sessionId\\\":\\\"*\\\",\\\"itemId\\\":\\\"*\\\",\\\"expires\\\":0,\\\"role\\\":\\\"*\\\"}\"}";

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    OptionsResponse deserialized = objectMapper.readValue(json, OptionsResponse.class);

    assertEquals("a369de25bf73cf3479dbcfc76f8c1b4ad983f777fe4834c33e3e57c98d86d31fe9e46bc606a8e3b61c5f2c1b935a6725e9e3cf227f558d3724895ef84ce43107645baf8f53dd068eafc9759b63b1ad44--b4cee74cb43af0d6b652bb044e9f464d", deserialized.getOptions());
    assertEquals("525bf17a92699001e6e81e6d", deserialized.getClientId());
  }

}
