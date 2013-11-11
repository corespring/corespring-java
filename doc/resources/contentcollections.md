## ContentCollections

A [ContentCollection](/src/main/java/org/corespring/resource/ContentCollection.java) represents a set of
[items](/doc/resources/items.md) within the CoreSpring platform. You can access the items associated with a
content collection by passing a content collection's id into an [ItemQuery](/doc/resources/items.md#itemquery) object
and using [CorespringClient](/src/main/java/org/corespring/CorespringClient.java)'s findItems method.


### CorespringClient methods

#### Get collections

    Collection<ContentCollection> collections = client.getCollections();
    for (ContentCollection collection : collections) {
      System.out.println(collection.getName());             // "CoreSpring ELA (Public)"
    }                                                       // "CoreSpring Mathematics (Public)"
                                                            // "Beta Items (Public)"
                                                            // "Demo Collection"