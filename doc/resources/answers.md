## Answers

An [Answer](/src/main/java/org/corespring/resource/question/Answer.java) corresponds to a student's response to an item.
At the minimum, an answer must contain an itemId value and a sessionId value.

### CorespringClient methods

#### Add answer to quiz

    Quiz quiz = new Quiz.Builder().title("My new quiz!").build();

    quiz = client.create(quiz);
    quiz = client.addParticipant(quiz, externalUid);

    Answer answer = new Answer.Builder().itemId(itemId).sessionId(sessionId).build();
    quiz = client.addAnswer(quiz, answer, externalUid);