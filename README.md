## corespring-java

### Installing

Use the following dependency in your project:

    <dependency>
      <groupId>org.corespring</groupId>
      <artifactId>corespring-java</artifactId>
      <version>0.1-SNAPSHOT</version>
      <scope>compile</scope>
    </dependency>

### Examples

Instantiate the CorespringRestClient by passing in your access token.

    CorespringRestClient client = new CorespringRestClient("52498773a9c98a782be5b739");

#### Organizations

    Collection<Organization> organizations = client.getOrganizations();
    for (Organization organization : organizations) {
      System.out.println(organization.getName);         // "Demo Organization"
      System.out.println(organization.getId);           // "51114b307fc1eaa866444648"
    }
