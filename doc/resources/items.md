## Items

An [Item](/src/main/java/org/corespring/resource/Item.java) represents an assessment item within the Corespring
platform. An item includes the pertinent metadata information related to the item, which can be subsequently accessed
through URLs based on the id.

### ItemQuery

An [ItemQuery](/src/main/java/org/corespring/rest/ItemQuery.java) is a query object which is passed to the CoreSpring API in
order to return a subset of available items. An ItemQuery can be built as follows:

    ItemQuery itemQuery = new ItemQuery.Builder()
        .bloomsTaxonomy("Remembering")
        .contributor("Corespring Assessment Professionals")
        .demonstratedKnowledge("Factual")
        .gradeLevel("04")
        .itemType("Multiple Choice")
        .keySkill("Categorize")
        .subject("Mathematics")
        .standard("RL.K.2")
        .collection("4ff2e4cae4b077b9e31689fd")
        .build();


### CorespringClient methods

#### Find items

    Collection<Item> items = client.findItems(itemQuery);

    for (Item item : items) {
      System.out.println(item.getTitle());
    }

#### Count items

    int itemCount = client.countItems(itemQuery);
    System.out.println(itemCount);
