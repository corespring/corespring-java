## Options

[Options](/src/main/java/org/corespring/resource/player/Options.java) describe the permissions for rendering the
CoreSpring item player. More information about how options are used to render the CoreSpring item player is available in
the [CoreSpring documentation](http://www.corespring.org/developer/home#the-corespring-item-player).


### Mode

A [mode](/src/main/java/org/corespring/resource/player/Mode.java) is an enumerated value describing the various modes
under which the CoreSpring item player can operate. You can find a detailed description of each mode in the
[CoreSpring documentation](http://www.corespring.org/developer/home#player-modes).


#### Role

A [role](/src/main/java/org/corespring/resource/player/Role.java) is an enumerated value describing the roles of users
who are allowed to view the item. Available values are ALL, STUDENT, and INSTRUCTOR.

### CorespringClient methods

#### Encrypt options

    CorespringClient client = new CorespringClient(clientId, clientSecret);

    Options options = new Options.Builder().itemId("*").sessionId("*").mode(Mode.ALL).role(Role.ALL).expiresNever()
        .build();

    String encryptedOptions = client.encryptOptions(options);
    System.out.println(encryptedOptions);
    // a369de25bf73cf3479dbcfc76f8c1b4ad983f777fe4834c33e3e57c98d86d31fe9e46bc606a8e3b61c5f2c1b935a6725e9e3cf227f558d3724895ef84ce43107645baf8f53dd068eafc9759b63b1ad44--b4cee74cb43af0d6b652bb044e9f464d