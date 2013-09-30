## corespring-java
[![Build Status](http://23.92.16.92:8080/buildStatus/icon?job=corespring-java)](http://23.92.16.92:8080/job/corespring-java/)

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

#### Quizzes

List quizzes for an organization:

    Collection<Quiz> quizzes = client.getQuizzes(organization);
    for (Quiz quiz : quizzes) {
      System.out.println(quiz.getTitle());              // "Sample Quiz"
      System.out.println(quiz.getCourse());             // "Challenge Course"
    }

