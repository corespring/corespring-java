package org.corespring.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.corespring.authentication.AccessTokenProvider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the REST details of the interface with the CoresSpring platform.
 */
public abstract class CorespringRestClient {

  /** Version of the API to target */
  public static final String API_VERSION = "api/v1";

  /** Version of corespring-java client */
  public static final String VERSION = "0.3.24";

  /** Default timeout in milliseconds */
  private static final int CONNECTION_TIMEOUT = 10000;

  /** The endpoint. */
  private String endpoint = "http://www.corespring.org/";

  private final String clientId;
  private final String clientSecret;

  private final String ACCESS_TOKEN_KEY = "access_token";

  private String accessToken;
  private AccessTokenProvider accessTokenProvider = new AccessTokenProvider();

  private HttpClient httpClient;

  public CorespringRestClient(String clientId, String clientSecret) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;

    ThreadSafeClientConnManager clientConnectionManager = new ThreadSafeClientConnManager();
    clientConnectionManager.setDefaultMaxPerRoute(10);
    this.httpClient = new DefaultHttpClient(clientConnectionManager);

    httpClient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
    httpClient.getParams().setParameter("http.socket.timeout", new Integer(CONNECTION_TIMEOUT));
    httpClient.getParams().setParameter("http.connection.timeout", new Integer(CONNECTION_TIMEOUT));
    httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
  }

  private String getAccessToken() {
    if (this.accessToken == null) {
      this.accessToken = getAccessTokenProvider().getAccessToken(clientId, clientSecret, getEndpoint());
    }
    return accessToken;
  }

  private AccessTokenProvider getAccessTokenProvider() {
    return accessTokenProvider;
  }

  public void setAccessTokenProvider(AccessTokenProvider accessTokenProvider) {
    this.accessTokenProvider = accessTokenProvider;
  }

  public StringBuilder baseUrl() {
    return new StringBuilder(getEndpoint()).append("/").append(CorespringRestClient.API_VERSION).append("/");
  }

  protected CorespringRestResponse get(String path, List<NameValuePair> paramList) throws CorespringRestException {
    return doRequest(path, "GET", paramList, null);
  }

  protected CorespringRestResponse get(String path) throws CorespringRestException {
    return doRequest(path, "GET", new ArrayList<NameValuePair>(), null);
  }

  protected CorespringRestResponse put(String path, Object entity) throws CorespringRestException {
    return doRequest(path, "PUT", new ArrayList<NameValuePair>(), buildJsonEntity(entity));
  }

  protected CorespringRestResponse post(String path, Object entity) throws CorespringRestException {
    return doRequest(path, "POST", new ArrayList<NameValuePair>(), buildJsonEntity(entity));
  }

  protected CorespringRestResponse delete(String path) throws CorespringRestException  {
    return doRequest(path, "DELETE", new ArrayList<NameValuePair>(), null);
  }

  private CorespringRestResponse doRequest(String path, String method, List<NameValuePair> paramList,
                                          StringEntity jsonEntity) throws CorespringRestException {
    if (getAccessToken() != null) {
      paramList.add(new BasicNameValuePair(ACCESS_TOKEN_KEY, getAccessToken()));
    }

    HttpUriRequest request = setupRequest(path, method, paramList, jsonEntity);
    CorespringRestResponse response = null;
    CorespringRestException exception = null;

    try {
      response = tryRequest(request);
    } catch (CorespringRestException e) {
      if (e.hasErrorCode() && e.isInvalidAccessToken()) {
        // reload access token if invalid
        this.accessToken = getAccessTokenProvider().getAccessToken(this.clientId, this.clientSecret, getEndpoint());

        if (getAccessToken() != null) {
          NameValuePair accessTokenPair = null;
          for (NameValuePair pair : paramList) {
            if (pair.getName().equals(ACCESS_TOKEN_KEY)) {
              accessTokenPair = pair;
              break;
            }
          }
          if (accessTokenPair != null) {
            paramList.remove(accessTokenPair);
          }

          paramList.add(new BasicNameValuePair(ACCESS_TOKEN_KEY, getAccessToken()));
        }

        request = setupRequest(path, method, paramList, jsonEntity);
        response = tryRequest(request);
      } else {
        exception = e;
      }
    }

    if (exception != null) {
      throw exception;
    }

    return response;
  }

  private CorespringRestResponse tryRequest(HttpUriRequest request) throws CorespringRestException {
    HttpResponse response;
    try {
      response = httpClient.execute(request);
      HttpEntity responseEntity = response.getEntity();

      String responseBody = "";

      if (responseEntity != null) {
        responseBody = EntityUtils.toString(responseEntity);
      }

      StatusLine status = response.getStatusLine();
      int statusCode = status.getStatusCode();

      CorespringRestResponse restResponse =
          new CorespringRestResponse(request.getURI().toString(), responseBody, statusCode);

      return restResponse;
    } catch (ClientProtocolException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private HttpUriRequest setupRequest(String path, String method, List<NameValuePair> parameters,
                                      StringEntity jsonEntity) {
    HttpUriRequest request = (jsonEntity == null) ? buildMethod(method, path, parameters) :
        buildMethod(method, path, parameters, jsonEntity);

    request.addHeader(new BasicHeader("User-Agent", "corespring-java/" + VERSION));
    request.addHeader(new BasicHeader("Accept", "application/json"));
    request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));

    if (method.equals("PUT") || method.equals("POST")) {
      request.addHeader(new BasicHeader("Content-Type", "application/json"));
    }

    return request;
  }

  private HttpUriRequest buildMethod(String method, String path, List<NameValuePair> parameters) {
    if (method.equalsIgnoreCase("GET")) {
      return generateGetRequest(path, parameters);
    } else if (method.equalsIgnoreCase("DELETE")) {
      return generateDeleteRequest(path, parameters);
    } else {
      throw new IllegalArgumentException("Unknown Method: " + method);
    }
  }

  private HttpUriRequest buildMethod(String method, String path, List<NameValuePair> parameters, StringEntity jsonEntity) {
    if (method.equalsIgnoreCase("POST")) {
      return generatePostRequest(path, parameters, jsonEntity);
    } else if (method.equalsIgnoreCase("PUT")) {
      return generatePutRequest(path, parameters, jsonEntity);
    } else {
      throw new IllegalArgumentException("Unknown Method " + method);
    }
  }

  private HttpPost generatePostRequest(String path, List<NameValuePair> parameters, StringEntity jsonEntity) {
    URI uri = buildUri(path, parameters);
    try {
      HttpPost post = new HttpPost(uri);
      post.setEntity(jsonEntity);
      return post;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private HttpPut generatePutRequest(String path, List<NameValuePair> parameters, StringEntity jsonEntity) {
    URI uri = buildUri(path, parameters);
    try {
      HttpPut put = new HttpPut(uri);
      put.setEntity(jsonEntity);
      return put;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private HttpDelete generateDeleteRequest(String path, List<NameValuePair> parameters) {
    URI uri = buildUri(path, parameters);
    return new HttpDelete(uri);
  }

  private StringEntity buildJsonEntity(Object entity) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    String json = null;
    try {
      json = objectMapper.writeValueAsString(entity);
      return new StringEntity(json);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
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