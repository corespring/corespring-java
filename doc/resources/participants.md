## Participants

A [Participant](/src/main/java/org/corespring/resource/question/Participant.java) defines a student by an external user
id, as well as a set of [answers](/doc/resources/answers.md) to items within a [quiz](/doc/resources/quizzes.md). A
participant's answers are unique by item id (i.e., the Builder for Participant will not allow duplicate itemIds for
Answers, and will override existing Answers with matching itemId). Participants are not normally instantiated directly,
and should be added to a Quiz using the addParticipant method of CorespringClient.

### CorespringClient methods

#### Add participant

    Quiz quiz = client.create(new Quiz.Builder().title("My quiz!").build());

    // where "ben1234" is the 3rd part user token for the participant
    quiz = client.addParticipant(quiz, "ben1234");

    Participant participant = quiz.getParticipant("ben1234");
    System.out.println(participant.getExternalUid());                   // "ben1234"
