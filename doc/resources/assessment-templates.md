## Assessments

An [AssessmentTemplate](/src/main/java/org/corespring/resource/AssessmentTemplate.java) contains many of the same
attributes as an [Assessment](/doc/resources/assessments.md), the key difference being that an AssessmentTemplate does
not contain any of the details relevant to a single administration of an Assessment. For example, an
AssessmentTemplate does not include the details about particular students' responses, when an Assessment starts, or
when it ends. It is meant to be used, literally, as a template of the item content from which to build an
Assessment.

### Creating an Assessment from an AssessmentTemplate

The purpose of an [AssessmentTemplate](/src/main/java/org/corespring/resource/AssessmentTemplate.java) is to create
other [Assessment](/doc/resources/assessments.md)s. This can be done using the static `fromTemplate` method on the
Assessment class:

    AssessmentTemplate assessmentTemplate = client.getAssessmentTemplates().iterator().next();
    Assessment assessmentFromTemplate = Assessment.fromTemplate(assessmentTemplate);


You can also use the `Assessment.Builder` class to use an AssessmentTemplate as the base from which to build a new
Assessment:

    String sharedExternalUid = "rjelcjdi4";
    Participant participant = new Participant.Builder().externalUid(sharedExternalUid).build();

    AssessmentTemplate assessmentTemplate = client.getAssessmentTemplates().iterator().next();
    Assessment assessment = new Assessment.Builder(assessmentTemplate).participant(participant).build();


### CorespringClient methods

#### Create an assessment

Create an assessment template by using the `AssessmentTemplate.Builder` class, and persist to CoreSpring using the
cilent's create method. Note that the result from the create method will return a new `AssessmentTemplate` object with
data from the server's response.

    Assessment assessmentTemplate =
      new AssessmentTemplate.Builder()
        .collectionId("51114b127fc1eaa866444647")
        .question(new Question.Builder().itemId("527a3bbe8808335f66e168a5").build())
        .build();

    assessmentTemplate = client.create(assessmentTemplate);
    System.out.println(assessment.getId());                   // "530bb914245b02301a118c2f"
    System.out.println(assessment.getCollectionId());         // "51114b127fc1eaa866444647"


#### List assessment templates

    Collection<AssessmentTemplate> assessmentTemplates = client.getAssessmentTemplates();
    for (AssessmentTemplate assessmentTemplate : assessmentTemplates) {
      System.out.println(assessment.getId());                 // "530b91797d845ec21ac7195c"
      System.out.println(assessment.getCollectionId());       // "51114b127fc1eaa866444647"
    }