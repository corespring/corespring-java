package org.corespring.parser;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.corespring.CorespringRestResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JsonResponseParser {

  public static Collection<Map<String, Object>> parseCollection(CorespringRestResponse response) {
    Collection<Map<String, Object>> returnValue = new ArrayList<Map<String, Object>>();
    try {
      returnValue = new ObjectMapper().readValue(response.getResponseText(), ArrayList.class);
    } catch (JsonParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return returnValue;
  }

  public static Map<String, Object> parse(CorespringRestResponse response) {
    Map<String, Object> returnValue = new HashMap<String, Object>();
    try {
      returnValue = new ObjectMapper().readValue(response.getResponseText(), HashMap.class);
    } catch (JsonParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return returnValue;
  }

}
