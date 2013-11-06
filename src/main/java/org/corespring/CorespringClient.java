package org.corespring;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.corespring.resource.ItemSession;
import org.corespring.resource.Organization;
import org.corespring.resource.Quiz;
import org.corespring.resource.player.Options;
import org.corespring.resource.player.OptionsResponse;
import org.corespring.resource.question.Answer;
import org.corespring.resource.question.Participant;
import org.corespring.rest.CorespringRestClient;
import org.corespring.rest.CorespringRestException;
import org.corespring.rest.CorespringRestResponse;

import java.util.*;

/**
 * {@link CorespringClient} serves as the main interface between the API and the CoreSpring platform. You should
 * instantiate CorespringClient with your client ID and client secret (which you will be issued when you sign up as
 * a developer for the CoreSpring platform):
 *
 *     String clientId = "524c5cb5300401522ab21db1";
 *     String clientSecret = "325hm11xiz7ykeen2ibt";
 *     CorespringClient client = new CorespringClient(clientId, clientSecret);
 *
 * You can then use the client object to interface with the various methods made available.
 */
public class CorespringClient extends CorespringRestClient {

  public CorespringClient(String clientId, String clientSecret) {
    super(clientId, clientSecret);
  }

  public Collection<Organization> getOrganizations() throws CorespringRestException {
    CorespringRestResponse response = get(Organization.getResourceRoute(this));
    return response.getAll(Organization.class);
  }

  public Collection<Quiz> getQuizzes(Organization organization) throws CorespringRestException {
    NameValuePair organizationId = new BasicNameValuePair("organization_id", organization.getId());
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(organizationId);

    CorespringRestResponse response = get(Quiz.getResourcesRoute(this), params);
    return response.getAll(Quiz.class);
  }

  public Quiz getQuizById(String id) throws CorespringRestException {
    CorespringRestResponse response = get(Quiz.getResourceRoute(this, id));
    return response.get(Quiz.class);
  }

  public Quiz create(Quiz quiz) throws CorespringRestException {
    CorespringRestResponse response = post(Quiz.getResourcesRoute(this), quiz);
    return response.get(Quiz.class);
  }

  public Quiz addParticipant(Quiz quiz, String externalUid) throws CorespringRestException {
    Collection<String> externalUids = new ArrayList<String>();
    externalUids.add(externalUid);
    return addParticipants(quiz, externalUids);
  }

  public Quiz addParticipants(Quiz quiz, Collection<String> externalUids) throws CorespringRestException {
    Map<String, Collection<String>> wrappedExternalUids = new HashMap<String, Collection<String>>();
    wrappedExternalUids.put("ids", externalUids);
    return put(quiz.getParticipantsRoute(this), wrappedExternalUids).get(Quiz.class);
  }

  /**
   * Adds an {@link Answer} to a {@link Quiz} for a provided external user id.
   */
  public Quiz addAnswer(Quiz quiz, Answer answer, String externalUid) throws CorespringRestException {
    return put(Answer.getAddAnswerRoute(this, quiz, externalUid), answer).get(Quiz.class);
  }

  public Quiz update(Quiz quiz) throws CorespringRestException {
    CorespringRestResponse response = put(Quiz.getResourceRoute(this, quiz.getId()), quiz);
    return response.get(Quiz.class);
  }

  public Quiz delete(Quiz quiz) throws CorespringRestException {
    delete(Quiz.getResourceRoute(this, quiz.getId()));
    return null;
  }

  public ItemSession create(ItemSession itemSession) throws CorespringRestException {
    CorespringRestResponse response = post(itemSession.getResourcesRoute(this), itemSession);
    return response.get(ItemSession.class);
  }

  public OptionsResponse encryptOptions(Options options) throws CorespringRestException {
    CorespringRestResponse response = post(Options.getEncryptionRoute(this), options);
    OptionsResponse optionsResponse = response.get(OptionsResponse.class);
    return optionsResponse;
  }

}
