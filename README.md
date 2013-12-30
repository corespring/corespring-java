![corespring](doc/images/logo.png)


## corespring-java

Corespring-java is a Java library designed for interfacing with the CoreSpring REST API.

[![Build Status](https://travis-ci.org/corespring/corespring-java.png)](https://travis-ci.org/corespring/corespring-java)

### Installing

You can find the latest corespring-java jar [here](https://github.com/corespring/corespring-java/raw/releases/org/corespring/corespring-java/0.3.22/corespring-java-0.3.22.jar) and include it on your classpath.
Alternatively, CoreSpring provides a public repository with access to different versions of the library.

#### Maven Setup

First, add the CoreSpring repository to your project in the <repositories> section of your pom.xml file:

    <repositories>
      ...
      <repository>
        <id>corespring</id>
        <name>CoreSpring Java Repository</name>
        <url>https://raw.github.com/corespring/corespring-java/releases</url>
      </repository>
      ...
    </repositories>

You will also need to add the following dependency to your project:

    <dependency>
      <groupId>org.corespring</groupId>
      <artifactId>corespring-java</artifactId>
      <version>0.3.22</version>
      <scope>compile</scope>
    </dependency>

Afterwards, the mvn compile task should pull down the corespring-java library and make it available within your project.
Additional information can be found on the [Apache Maven website](http://maven.apache.org/).


### Quick Start

Instantiate the CorespringClient by passing in your client ID and client secret (you will receive these when you
register for the CoreSpring platform), and use its methods to interact with the platform:

    String clientId = "524c5cb5300401522ab21db1";
    String clientSecret = "325hm11xiz7ykeen2ibt";

    CorespringClient client = new CorespringClient(clientId, clientSecret);

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

* [ContentCollections](/doc/resources/contentcollections.md)
* [Items](/doc/resources/items.md)
* [ItemSessions](/doc/resources/itemsessions.md)
* [Options](/doc/resources/options.md)
* [Organizations](/doc/resources/organizations.md)
* [Quizzes](/doc/resources/quizzes.md)
  * [Settings](/doc/resources/settings.md)
  * [Participants](/doc/resources/participants.md)
    * [Answers](/doc/resources/answers.md)

## License

This library is distributed under the MIT License found in the provided [LICENSE](/LICENSE) file.
