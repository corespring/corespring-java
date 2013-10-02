## Settings

A CoreSpring [Settings](/src/main/java/org/corespring/resource/question/Settings.java) resource contains information
about how an item should be rendered. An example below shows the information contained by a Settings object:

    Settings settings = new Settings.builder()
        .maxNumberOfAttempts(3)
        .highlightUserResponse(true)
        .highlightCorrectResponse(true)
        .showFeedback(true)
        .allowEmptyResponses(true)
        .submitCompleteMessage("Ok! Your response was submitted.")
        .submitIncompleteMessage("You may revise your work before you submit your final response.")
        .submitIncorrectMessage("You may revise your work before you submit your final response.")
        .build();


The Settings object also provides a .standard convenience method for building the most common set of settings:

    Settings settings = Settings.standard();