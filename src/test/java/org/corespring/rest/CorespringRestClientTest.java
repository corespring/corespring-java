package org.corespring.rest;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.corespring.resource.Organization;
import org.corespring.resource.Question;
import org.corespring.resource.Quiz;
import org.corespring.resource.question.Participant;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class CorespringRestClientTest {

  private String clientId = "524c5cb5300401522ab21db1";
  private String clientSecret = "325hm11xiz7ykeen2ibt";

  private final Organization organization =
      new Organization("51114b307fc1eaa866444648", "Demo Organization", new ArrayList<String>(), false);

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(8089);

  @Test
  public void testGetOrganizations() {
    CorespringRestClient client = new CorespringRestClient(clientId, clientSecret);
    client.setEndpoint("http://localhost:8089");

    Collection<Organization> organizations = client.getOrganizations();

    assertEquals(1, organizations.size());
    Organization organization = organizations.iterator().next();

    assertEquals("Demo Organization", organization.getName());
    assertEquals("51114b307fc1eaa866444648", organization.getId());
  }

  @Test
  public void testGetQuizzes() {

    CorespringRestClient client = new CorespringRestClient(clientId, clientSecret);
    client.setEndpoint("http://localhost:8089");

    Collection<Quiz> quizzes = client.getQuizzes(organization);
    for (Quiz quiz : quizzes) {
      checkQuiz(quiz);
    }
  }

  @Test
  public void testGetQuizById() {
    CorespringRestClient client = new CorespringRestClient(clientId, clientSecret);
    client.setEndpoint("http://localhost:8089");

    Quiz quiz = client.getQuizById("000000000000000000000002");
    checkQuiz(quiz);
  }

  private void checkQuiz(Quiz quiz) {
    if (!quiz.getQuestions().isEmpty()) {
      Collection<Question> questions = quiz.getQuestions();
      for (Question question : questions) {
        assertNotNull(question.getTitle());
        assertNotNull(question.getItemId());
        assertNotNull(question.getSettings());
        assertNotNull(question.getStandards());
      }

      Collection<Participant> participants = quiz.getParticipants();
      for (Participant participant : participants) {
        assertNotNull(participant.getAnswers());
        assertNotNull(participant.getExternalUid());
      }
    }
  }

  @Test
  public void testCreateQuiz() {
    Quiz quiz = new Quiz.Builder().title("My new quiz!").build();
    CorespringRestClient client = new CorespringRestClient(clientId, clientSecret);
    client.setEndpoint("http://localhost:8089");
    Quiz updatedQuiz = client.create(quiz);

    // Created quiz has id and orgId
    assertNotNull(updatedQuiz.getId());
    assertNotNull(updatedQuiz.getOrgId());
  }

  @Test
  public void testUpdateQuiz() {
    Quiz quiz = new Quiz.Builder().title("My new quiz!").build();
    CorespringRestClient client = new CorespringRestClient(clientId, clientSecret);
    client.setEndpoint("http://localhost:8089");

    quiz = client.create(quiz);
    quiz = client.update(new Quiz.Builder(quiz).course("English 101").build());

    assertEquals("English 101", quiz.getCourse());
  }

  @Test
  public void testDeleteQuiz() {
    Quiz quiz = new Quiz.Builder().title("My new quiz!").build();
    CorespringRestClient client = new CorespringRestClient(clientId, clientSecret);
    client.setEndpoint("http://localhost:8089");

    quiz = client.create(quiz);
    assertNotNull(quiz);
    quiz = client.delete(quiz);

    assertNull(quiz);
  }

}
