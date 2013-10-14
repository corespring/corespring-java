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

/**
 * An enumerated value describing the various modes under which the CoreSpring item player can operate. More information
 * is available <a href="http://www.corespring.org/developer/home#player-modes">here</a>.
 */
@JsonSerialize(using = Mode.Serializer.class)
@JsonDeserialize(using = Mode.Deserializer.class)
public enum Mode {

  /**
   * if set to ALL then key will work for any mode passed in query string
   */
  ALL,

  /**
   * This mode allows an educator to interact with the item and see how it would appear to a student. The outcome of the
   * interaction will not be saved.
   */
  PREVIEW,

  /**
   * Render mode displays an existing Item Session: It represents a single user's interaction with an item. If the Item
   * Session is not finished a user may submit answers, which will be stored in the Item Session. Once the ItemSession
   * is finished nothing else will get saved to this Item Session. There are different roles for viewing the response;
   * for example, a student could be reviewing his or her own work, or an educator could be reviewing a student's work.
   */
  RENDER,

  /**
   * Administer is the same as Render, except that you can also use an itemId instead of a sessionId. If you do use an
   * itemId the player will create an Item Session for you and then notify you via the onItemSessionCreated callback.
   */
  ADMINISTER,

  /**
   * In aggregate mode, the player renders the interactions in the item with aggregated/averaged information for a set
   * of responses from a group of participants for a given assessmentId. For example, if 30 students "take" an item with
   * a multiple choice interaction, the player will show what percentage of the students selected a particular choice
   * in the set.
   */
  AGGREGATE;

  public static class Serializer extends JsonSerializer<Mode> {

    @Override
    public void serialize(Mode mode, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
      switch (mode) {
        case ALL:
          jsonGenerator.writeString("*");
          break;
        default: jsonGenerator.writeString(mode.name().toLowerCase());
      }
    }

  }

  public static class Deserializer extends JsonDeserializer<Mode> {

    @Override
    public Mode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
      String valueAsString = jsonParser.getValueAsString();
      return valueAsString.equals("*") ? Mode.ALL : Mode.valueOf(jsonParser.getValueAsString().toUpperCase());
    }

  }

};