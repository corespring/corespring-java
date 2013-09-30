package org.corespring;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.corespring.resource.Organization;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CorespringRestClientTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(8089);

  @Test
  public void testValidAccessTokenDoesNotThrowException() {

    // Success with valid ObjectId
    try {
      new CorespringRestClient("52498773a9c98a782be5b739");
      assertTrue(true);
    } catch (IllegalArgumentException e) {
      assertTrue(false);
    }

  }

  @Test
  public void testInvalidAccessTokenThrowsException() {

    // Fails with invalid ObjectId
    try {
      new CorespringRestClient("abc");
      assertTrue(false);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

  }


  @Test
  public void testGetOrganizations() {
    CorespringRestClient client = new CorespringRestClient("demo_token");
    client.setEndpoint("http://localhost:8089/");

    Collection<Organization> organizations = client.getOrganizations();

    assertEquals(1, organizations.size());
    Organization organization = organizations.iterator().next();

    assertEquals("Demo Organization", organization.getName());
    assertEquals("51114b307fc1eaa866444648", organization.getId());
  }

}
