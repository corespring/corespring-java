package org.corespring.resource.player;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

@JsonSerialize(using = Role.Serializer.class)
@JsonDeserialize(using = Role.Deserializer.class)
public enum Role {
  ALL,
  STUDENT,
  INSTRUCTOR;

  public static class Serializer extends JsonSerializer<Role> {

    @Override
    public void serialize(Role role, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
      switch (role) {
        case ALL:
          jsonGenerator.writeString("*");
          break;
        default: jsonGenerator.writeString(role.name().toLowerCase());
      }
    }
  }

  public static class Deserializer extends JsonDeserializer<Role> {

    @Override
    public Role deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
      String valueAsString = jsonParser.getValueAsString();
      return valueAsString.equals("*") ? Role.ALL : Role.valueOf(jsonParser.getValueAsString().toUpperCase());
    }

  }

};
