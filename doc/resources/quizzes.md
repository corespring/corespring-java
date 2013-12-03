## Quizzes

A CoreSpring [Quiz](/src/main/java/org/corespring/resource/Quiz.java) resource is made up of a set of metadata, a
collection of questions, and a set of participants.

A typical quiz might be represented by the following JSON:

    {
      "id": "000000000000000000000002",
      "orgId": "51114b307fc1eaa866444648",
      "metadata": {
        "title": "Sample Quiz",
        "description": "This is a sample quiz",
        "instructions": "This quiz consists of questions to help users get up to speed using the CoreSpring platform",
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

Metadata consists of key-value pairs of data associated with a Quiz. The default fields for Metadata are title,
description, and instructions. These can be added using the Quiz.Builder helper methods:

    Quiz quiz = new Quiz.Builder()
        .title("Corepring Sample Quiz")
        .description("This is a sample quiz")
        .instructions("This quiz consists of questions to help users get up to speed using the CoreSpring platform")
        .start(today)
        .end(tomorrow)
        .build();

You can also add arbitrary metadata using the addMetadata method:

    Quiz quiz = new Quiz.Builder()
       .addMetadata("classroom", "1034")
       .build();

    System.out.println(quiz.getMetadataValue("classroom"));             // "1034"

#### Start/End/isActive

Quiz objects contain special metadata for the start and end of quizzes. If you're constructing a Quiz using the builder,
and you would like to define a time frame for the quiz, you must ensure that the following conditions are met:

  1. The start comes before the end.
  2. Both start and end are defined.

Failure to meet either of these conditions will result in exceptions being raised.

There is also a convenience method isActive to determine whether a quiz is currently active. Quizzes without a defined
time range are active by default. For example:

    System.out.println(new Quiz.Builder().build().isActive(today));                                     // true
    System.out.println(new Quiz.Builder().start(yesterday).end(tomorrow).build().isActive());           // true
    System.out.println(new Quiz.Builder().start(yesterday).end(tomorrow).build().isActive(today));      // true
    System.out.println(new Quiz.Builder().start(today).end(tomorrow).build().isActive(yesterday));      // false
    System.out.println(new Quiz.Builder().start(yesterday).end(today).build().isActive(tomorrow));      // false
    

### Questions

Questions consist fo an id for a CoreSpring Items, a set of rendering [settings](/doc/resources/settings.md), a title,
and a list of standards that the question maps to. Quizzes also contain metadata (title, course, and a note).


### Participants

[Participants](/doc/resources/participants.md) in Quiz objects are unique by their externalUid. If you specify a
Participant with the same externalUid as an existing Participant, it will be overridden with the new value. For example:

    String sharedExternalUid = "rjelcjdi4";
    Participant participant = new Participant.Builder().externalUid(sharedExternalUid).build();
    Participant anotherParticipant = new Participant.Builder().externalUid(sharedExternalUid).build();

    Quiz quiz = new Quiz.Builder().participant(participant).participant(anotherParticipant).build();

    System.out.println(quiz.getParticipants().size());                                          // 1
    System.out.println(quiz.getParticipants().iterator().next().equals(participant);            // false
    System.out.println(quiz.getParticipants().iterator().next().equals(anotherParticipant);     // true


### CorespringClient methods

#### List quizzes for an organization

    Collection<Quiz> quizzes = client.getQuizzes(organization);
    for (Quiz quiz : quizzes) {
      System.out.println(quiz.getTitle());              // "Sample Quiz"
      System.out.println(quiz.getCourse());             // "Challenge Course"
    }


#### Retrieve a quiz by id

    Quiz quiz = client.getQuiz("000000000000000000000002");
    System.out.println(quiz.getTitle());                // "Sample Quiz"
    System.out.println(quiz.getCourse());               // "Challenge Course"


#### Retrieve quizzes by author id

    Quizzes quizzes = client.getQuizzesByAuthor("fd707fc3c");
    System.out.println(quiz.getTitle();                 // "Sample Quiz"


#### Create a quiz

Create a quiz by using the Quiz.Builder class, and persist to CoreSpring using the cilent's create method. Note that the
result from the create method will return a new Quiz object with data from the server's response.

    Quiz quiz = new Quiz.Bulider().title("My Quiz");

    quiz = client.create(quiz);
    System.out.println(quiz.getId());                   // "524c0aa9300401522ab21da3"
    System.out.println(quiz.getOrgId());                // "51114b307fc1eaa866444648"


#### Update a quiz

Because Quiz objects are immutable, you should instantiate a new Quiz.Builder with a Quiz, modify the builder, and then
build the quiz. This result can then be sent to the client's update method:

    Quiz quiz = client.getQuiz("000000000000000000000002");
    System.out.println(quiz.getTitle());                // "Sample Quiz"
    quiz = client.update(new Quiz.Builder().title("My new title").build());
    System.out.println(quiz.getTitle());                // "My new title"


#### Delete a quiz

    Quiz quiz = client.getQuiz("000000000000000000000002");
    System.out.println(quiz.getTitle());                // "Sample Quiz"
    quiz = client.delete(quiz);
    System.out.println(quiz);                           // null