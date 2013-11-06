## Participants

A [Participant](/src/main/java/org/corespring/resource/question/Participant.java) defines a student by an external user
id, as well as a set of [answers](/doc/resources/answers.md) to items within a [quiz](/doc/resources/quizzes.md). A
participant's answers are unique by item id (i.e., the Builder for Participant will not allow duplicate itemIds for
Answers, and will override existing Answers with matching itemId).

    Participant participant = new Participant.Builder()
        .externalUid("ben1234")
        .answer(new Answer.Builder().itemId("527a3bbe8808335f66e168a5").sessionId("527a3bca8808335f66e168a6").build())
        .build();
