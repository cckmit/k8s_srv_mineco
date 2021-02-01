package com.egoveris.script.parser;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class ParserUtils {
  
  private ParserUtils() {
    // Private constructor
  }
  
  /**
   * Parses the swagger api data from a url
   * @param url
   * @return
   */
  public static Swagger parseFromUrl(String url) {
    return new SwaggerParser().read(url);
  }
  
  /**
   * Parses the swagger api data from a file
   * (used mostly for testing purposes)
   * 
   * @param location
   * @return
   */
  public static Swagger parseFromFile(String location) {
    return new SwaggerParser().read(ParserUtils.class.getResource(location).toString());
  }
  
}
