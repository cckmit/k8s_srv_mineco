package com.egoveris.vucfront.base.util;

import java.security.SecureRandom;

public class RandomFileNameGenerator {

  private RandomFileNameGenerator() {

  }

  /**
   * Generates a unique filename.
   * 
   * @param fileName
   *          E.G.: nombreDelArchivo.pdf
   * @return 24872394nombreDelArchivo.pdf
   */
  public static String generateFilename(String fileName) {
    String result = "";
    final SecureRandom random = new SecureRandom();
    long n = random.nextLong();
    if (n == Long.MIN_VALUE) {
      n = 0;
    } else {
      n = Math.abs(n);
    }
    result = result.concat(Long.toString(n)).concat(fileName);
    return result;
  }
}
