## Assessments

A CoreSpring [Assessment](/src/main/java/org/corespring/resource/Assessment.java) resource is made up of a set of
metadata, a collection of questions, and a set of participants.

A typical assessment might be represented by the following JSON:

    {
      "id": "000000000000000000000002",
      "orgId": "51114b307fc1eaa866444648",
      "metadata": {
        "title": "Sample Assessment",
        "description": "This is a sample assessment",
        "instructions": "This consists of questions to help users get up to speed using the CoreSpring platform",
        "classroom": "1034"
      },
      "questions": [
        {
          "itemId": "503c2e91e4b00f3f0a9a7a6a",
          "settings": {
            "maxNoOfAttempts": 1,
            "highlightUserResponse": true,
            "highlightCorrectResponse": true,
            "showFeedback": false,
            "allowEmptyResponses": false,
            "submitCompleteMessage": "Ok! Your response was submitted.",
            "submitIncorrectMessage": "You may revise your work before you submit your final response."
          },
          "title": "How many pancakes were eaten when Rodrigo made breakfast for his family and friends?",
          "standards": [
            "2.0A.A.1"
          ]
        }
      ],
      "participants": [
        {
          "answers": [
            {
              "sessionId": "515425f33004e34080967bbb",
              "itemId": "503c2e91e4b00f3f0a9a7a6a"
            }
          ],
          "externalUid": "my-platform-student-53234"
        }
      ]
    }

### Metadata

Metadata consists of key-value pairs of data associated with an Assessment. The default fields for Metadata are title,
description, and instructions. These can be added using the Assessment.Builder helper methods:

    Assessment assessment = new Assessment.Builder()
        .title("Corepring Sample Assessment")
        .description("This is a sample assessment")
        .instructions("This consists of questions to help users get up to speed using the CoreSpring platform")
        .start(today)
        .end(tomorrow)
        .build();

You can also add arbitrary metadata using the addMetadata method:

    Assessment assessment = new Assessment.Builder()
       .addMetadata("classroom", "1034")
       .build();

    System.out.println(assessment.getMetadataValue("classroom"));             // "1034"

#### Start/End/isActive

Assessment objects contain special metadata for the start and end of assessments. If you're constructing an Assessment
using the builder, and you would like to define a time frame for the assessment, you must ensure that the following
conditions are met:

  1. The start comes before the end.
  2. Both start and end are defined.

Failure to meet either of these conditions will result in exceptions being raised.

There is also a convenience method isActive to determine whether an assessment is currently active. Assessments without
a defined time range are active by default. For example:

    System.out.println(new Assessment.Builder().build().isActive(today));                                     // true
    System.out.println(new Assessment.Builder().start(yesterday).end(tomorrow).build().isActive());           // true
    System.out.println(new Assessment.Builder().start(yesterday).end(tomorrow).build().isActive(today));      // true
    System.out.println(new Assessment.Builder().start(today).end(tomorrow).build().isActive(yesterday));      // false
    System.out.println(new Assessment.Builder().start(yesterday).end(today).build().isActive(tomorrow));      // false
    

### Questions

Questions consist fo an id for a CoreSpring Items, a set of rendering [settings](/doc/resources/settings.md), a title,
and a list of standards that the question maps to. Assessments also contain metadata (title, course, and a note).


### Participants

[Participants](/doc/resources/participants.md) in Assessment objects are unique by their externalUid. If you specify a
Participant with the same externalUid as an existing Participant, it will be overridden with the new value. For example:

    String sharedExternalUid = "rjelcjdi4";
    Participant participant = new Participant.Builder().externalUid(sharedExternalUid).build();
    Participant anotherParticipant = new Participant.Builder().externalUid(sharedExternalUid).build();

    Assessment assessment = new Assessment.Builder().participant(participant).participant(anotherParticipant).build();

    System.out.println(assessment.getParticipants().size());                                          // 1
    System.out.println(assessment.getParticipants().iterator().next().equals(participant);            // false
    System.out.println(assessment.getParticipants().iterator().next().equals(anotherParticipant);     // true


### CorespringClient methods

#### List assessments for an organization

    Collection<Assessment> assessments = client.getAssessments(organization);
    for (Assessment assessment : assessments) {
      System.out.println(assessment.getTitle());              // "Sample Assessment"
      System.out.println(assessment.getCourse());             // "Challenge Course"
    }


#### Retrieve an assessment by id

    Assessment assessment = client.getAssessment("000000000000000000000002");
    System.out.println(assessment.getTitle());                // "Sample Assessment"
    System.out.println(assessment.getCourse());               // "Challenge Course"


#### Retrieve assessments by author id

    Collection<Assessment> assessments = client.getAssessmentsByAuthor("fd707fc3c");
    System.out.println(assessment.next().getTitle();                 // "Sample Assessment"


#### Create an assessment

Create an assessment by using the Assessment.Builder class, and persist to CoreSpring using the cilent's create method.
Note that the result from the create method will return a new Assessment object with data from the server's response.

    Assessment assessment = new Assessment.Bulider().title("My Assessment");

    assessment = client.create(assessment);
    System.out.println(assessment.getId());                   // "524c0aa9300401522ab21da3"
    System.out.println(assessment.getOrgId());                // "51114b307fc1eaa866444648"


#### Update an assessment

Because Assessment objects are immutable, you should instantiate a new Assessment.Builder with an Assessment, modify
the builder, and then build the assessment. This result can then be sent to the client's update method:

    Assessment assessment = client.getAssessment("000000000000000000000002");
    System.out.println(assessment.getTitle());                // "Sample Assessment"
    assessment = client.update(new Assessment.Builder().title("My new title").build());
    System.out.println(assessment.getTitle());                // "My new title"


#### Delete an assessment

    Assessment assessment = client.getAssessment("000000000000000000000002");
    System.out.println(assessment.getTitle());                // "Sample Assessment"
    assessment = client.delete(assessment);
    System.out.println(assessment);                           // null