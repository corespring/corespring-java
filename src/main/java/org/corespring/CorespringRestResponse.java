package org.corespring;

import org.corespring.parser.JsonResponseParser;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CorespringRestResponse {

  private final String responseText;
  private final int httpStatus;
  private final String url;
  private final String queryString;
  private final boolean error;

  public CorespringRestResponse(String url, String text, int status) {
    Pattern p = Pattern.compile("([^?]+)\\??(.*)");
    Matcher m = p.matcher(url);
    m.matches();
    this.url = m.group(1);
    this.queryString = m.group(2);
    this.responseText = text;
    this.httpStatus = status;
    this.error = (status >= 400);
  }

  public String getResponseText() {
    return responseText;
  }

  public int getHttpStatus() {
    return httpStatus;
  }

  public String getUrl() {
    return url;
  }

  public String getQueryString() {
    return queryString;
  }

  public boolean isError() {
    return error;
  }

  public Map<String, Object> toMap() {
    return JsonResponseParser.parse(this);
  }

  public Collection<Map<String, Object>> toCollection() {
    return JsonResponseParser.parseCollection(this);
  }

}
