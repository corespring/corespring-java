package org.corespring;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bson.types.ObjectId;
import org.corespring.resource.Organization;
import org.corespring.resource.Quiz;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CorespringRestClient {

  /** Version of the API to target */
  public static final String API_VESRION = "v1";

  /** Version of corespring-java client */
  public static final String VERSION = "1.0-SNAPSHOT";

  /** Default timeout in milliseconds */
  private static final int CONNECTION_TIMEOUT = 10000;

  /** The endpoint. */
  private String endpoint = "http://localhost:9000/api";

  private final String accessToken;

  private HttpClient httpClient;

  public CorespringRestClient(String accessToken) {
    validateAccessToken(accessToken);
    this.accessToken = accessToken;

    ThreadSafeClientConnManager clientConnectionManager = new ThreadSafeClientConnManager();
    clientConnectionManager.setDefaultMaxPerRoute(10);
    this.httpClient = new DefaultHttpClient(clientConnectionManager);

    httpClient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
    httpClient.getParams().setParameter("http.socket.timeout", new Integer(CONNECTION_TIMEOUT));
    httpClient.getParams().setParameter("http.connection.timeout", new Integer(CONNECTION_TIMEOUT));
    httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
  }

  public Collection<Organization> getOrganizations() {
    CorespringRestResponse response = get(Organization.getResourceRoute(this), "GET");
    Collection<Map<String, Object>> results = response.toCollection();

    Collection<Organization> organizations = new ArrayList<Organization>();
    for (Map<String, Object> map : results) {
      organizations.add(Organization.fromObjectMap(map));
    }
    return organizations;
  }

  public Collection<Quiz> getQuizzes(Organization organization) {
    NameValuePair organizationId = new BasicNameValuePair("organization_id", organization.getId());
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(organizationId);

    CorespringRestResponse response = get(Quiz.getResourcesRoute(this), "GET", params);
    Collection<Map<String, Object>> results = response.toCollection();

    Collection<Quiz> quizzes = new ArrayList<Quiz>();
    for (Map<String, Object> map : results) {
      quizzes.add(Quiz.fromObjectMap(map));
    }
    return quizzes;
  }

  public Quiz getQuizById(String id) {
    CorespringRestResponse response = get(Quiz.getResourceRoute(this, id), "GET");
    Map<String, Object> result = response.toMap();
    return Quiz.fromObjectMap(result);
  }

  public StringBuilder baseUrl() {
    return new StringBuilder(this.getEndpoint()).append("/").append(CorespringRestClient.API_VESRION).append("/");
  }

  private void validateAccessToken(String accessToken) {
    if (!accessToken.equals("demo_token") && !ObjectId.isValid(accessToken)) {
      throw new IllegalArgumentException(
          new StringBuilder("Access token ").append(accessToken).append(" is not valid").toString());
    }
  }

  public CorespringRestResponse get(String path, String method) {
    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
    return get(path, method, paramList);
  }

  private CorespringRestResponse get(String path, String method, List<NameValuePair> paramList) {
    paramList.add(new BasicNameValuePair("access_token", this.accessToken));
    HttpUriRequest request = setupRequest(path, method, paramList);

    HttpResponse response;
    try {
      response = httpClient.execute(request);
      HttpEntity entity = response.getEntity();

      Header[] contentTypeHeaders = response.getHeaders("Content-Type");
      String responseBody = "";

      if (entity != null) {
        responseBody = EntityUtils.toString(entity);
      }

      StatusLine status = response.getStatusLine();
      int statusCode = status.getStatusCode();

      CorespringRestResponse restResponse =
          new CorespringRestResponse(request.getURI().toString(), responseBody, statusCode);

      return restResponse;

    } catch (ClientProtocolException e1) {
      throw new RuntimeException(e1);
    } catch (IOException e1) {
      throw new RuntimeException(e1);
    }
  }

  private HttpUriRequest setupRequest(String path, String method, List<NameValuePair> parameters) {
    HttpUriRequest request = buildMethod(method, path, parameters);

    request.addHeader(new BasicHeader("User-Agent", "corespring-java/" + VERSION));
    request.addHeader(new BasicHeader("Accept", "application/json"));
    request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));

    return request;
  }

  private HttpUriRequest buildMethod(String method, String path, List<NameValuePair> parameters) {
    if (method.equalsIgnoreCase("GET")) {
      return generateGetRequest(path, parameters);
    } else {
      throw new IllegalArgumentException("Unknown Method: " + method);
    }
  }

  private HttpGet generateGetRequest(String path, List<NameValuePair> parameters) {
    URI uri = buildUri(path, parameters);
    return new HttpGet(uri);
  }

  private URI buildUri(String path, List<NameValuePair> parameters) {
    StringBuilder sb = new StringBuilder();
    sb.append(path);

    if (parameters != null && parameters.size() > 0) {
      sb.append("?");
      sb.append(URLEncodedUtils.format(parameters, "UTF-8"));
    }

    URI uri;
    try {
      uri = new URI(sb.toString());
    } catch (URISyntaxException e) {
      throw new IllegalStateException("Invalid uri", e);
    }

    return uri;
  }

  public String getEndpoint() {
    return this.endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }


}