package org.corespring.resource.player;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.util.Date;

/**
 * A wrapper for {@link Date} which interprets {@link ExpirationDate.WILDCARD_DATE} as a "wildcard" date. In the context
 * of JSON (de)/serialization, ExpirationDate interprets "*" as {@link ExpirationDate.WILDCARD_DATE}.
 */
@JsonSerialize(using = ExpirationDate.Serializer.class)
@JsonDeserialize(using = ExpirationDate.Deserializer.class)
public class ExpirationDate {

  /** Static instance of {@link ObjectMapper} that can be used by static methods **/
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private static final Date WILDCARD_DATE = new Date(0);

  private final Date date;

  public ExpirationDate(Date date) {
    this.date = date;
  }

  public boolean isWildcard() {
    return getDate().equals(WILDCARD_DATE);
  }

  public Date getDate() {
    return this.date;
  }

  public static ExpirationDate wildcard() {
    return new ExpirationDate(WILDCARD_DATE);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ExpirationDate that = (ExpirationDate) o;

    if (date != null ? !date.equals(that.date) : that.date != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return date != null ? date.hashCode() : 0;
  }

  public static class Serializer extends JsonSerializer<ExpirationDate> {

    @Override
    public void serialize(ExpirationDate expirationDate, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
      if (expirationDate.isWildcard()) {
        jsonGenerator.writeString("*");
      } else {
        jsonGenerator.writeString(objectMapper.writeValueAsString(expirationDate.getDate()));
      }
    }

  }

  public static class Deserializer extends JsonDeserializer<ExpirationDate> {

    @Override
    public ExpirationDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
      String valueAsString = jsonParser.getValueAsString();
      return valueAsString.equals("*") ? ExpirationDate.wildcard() :
          new ExpirationDate(objectMapper.readValue(valueAsString, Date.class));
    }
  }

}
