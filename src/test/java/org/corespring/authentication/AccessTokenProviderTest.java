package org.corespring.authentication;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccessTokenProviderTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(8089);

  @Test
  public void testGetAccessToken() {
    String clientId = "524c5cb5300401522ab21db1";
    String clientSecret = "325hm11xiz7ykeen2ibt";

    String accessToken = new AccessTokenProvider().getAccessToken(clientId, clientSecret, "http://localhost:8089");

    assertEquals("demo_token", accessToken);
  }
}
