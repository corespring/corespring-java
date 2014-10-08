package org.corespring.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import org.corespring.resource.question.ItemType;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.corespring.resource.question.ItemType.*;

public class FieldValuesTest {

  public static final Collection<String> CONTRIBUTORS = new ArrayList<String>() {
    {
      add("Illustrative Mathematics"); add("New Classrooms Innovation Partners");
      add("New England Common Assessment Program"); add("New York State Education Department");
      add("Smarter Balanced Assessment Consortium"); add("State of New Jersey Department of Education"); add("TIMSS");
    }
  };

  public static final Collection<String> SUBJECTS = new ArrayList<String>() {
    {
      add("English Language Arts"); add("Mathematics"); add("Science");
    }
  };

  public static final Collection<String> STANDARDS = new ArrayList<String>() {
    {
      add("1.NBT.B.2a"); add("2.NBT.A.1"); add("2.NBT.A.2"); add("2.OA.A.1"); add("4.G.A.1"); add("4.G.A.2");
      add("4.MD.A.3"); add("4.MD.C.5a"); add("4.MD.C.5b"); add("4.MD.C.6"); add("4.NBT.B.4"); add("5.G.A.2");
      add("5.G.B.3"); add("5.G.B.4"); add("5.MD.A.1"); add("5.MD.C.3a"); add("5.MD.C.3b"); add("5.MD.C.4");
      add("5.MD.C.5a"); add("5.MD.C.5b"); add("5.NBT.A.1"); add("5.NBT.A.2"); add("5.NBT.A.3b"); add("5.NBT.B.5");
      add("5.NBT.B.7"); add("5.NF.A.2"); add("5.NF.B.4b"); add("5.NF.B.5a"); add("5.NF.B.5b"); add("5.NF.B.6");
      add("6.EE.A.1"); add("6.EE.A.2a"); add("6.EE.A.2b"); add("6.EE.A.2c"); add("6.EE.B.5"); add("6.EE.B.6");
      add("6.EE.B.7"); add("6.G.A.1"); add("6.G.A.2"); add("6.G.A.4"); add("6.NS.B.3"); add("6.NS.C.5");
      add("6.NS.C.6a"); add("6.NS.C.6b"); add("6.NS.C.6c"); add("6.NS.C.7a"); add("6.NS.C.7b"); add("6.NS.C.8");
      add("6.RP.A.2"); add("6.RP.A.3a"); add("6.RP.A.3c"); add("6.SP.A.1"); add("6.SP.A.2"); add("6.SP.A.3");
      add("6.SP.B.5a"); add("6.SP.B.5b"); add("6.SP.B.5c"); add("6.SP.B.5d"); add("7.EE.A.1"); add("7.EE.A.2");
      add("7.EE.B.3"); add("7.EE.B.4a"); add("7.EE.B.4b"); add("7.G.A.1"); add("7.G.B.4"); add("7.G.B.5");
      add("7.G.B.6"); add("7.NS.A.1a"); add("7.NS.A.1b"); add("7.NS.A.1c"); add("7.NS.A.1d"); add("7.NS.A.2a");
      add("7.NS.A.2b"); add("7.NS.A.2c"); add("7.NS.A.2d"); add("7.NS.A.3"); add("7.RP.A.1"); add("7.RP.A.2b");
      add("7.RP.A.2c"); add("7.RP.A.2d"); add("7.RP.A.3"); add("7.SP.B.3"); add("7.SP.B.4"); add("8.EE.A.1");
      add("8.EE.A.2"); add("8.EE.A.3"); add("8.EE.A.4"); add("8.EE.B.5"); add("8.EE.B.6"); add("8.EE.C.7a");
      add("8.EE.C.7b"); add("8.EE.C.8a"); add("8.EE.C.8b"); add("8.EE.C.8c"); add("8.F.A.1"); add("8.F.A.2");
      add("8.F.A.3"); add("8.F.B.4"); add("8.F.B.5"); add("8.G.A.1"); add("8.G.A.2"); add("8.G.A.3"); add("8.G.A.4");
      add("8.G.A.5"); add("8.G.B.6"); add("8.G.B.7"); add("8.G.B.8"); add("8.G.C.9"); add("8.NS.A.1"); add("8.NS.A.2");
      add("8.SP.A.1"); add("8.SP.A.2"); add("8.SP.A.3"); add("8.SP.A.4"); add("HSA-APR.A.1"); add("HSA-CED.A.1");
      add("HSA-CED.A.2"); add("HSA-CED.A.3"); add("HSA-CED.A.4"); add("HSA-REI.A.1"); add("HSA-REI.B.3");
      add("HSA-REI.B.4a"); add("HSA-REI.B.4b"); add("HSA-REI.C.5"); add("HSA-REI.C.6"); add("HSA-REI.C.7");
      add("HSA-REI.D.10"); add("HSA-REI.D.11"); add("HSA-REI.D.12"); add("HSA-SSE.A.1"); add("HSA-SSE.A.2");
      add("HSA-SSE.B.3a"); add("HSA-SSE.B.3b"); add("HSA-SSE.B.3c"); add("HSF-BF.A.1a"); add("HSF-BF.A.1b");
      add("HSF-BF.A.2"); add("HSF-BF.B.3"); add("HSF-BF.B.4a"); add("HSF-IF.A.1"); add("HSF-IF.A.2");
      add("HSF-IF.A.3"); add("HSF-IF.B.4"); add("HSF-IF.B.5"); add("HSF-IF.B.6"); add("HSF-IF.C.7a");
      add("HSF-IF.C.7b"); add("HSF-IF.C.7e"); add("HSF-IF.C.8"); add("HSF-IF.C.8a"); add("HSF-IF.C.8b");
      add("HSF-IF.C.9"); add("HSF-LE.A.1"); add("HSF-LE.A.2"); add("HSF-LE.A.3"); add("HSF-LE.B.5");
      add("HSN-RN.A.1"); add("HSN-RN.A.2"); add("HSN-RN.B.3"); add("HSS-ID.A.1"); add("HSS-ID.A.2");
      add("HSS-ID.B.5"); add("HSS-ID.B.6b"); add("HSS-ID.B.6c"); add("HSS-ID.C.7"); add("HSS-ID.C.8"); add("RI.1.1");
      add("RI.3.1"); add("RL.4.1"); add("RL.4.3"); add("RL.6.9"); add("RL.9-10.1"); add("RL.9-10.2"); add("RL.9-10.4");
      add("RL.9-10.5"); add("W.6.1"); add("W.6.4"); add("W.6.8"); add("W.6.9a"); add("W.6.9b");
    }
  };

  public static final Collection<String> GRADE_LEVELS = new ArrayList<String>() {
    {
      add("01"); add("02"); add("03"); add("04"); add("05"); add("06"); add("07"); add("08"); add("09"); add("10");
      add("11"); add("12");
    }
  };

  public static final Collection<String> BLOOMS_TAXONOMY = new ArrayList<String>() {
    {
      add("Analyzing"); add("Applying"); add("Remembering"); add("Understanding");
    }
  };

  public static final Collection<String> KEY_SKILLS = new ArrayList<String>() {
    {
      add("Analyze"); add("Apply"); add("Arrange"); add("Breakdown"); add("Calculate"); add("Choose"); add("Classify");
      add("Compare"); add("Compute"); add("Define"); add("Demonstrate"); add("Describe"); add("Diagram");
      add("Discuss"); add("Distinguish"); add("Examine"); add("Explain"); add("Express"); add("Give"); add("Identify");
      add("Illustrate"); add("Indicate"); add("Interpret"); add("List"); add("Name"); add("Order"); add("Point-Out");
      add("Predict"); add("Produce"); add("Recall"); add("Recognize"); add("Review"); add("Select"); add("Solve");
      add("Translate"); add("Understand"); add("Write");
    }
  };

  public static final Collection<ItemType> ITEM_TYPES = new ArrayList<ItemType>() {
    {
      add(SHORT_ANSWER); add(MULTI_CHOICE); add(ORDERING); add(PASSAGE_WITH_QUESTIONS_EVIDENCE);
      add(VISUAL_MULTI_CHOICE);
    }
  };

  public static final Collection<String> ITEM_TYPE_STRINGS = new ArrayList<String>() {
    {
      for (ItemType itemType : ITEM_TYPES) {
        add(itemType.getDescription());
      }
    }
  };

  public static final Collection<String> DEMONSTRATED_KNOWLEDGE = new ArrayList<String>() {
    {
      add("Strategic Thinking & Reasoning"); add("Recall & Reproduction"); add("Extended Thinking"); add("None"); add("Skills & Concepts");
    }
  };

  @Test
  public void testSerialization() throws JsonProcessingException {
    FieldValues fieldValues = new FieldValues(CONTRIBUTORS, SUBJECTS, STANDARDS, GRADE_LEVELS, BLOOMS_TAXONOMY,
      KEY_SKILLS, ITEM_TYPE_STRINGS, DEMONSTRATED_KNOWLEDGE);

    ObjectMapper objectMapper = new ObjectMapper();
    assertEquals(
      "{\"bloomsTaxonomy\":[\"Analyzing\",\"Applying\",\"Remembering\",\"Understanding\"],\"depthOfKnowledge\":[\"Strategic Thinking & Reasoning\",\"Recall & Reproduction\",\"Extended Thinking\",\"None\",\"Skills & Concepts\"],\"contributors\":[\"Illustrative Mathematics\",\"New Classrooms Innovation Partners\",\"New England Common Assessment Program\",\"New York State Education Department\",\"Smarter Balanced Assessment Consortium\",\"State of New Jersey Department of Education\",\"TIMSS\"],\"subjects\":[\"English Language Arts\",\"Mathematics\",\"Science\"],\"standards\":[\"1.NBT.B.2a\",\"2.NBT.A.1\",\"2.NBT.A.2\",\"2.OA.A.1\",\"4.G.A.1\",\"4.G.A.2\",\"4.MD.A.3\",\"4.MD.C.5a\",\"4.MD.C.5b\",\"4.MD.C.6\",\"4.NBT.B.4\",\"5.G.A.2\",\"5.G.B.3\",\"5.G.B.4\",\"5.MD.A.1\",\"5.MD.C.3a\",\"5.MD.C.3b\",\"5.MD.C.4\",\"5.MD.C.5a\",\"5.MD.C.5b\",\"5.NBT.A.1\",\"5.NBT.A.2\",\"5.NBT.A.3b\",\"5.NBT.B.5\",\"5.NBT.B.7\",\"5.NF.A.2\",\"5.NF.B.4b\",\"5.NF.B.5a\",\"5.NF.B.5b\",\"5.NF.B.6\",\"6.EE.A.1\",\"6.EE.A.2a\",\"6.EE.A.2b\",\"6.EE.A.2c\",\"6.EE.B.5\",\"6.EE.B.6\",\"6.EE.B.7\",\"6.G.A.1\",\"6.G.A.2\",\"6.G.A.4\",\"6.NS.B.3\",\"6.NS.C.5\",\"6.NS.C.6a\",\"6.NS.C.6b\",\"6.NS.C.6c\",\"6.NS.C.7a\",\"6.NS.C.7b\",\"6.NS.C.8\",\"6.RP.A.2\",\"6.RP.A.3a\",\"6.RP.A.3c\",\"6.SP.A.1\",\"6.SP.A.2\",\"6.SP.A.3\",\"6.SP.B.5a\",\"6.SP.B.5b\",\"6.SP.B.5c\",\"6.SP.B.5d\",\"7.EE.A.1\",\"7.EE.A.2\",\"7.EE.B.3\",\"7.EE.B.4a\",\"7.EE.B.4b\",\"7.G.A.1\",\"7.G.B.4\",\"7.G.B.5\",\"7.G.B.6\",\"7.NS.A.1a\",\"7.NS.A.1b\",\"7.NS.A.1c\",\"7.NS.A.1d\",\"7.NS.A.2a\",\"7.NS.A.2b\",\"7.NS.A.2c\",\"7.NS.A.2d\",\"7.NS.A.3\",\"7.RP.A.1\",\"7.RP.A.2b\",\"7.RP.A.2c\",\"7.RP.A.2d\",\"7.RP.A.3\",\"7.SP.B.3\",\"7.SP.B.4\",\"8.EE.A.1\",\"8.EE.A.2\",\"8.EE.A.3\",\"8.EE.A.4\",\"8.EE.B.5\",\"8.EE.B.6\",\"8.EE.C.7a\",\"8.EE.C.7b\",\"8.EE.C.8a\",\"8.EE.C.8b\",\"8.EE.C.8c\",\"8.F.A.1\",\"8.F.A.2\",\"8.F.A.3\",\"8.F.B.4\",\"8.F.B.5\",\"8.G.A.1\",\"8.G.A.2\",\"8.G.A.3\",\"8.G.A.4\",\"8.G.A.5\",\"8.G.B.6\",\"8.G.B.7\",\"8.G.B.8\",\"8.G.C.9\",\"8.NS.A.1\",\"8.NS.A.2\",\"8.SP.A.1\",\"8.SP.A.2\",\"8.SP.A.3\",\"8.SP.A.4\",\"HSA-APR.A.1\",\"HSA-CED.A.1\",\"HSA-CED.A.2\",\"HSA-CED.A.3\",\"HSA-CED.A.4\",\"HSA-REI.A.1\",\"HSA-REI.B.3\",\"HSA-REI.B.4a\",\"HSA-REI.B.4b\",\"HSA-REI.C.5\",\"HSA-REI.C.6\",\"HSA-REI.C.7\",\"HSA-REI.D.10\",\"HSA-REI.D.11\",\"HSA-REI.D.12\",\"HSA-SSE.A.1\",\"HSA-SSE.A.2\",\"HSA-SSE.B.3a\",\"HSA-SSE.B.3b\",\"HSA-SSE.B.3c\",\"HSF-BF.A.1a\",\"HSF-BF.A.1b\",\"HSF-BF.A.2\",\"HSF-BF.B.3\",\"HSF-BF.B.4a\",\"HSF-IF.A.1\",\"HSF-IF.A.2\",\"HSF-IF.A.3\",\"HSF-IF.B.4\",\"HSF-IF.B.5\",\"HSF-IF.B.6\",\"HSF-IF.C.7a\",\"HSF-IF.C.7b\",\"HSF-IF.C.7e\",\"HSF-IF.C.8\",\"HSF-IF.C.8a\",\"HSF-IF.C.8b\",\"HSF-IF.C.9\",\"HSF-LE.A.1\",\"HSF-LE.A.2\",\"HSF-LE.A.3\",\"HSF-LE.B.5\",\"HSN-RN.A.1\",\"HSN-RN.A.2\",\"HSN-RN.B.3\",\"HSS-ID.A.1\",\"HSS-ID.A.2\",\"HSS-ID.B.5\",\"HSS-ID.B.6b\",\"HSS-ID.B.6c\",\"HSS-ID.C.7\",\"HSS-ID.C.8\",\"RI.1.1\",\"RI.3.1\",\"RL.4.1\",\"RL.4.3\",\"RL.6.9\",\"RL.9-10.1\",\"RL.9-10.2\",\"RL.9-10.4\",\"RL.9-10.5\",\"W.6.1\",\"W.6.4\",\"W.6.8\",\"W.6.9a\",\"W.6.9b\"],\"gradeLevels\":[\"01\",\"02\",\"03\",\"04\",\"05\",\"06\",\"07\",\"08\",\"09\",\"10\",\"11\",\"12\"],\"keySkills\":[\"Analyze\",\"Apply\",\"Arrange\",\"Breakdown\",\"Calculate\",\"Choose\",\"Classify\",\"Compare\",\"Compute\",\"Define\",\"Demonstrate\",\"Describe\",\"Diagram\",\"Discuss\",\"Distinguish\",\"Examine\",\"Explain\",\"Express\",\"Give\",\"Identify\",\"Illustrate\",\"Indicate\",\"Interpret\",\"List\",\"Name\",\"Order\",\"Point-Out\",\"Predict\",\"Produce\",\"Recall\",\"Recognize\",\"Review\",\"Select\",\"Solve\",\"Translate\",\"Understand\",\"Write\"],\"itemTypes\":[\"Constructed Response - Short Answer\",\"Multiple Choice\",\"Ordering\",\"Passage With Questions\",\"Visual Multi Choice\"]}",
      objectMapper.writeValueAsString(fieldValues)
    );
  }

  @Test
  public void testDeserialization() throws IOException {
    String json = "{\"bloomsTaxonomy\":[\"Analyzing\",\"Applying\",\"Remembering\",\"Understanding\"],\"depthOfKnowledge\":[\"Strategic Thinking & Reasoning\",\"Recall & Reproduction\",\"Extended Thinking\",\"None\",\"Skills & Concepts\"],\"contributors\":[\"Illustrative Mathematics\",\"New Classrooms Innovation Partners\",\"New England Common Assessment Program\",\"New York State Education Department\",\"Smarter Balanced Assessment Consortium\",\"State of New Jersey Department of Education\",\"TIMSS\"],\"subjects\":[\"English Language Arts\",\"Mathematics\",\"Science\"],\"standards\":[\"1.NBT.B.2a\",\"2.NBT.A.1\",\"2.NBT.A.2\",\"2.OA.A.1\",\"4.G.A.1\",\"4.G.A.2\",\"4.MD.A.3\",\"4.MD.C.5a\",\"4.MD.C.5b\",\"4.MD.C.6\",\"4.NBT.B.4\",\"5.G.A.2\",\"5.G.B.3\",\"5.G.B.4\",\"5.MD.A.1\",\"5.MD.C.3a\",\"5.MD.C.3b\",\"5.MD.C.4\",\"5.MD.C.5a\",\"5.MD.C.5b\",\"5.NBT.A.1\",\"5.NBT.A.2\",\"5.NBT.A.3b\",\"5.NBT.B.5\",\"5.NBT.B.7\",\"5.NF.A.2\",\"5.NF.B.4b\",\"5.NF.B.5a\",\"5.NF.B.5b\",\"5.NF.B.6\",\"6.EE.A.1\",\"6.EE.A.2a\",\"6.EE.A.2b\",\"6.EE.A.2c\",\"6.EE.B.5\",\"6.EE.B.6\",\"6.EE.B.7\",\"6.G.A.1\",\"6.G.A.2\",\"6.G.A.4\",\"6.NS.B.3\",\"6.NS.C.5\",\"6.NS.C.6a\",\"6.NS.C.6b\",\"6.NS.C.6c\",\"6.NS.C.7a\",\"6.NS.C.7b\",\"6.NS.C.8\",\"6.RP.A.2\",\"6.RP.A.3a\",\"6.RP.A.3c\",\"6.SP.A.1\",\"6.SP.A.2\",\"6.SP.A.3\",\"6.SP.B.5a\",\"6.SP.B.5b\",\"6.SP.B.5c\",\"6.SP.B.5d\",\"7.EE.A.1\",\"7.EE.A.2\",\"7.EE.B.3\",\"7.EE.B.4a\",\"7.EE.B.4b\",\"7.G.A.1\",\"7.G.B.4\",\"7.G.B.5\",\"7.G.B.6\",\"7.NS.A.1a\",\"7.NS.A.1b\",\"7.NS.A.1c\",\"7.NS.A.1d\",\"7.NS.A.2a\",\"7.NS.A.2b\",\"7.NS.A.2c\",\"7.NS.A.2d\",\"7.NS.A.3\",\"7.RP.A.1\",\"7.RP.A.2b\",\"7.RP.A.2c\",\"7.RP.A.2d\",\"7.RP.A.3\",\"7.SP.B.3\",\"7.SP.B.4\",\"8.EE.A.1\",\"8.EE.A.2\",\"8.EE.A.3\",\"8.EE.A.4\",\"8.EE.B.5\",\"8.EE.B.6\",\"8.EE.C.7a\",\"8.EE.C.7b\",\"8.EE.C.8a\",\"8.EE.C.8b\",\"8.EE.C.8c\",\"8.F.A.1\",\"8.F.A.2\",\"8.F.A.3\",\"8.F.B.4\",\"8.F.B.5\",\"8.G.A.1\",\"8.G.A.2\",\"8.G.A.3\",\"8.G.A.4\",\"8.G.A.5\",\"8.G.B.6\",\"8.G.B.7\",\"8.G.B.8\",\"8.G.C.9\",\"8.NS.A.1\",\"8.NS.A.2\",\"8.SP.A.1\",\"8.SP.A.2\",\"8.SP.A.3\",\"8.SP.A.4\",\"HSA-APR.A.1\",\"HSA-CED.A.1\",\"HSA-CED.A.2\",\"HSA-CED.A.3\",\"HSA-CED.A.4\",\"HSA-REI.A.1\",\"HSA-REI.B.3\",\"HSA-REI.B.4a\",\"HSA-REI.B.4b\",\"HSA-REI.C.5\",\"HSA-REI.C.6\",\"HSA-REI.C.7\",\"HSA-REI.D.10\",\"HSA-REI.D.11\",\"HSA-REI.D.12\",\"HSA-SSE.A.1\",\"HSA-SSE.A.2\",\"HSA-SSE.B.3a\",\"HSA-SSE.B.3b\",\"HSA-SSE.B.3c\",\"HSF-BF.A.1a\",\"HSF-BF.A.1b\",\"HSF-BF.A.2\",\"HSF-BF.B.3\",\"HSF-BF.B.4a\",\"HSF-IF.A.1\",\"HSF-IF.A.2\",\"HSF-IF.A.3\",\"HSF-IF.B.4\",\"HSF-IF.B.5\",\"HSF-IF.B.6\",\"HSF-IF.C.7a\",\"HSF-IF.C.7b\",\"HSF-IF.C.7e\",\"HSF-IF.C.8\",\"HSF-IF.C.8a\",\"HSF-IF.C.8b\",\"HSF-IF.C.9\",\"HSF-LE.A.1\",\"HSF-LE.A.2\",\"HSF-LE.A.3\",\"HSF-LE.B.5\",\"HSN-RN.A.1\",\"HSN-RN.A.2\",\"HSN-RN.B.3\",\"HSS-ID.A.1\",\"HSS-ID.A.2\",\"HSS-ID.B.5\",\"HSS-ID.B.6b\",\"HSS-ID.B.6c\",\"HSS-ID.C.7\",\"HSS-ID.C.8\",\"RI.1.1\",\"RI.3.1\",\"RL.4.1\",\"RL.4.3\",\"RL.6.9\",\"RL.9-10.1\",\"RL.9-10.2\",\"RL.9-10.4\",\"RL.9-10.5\",\"W.6.1\",\"W.6.4\",\"W.6.8\",\"W.6.9a\",\"W.6.9b\"],\"gradeLevels\":[\"01\",\"02\",\"03\",\"04\",\"05\",\"06\",\"07\",\"08\",\"09\",\"10\",\"11\",\"12\"],\"keySkills\":[\"Analyze\",\"Apply\",\"Arrange\",\"Breakdown\",\"Calculate\",\"Choose\",\"Classify\",\"Compare\",\"Compute\",\"Define\",\"Demonstrate\",\"Describe\",\"Diagram\",\"Discuss\",\"Distinguish\",\"Examine\",\"Explain\",\"Express\",\"Give\",\"Identify\",\"Illustrate\",\"Indicate\",\"Interpret\",\"List\",\"Name\",\"Order\",\"Point-Out\",\"Predict\",\"Produce\",\"Recall\",\"Recognize\",\"Review\",\"Select\",\"Solve\",\"Translate\",\"Understand\",\"Write\"],\"itemTypes\":[\"Constructed Response - Short Answer\",\"Multiple Choice\",\"Ordering\",\"Passage With Questions\",\"Visual Multi Choice\"]}";
    ObjectMapper objectMapper = new ObjectMapper();
    FieldValues deserialized = objectMapper.readValue(json, FieldValues.class);

    assertTrue(Iterables.elementsEqual(deserialized.getContributors(), CONTRIBUTORS));
    assertTrue(Iterables.elementsEqual(deserialized.getSubjects(), SUBJECTS));
    assertTrue(Iterables.elementsEqual(deserialized.getStandards(), STANDARDS));
    assertTrue(Iterables.elementsEqual(deserialized.getGradeLevels(), GRADE_LEVELS));
    assertTrue(Iterables.elementsEqual(deserialized.getBloomsTaxonomy(), BLOOMS_TAXONOMY));
    assertTrue(Iterables.elementsEqual(deserialized.getKeySkills(), KEY_SKILLS));
    assertTrue(Iterables.elementsEqual(deserialized.getItemTypes(), ITEM_TYPES));
    assertTrue(Iterables.elementsEqual(deserialized.getDepthOfKnowledge(), DEMONSTRATED_KNOWLEDGE));
  }

}
