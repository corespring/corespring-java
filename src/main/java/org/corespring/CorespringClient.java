package org.corespring;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.corespring.resource.Organization;
import org.corespring.resource.Quiz;
import org.corespring.resource.player.Options;
import org.corespring.resource.player.OptionsResponse;
import org.corespring.rest.CorespringRestClient;
import org.corespring.rest.CorespringRestException;
import org.corespring.rest.CorespringRestResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

  public Quiz update(Quiz quiz) throws CorespringRestException {
    CorespringRestResponse response = put(Quiz.getResourceRoute(this, quiz.getId()), quiz);
    return response.get(Quiz.class);
  }

  public Quiz delete(Quiz quiz) throws CorespringRestException {
    CorespringRestResponse response = delete(Quiz.getResourceRoute(this, quiz.getId()), quiz);
    return null;
  }

  public OptionsResponse encryptOptions(Options options) throws CorespringRestException {
    CorespringRestResponse response = post(Options.getEncryptionRoute(this), options);
    OptionsResponse optionsResponse = response.get(OptionsResponse.class);
    return optionsResponse;
  }

}
