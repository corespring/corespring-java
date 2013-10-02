![corespring](doc/images/logo.png)
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

##### List organizations

    Collection<Organization> organizations = client.getOrganizations();
    for (Organization organization : organizations) {
      System.out.println(organization.getName);         // "Demo Organization"
      System.out.println(organization.getId);           // "51114b307fc1eaa866444648"
    }

#### Quizzes

##### List quizzes for an organization

    Collection<Quiz> quizzes = client.getQuizzes(organization);
    for (Quiz quiz : quizzes) {
      System.out.println(quiz.getTitle());              // "Sample Quiz"
      System.out.println(quiz.getCourse());             // "Challenge Course"
    }

##### Retrieve a quiz by id

    Quiz quiz = client.getQuiz("000000000000000000000002");
    System.out.println(quiz.getTitle());                // "Sample Quiz"
    System.out.println(quiz.getCourse());               // "Challenge Course"


##### Create a quiz

Create a quiz by using the Quiz.Builder class, and persist to CoreSpring using the cilent's create method. Note that the
result from the create method will return a new Quiz object with data from the server's response.

    Quiz quiz = new Quiz.Bulider().title("My Quiz");

    quiz = client.create(quiz);
    System.out.println(quiz.getId());                   // "524c0aa9300401522ab21da3"
    System.out.println(quiz.getOrgId());                // "51114b307fc1eaa866444648"
