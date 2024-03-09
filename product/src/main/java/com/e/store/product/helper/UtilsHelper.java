package com.e.store.product.helper;

import java.util.Random;

public class UtilsHelper {

  private static final int LEFT_LIMIT = 97; // letter 'a'

  private static final int RIGHT_LIMIT = 122; // letter 'z'

  private static final int LENGTH_SLUG_APPENDED = 10;

  public static String getGenerateString() {
    Random random = new Random();
    StringBuilder buffer = new StringBuilder(LENGTH_SLUG_APPENDED);
    buffer.append("-");
    for (int i = 0; i < LENGTH_SLUG_APPENDED; i++) {
      int newChar = LEFT_LIMIT + (int) (random.nextFloat() * (RIGHT_LIMIT - LEFT_LIMIT + 1));
      buffer.append((char) newChar);
    }
    return buffer.toString();
  }
}
