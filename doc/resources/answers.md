## Answers

An [Answer](/src/main/java/org/corespring/resource/question/Answer.java) corresponds to a student's response to an item.
At the minimum, an answer must contain an itemId value and a sessionId value.

### CorespringClient methods

#### Add answer to assessment

    Assessment assessment = new Assessment.Builder().title("My new assessment!").build();

    assessment = client.create(assessment);
    assessment = client.addParticipant(assessment, externalUid);

    Answer answer = new Answer.Builder().itemId(itemId).sessionId(sessionId).build();
    assessment = client.addAnswer(assessment, answer, externalUid);