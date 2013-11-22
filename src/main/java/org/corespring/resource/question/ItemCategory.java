package org.corespring.resource.question;

import static org.corespring.resource.question.ItemType.*;

/**
 * An {@link ItemCategory} describes a logical grouping of {@link ItemType}s.
 */
public class ItemCategory {

  public static final ItemCategory FIXED_CHOICE = new ItemCategory("Fixed Choice", new ItemType[] {
      MULTI_CHOICE,
      MULTI_MULTI_CHOICE,
      VISUAL_MULTI_CHOICE,
      INLINE_CHOICE,
      ORDERING,
      DRAG_AND_DROP
  });

  public static final ItemCategory CONSTRUCTED_RESPONSE = new ItemCategory("Constructed Response", new ItemType[] {
      SHORT_ANSWER,
      OPEN_ENDED
  });

  public static final ItemCategory EVIDENCE = new ItemCategory("Evidence", new ItemType[] {
      TEXT_EVIDENCE,
      DOCUMENT_BASED_EVIDENCE,
      PASSAGE_WITH_QUESTIONS_EVIDENCE
  });

  public static final ItemCategory COMPOSITE = new ItemCategory("Composite", new ItemType[] {
      COMPOSITE_MULTI_MULTI_CHOICE,
      COMPOSITE_MULTI_CHOICE_AND_SHORT_ANSWER,
      COMPOSITE_MULTI_CHOICE_SHORT_ANSWER_AND_OPEN_ENDED,
      COMPOSITE_PROJECT,
      COMPOSITE_PERFORMANCE,
      COMPOSITE_ACTIVITY,
      COMPOSITE_ALGEBRA
  });

  public static final ItemCategory ALGEBRA = new ItemCategory("Algebra", new ItemType[] {
      PLOT_LINES,
      PLOT_POINTS,
      EVALUATE_AN_EQUATION
  });

  private final String description;
  private final ItemType[] itemTypes;

  public ItemCategory(String description, ItemType[] itemTypes) {
    this.description = description;
    this.itemTypes = itemTypes;
  }

  public String getDescription() {
    return description;
  }

  public ItemType[] getItemTypes() {
    return itemTypes;
  }

}
