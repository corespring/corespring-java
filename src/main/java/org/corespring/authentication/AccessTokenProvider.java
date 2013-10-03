package org.corespring.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.corespring.rest.CorespringRestClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * A utility class whose {@link #getAccessToken(String, String, String)} method calls the CoreSpring platform with a
 * specified client id and client secret, returning an access token.
 *
 * Note: You will be issued a client id and client secret when you sign up for a developer account at
 * http://www.corespring.org/signup
 */
public class AccessTokenProvider {

  /* Route for access token */
  private static final String ACCESS_TOKEN_ROUTE = "/auth/access_token";

  /** Default timeout in milliseconds */
  private static final int CONNECTION_TIMEOUT = 10000;


  /**
   * A static method that takes a client id and client secret, passes them to the CoreSpring platform, and returns an
   * access token used for subsequent API calls.
   *
   * @param clientId client id
   * @param clientSecret client secret
   * @param baseUrl base URL for CoreSpring server
   * @return
   */
  public String getAccessToken(String clientId, String clientSecret, String baseUrl) {

    AccessTokenRequest authTokenRequest = new AccessTokenRequest(clientId, clientSecret);

    ThreadSafeClientConnManager clientConnectionManager = new ThreadSafeClientConnManager();
    clientConnectionManager.setDefaultMaxPerRoute(10);
    HttpClient httpClient = new DefaultHttpClient(clientConnectionManager);

    httpClient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
    httpClient.getParams().setParameter("http.socket.timeout", new Integer(CONNECTION_TIMEOUT));
    httpClient.getParams().setParameter("http.connection.timeout", new Integer(CONNECTION_TIMEOUT));
    httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");

    try {
      HttpPost request = new HttpPost(new URI(baseUrl + ACCESS_TOKEN_ROUTE));
      request.addHeader(new BasicHeader("User-Agent", "corespring-java/" + CorespringRestClient.VERSION));
      request.addHeader(new BasicHeader("Accept", "application/json"));
      request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));
      request.addHeader(new BasicHeader("Content-Type", "application/json"));

      StringEntity jsonEntity = buildJsonEntity(authTokenRequest);
      request.setEntity(jsonEntity);

      HttpResponse response = httpClient.execute(request);
      HttpEntity entity = response.getEntity();

      String responseBody = "";

      if (entity != null) {
        responseBody = EntityUtils.toString(entity);
      }

      ObjectMapper objectMapper = new ObjectMapper();
      AccessTokenResponse accessToken = objectMapper.readValue(responseBody, AccessTokenResponse.class);

      return accessToken.toString();

    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private static StringEntity buildJsonEntity(Object entity) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      String json = objectMapper.writeValueAsString(entity);
      return new StringEntity(json);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

}
