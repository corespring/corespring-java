package org.corespring;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.corespring.resource.*;
import org.corespring.resource.player.Options;
import org.corespring.resource.player.OptionsResponse;
import org.corespring.resource.question.Answer;
import org.corespring.resource.question.Contributor;
import org.corespring.resource.question.ItemType;
import org.corespring.rest.CorespringRestClient;
import org.corespring.rest.CorespringRestException;
import org.corespring.rest.CorespringRestResponse;
import org.corespring.rest.ItemQuery;

import java.util.*;
import java.util.Map.Entry;

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

  public Collection<ContentCollection> getCollections() throws CorespringRestException {
    CorespringRestResponse response = get(ContentCollection.getResourcesRoute(this));
    return response.getAll(ContentCollection.class);
  }

  public List<ItemType> getItemTypesByCollection(ContentCollection contentCollection) throws CorespringRestException {
    Collection<ContentCollection> contentCollections = new ArrayList<ContentCollection>();
    contentCollections.add(contentCollection);
    return this.getItemTypesByCollections(contentCollections);
  }

  /**
   * Returns a {@link List} of {@link ItemType}s, ordered by the frequency with which they appear in the provided
   * @{link ContentCollection}s.
   */
  public List<ItemType> getItemTypesByCollections(Collection<ContentCollection> collections)
      throws CorespringRestException {
    Collection<String> collectionIds = new ArrayList<String>();
    for (ContentCollection collection : collections) {
      collectionIds.add(collection.getId());
    }

    return getItemTypesByCollectionIds(collectionIds);
  }

  public List<ItemType> getItemTypesByCollectionIds(Collection<String> collectionIds) throws CorespringRestException {
    String route = ContentCollection.getFieldValuesRoute(this, collectionIds, ItemType.FIELD_NAME);
    Map<String, Double> map = get(route).get(Map.class);

    ValueComparator valueComparator = new ValueComparator(map);
    TreeMap<String, Double> sortedMap = new TreeMap<String, Double>(valueComparator);
    sortedMap.putAll(map);

    List<ItemType> itemTypes = new ArrayList<ItemType>();
    for (Entry<String, Double> entry : sortedMap.entrySet()) {
      ItemType itemType = ItemType.fromDescription(entry.getKey());
      if (itemType != null) {
        itemTypes.add(itemType);
      }
    }

    return itemTypes;
  }

  public List<String> getContributorsByCollection(ContentCollection contentCollection) throws CorespringRestException {
    Collection<ContentCollection> contentCollections = new ArrayList<ContentCollection>();
    contentCollections.add(contentCollection);
    return this.getContributorsByCollections(contentCollections);
  }

  /**
   * Returns a {@link List} of Strings representing contributors to the provided {@link ContentCollection}s, ordered by
   * frequency with which they appear.
   */
  public List<String> getContributorsByCollections(Collection<ContentCollection> contentCollections)
      throws CorespringRestException {
    Collection<String> collectionIds = new ArrayList<String>();
    for (ContentCollection collection : contentCollections) {
      collectionIds.add(collection.getId());
    }

    return getContributorsByCollectionIds(collectionIds);
  }

  public List<String> getContributorsByCollectionIds(Collection<String> collectionIds) throws CorespringRestException {
    String route = ContentCollection.getFieldValuesRoute(this, collectionIds, Contributor.FIELD_NAME);
    Map<String, Double> map = get(route).get(Map.class);

    ValueComparator valueComparator = new ValueComparator(map);
    TreeMap<String, Double> sortedMap = new TreeMap<String, Double>(valueComparator);
    sortedMap.putAll(map);

    List<String> contributors = new ArrayList<String>();
    for (Entry<String, Double> entry : sortedMap.entrySet()) {
      if (entry.getKey() != null) {
        contributors.add(entry.getKey());
      }
    }

    return contributors;
  }

  public Collection<Item> findItems(ItemQuery itemQuery) throws CorespringRestException {
    return findItems(itemQuery, null, null);
  }

  public Collection<Item> findItems(ItemQuery itemQuery, Integer size, Integer offset) throws CorespringRestException {
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("q", itemQuery.toString()));
    if (size != null) {
      params.add(new BasicNameValuePair("l", size.toString()));
    }
    if (offset != null) {
      params.add(new BasicNameValuePair("sk", offset.toString()));
    }
    CorespringRestResponse response = get(Item.getResourceRoute(this), params);
    return response.getAll(Item.class);
  }

  public int countItems(ItemQuery itemQuery) throws CorespringRestException {
    return countItems(itemQuery, null, null);
  }

  public int countItems(ItemQuery itemQuery, Integer size, Integer offset) throws CorespringRestException {
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("q", itemQuery.toString()));
    params.add(new BasicNameValuePair("c", "true"));
    if (size != null) {
      params.add(new BasicNameValuePair("l", size.toString()));
    }
    if (offset != null) {
      params.add(new BasicNameValuePair("sk", offset.toString()));
    }
    CorespringRestResponse response = get(Item.getResourceRoute(this), params);
    HashMap map = response.get(HashMap.class);
    return (Integer) map.get("count");
  }


  private class ValueComparator implements Comparator<String> {

    Map<String, Double> base;
    public ValueComparator(Map<String, Double> base) {
      this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.
    public int compare(String a, String b) {
      if (base.get(a) >= base.get(b)) {
        return -1;
      } else {
        return 1;
      } // returning 0 would merge keys
    }
  }

}
