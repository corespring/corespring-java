![corespring](doc/images/logo.png)


## corespring-java

Corespring-java is a Java library designed for interfacing with the CoreSpring REST API.

[![Build Status](http://23.92.16.92:8080/buildStatus/icon?job=corespring-java)](http://23.92.16.92:8080/job/corespring-java/)

### Installing

You can find the latest corespring-java jar [here](http://repository.corespring.org/) and include it on your classpath.
Alternatively, CoreSpring provides a public repository with access to different versions of the library.

#### Maven Setup

First, add the CoreSpring repository to your project in the <repositories> section of your pom.xml file:

    <repositories>
      ...
      <repository>
        <id>corespring</id>
        <name>CoreSpring Repository</name>
        <url>http://repository.corespring.org</url>
      </repository>
      ...
    </repositories>

You will also need to add the following dependency to your project:

    <dependency>
      <groupId>org.corespring</groupId>
      <artifactId>corespring-java</artifactId>
      <version>0.1-SNAPSHOT</version>
      <scope>compile</scope>
    </dependency>

Afterwards, the mvn compile task should pull down the corespring-java library and make it available within your project.
Additional information can be found on the [Apache Maven website](http://maven.apache.org/).


### Quick Start

Instantiate the CorespringRestClient by passing in your access token, and use its methods to interact with the platform:

    CorespringRestClient client = new CorespringRestClient("52498773a9c98a782be5b739");

    Collection<Organization> organizations = client.getOrganizations();

    for (Organization organization : organization) {
      System.out.println(organization.getName());           // "Demo Organization"

      Collection<Quiz> quizzes = client.getQuizzes(organization);
      for (Quiz quiz : quizzes) {
        System.out.println(quiz.getTitle());                // "Sample Quiz"
      }
    }


### Resources

Below you will find additional documentation related to the individual resources in the domain model of the CoreSpring
platform:

* Items
* ItemSessions
* [Organizations](/doc/resources/organizations.md)
* [Quizzes](/doc/resources/quizzes.md)
  * [Settings](/doc/resources/settings.md)