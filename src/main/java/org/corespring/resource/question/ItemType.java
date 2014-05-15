package org.corespring.resource.question;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.corespring.resource.Item;

import java.io.IOException;

/**
 * {@link ItemType} describes the content of an {@link Item}.
 */
@JsonSerialize(using = ItemType.ItemTypeSerializer.class)
@JsonDeserialize(using = ItemType.ItemTypeDeserializer.class)
public enum ItemType {

  MULTI_CHOICE("Multiple Choice"),
  MULTI_MULTI_CHOICE("Multi-Multi Choice"),
  VISUAL_MULTI_CHOICE("Visual Multi Choice"),
  INLINE_CHOICE("Inline Choice"),
  ORDERING("Ordering"),
  DRAG_AND_DROP("Drag & Drop"),
  SHORT_ANSWER("Constructed Response - Short Answer"),
  OPEN_ENDED("Constructed Response - Open Ended"),
  TEXT_EVIDENCE("Select Evidence in Text"),
  DOCUMENT_BASED_EVIDENCE("Document Based Question"),
  PASSAGE_WITH_QUESTIONS_EVIDENCE("Passage With Questions"),
  COMPOSITE_MULTI_MULTI_CHOICE("Composite - Multiple MC"),
  COMPOSITE_MULTI_CHOICE_AND_SHORT_ANSWER("Composite - MC and SA"),
  COMPOSITE_MULTI_CHOICE_SHORT_ANSWER_AND_OPEN_ENDED("Composite - MC, SA, OE"),
  COMPOSITE_PROJECT("Project"),
  COMPOSITE_PERFORMANCE("Performance"),
  COMPOSITE_ACTIVITY("Activity"),
  COMPOSITE_ALGEBRA("Composite - Algebra"),
  PLOT_LINES("Plot Lines"),
  PLOT_POINTS("Plot Points"),
  EVALUATE_AN_EQUATION("Evaluate an Equation");

  public static final String FIELD_NAME = "itemType";

  private final String description;

  private ItemType(String description) {
    this.description = description;
  }

  public static ItemType fromDescription(String description) {
    for (ItemType itemType : ItemType.values()) {
      if (itemType.getDescription().equals(description)) {
        return itemType;
      }
    }
    return null;
  }

  public String getDescription() {
    return description;
  }

  public static class ItemTypeSerializer extends JsonSerializer<ItemType> {

    @Override
    public void serialize(ItemType itemType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException, JsonProcessingException {
      jsonGenerator.writeString(itemType.getDescription());
    }
  }

  public static class ItemTypeDeserializer extends JsonDeserializer<ItemType> {

    @Override
    public ItemType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException, JsonProcessingException {
      String jsonValue = jsonParser.getText();
      return fromDescription(jsonValue);
    }
  }

}
