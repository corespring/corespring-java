## ItemSessions

An [ItemSession](/src/main/java/org/corespring/resource/ItemSession.java) represents an instance of a student responding
to a particular CoreSpring assessment item. It includes
[Settings](/src/main/java/org/corespring/resource/question/Settings.java) which specify how the item is to be rendered,
as well as start and finish times for the student's response to the item.


### CorespringClient methods

#### Create an item session

    CorespringClient client = new CorespringClient(clientId, clientSecret);
    ItemSession itemSession = new ItemSession.Builder().itemId("503c2e91e4b00f3f0a9a7a6a").build()

    client.create(itemSession);

    System.out.println(itemSession.getId());                    // "5278f6c33004fe93e2c34d6f"
    System.out.println(itemSession.getItemId());                // "503c2e91e4b00f3f0a9a7a6a"