package com.ashkanzafari.assignment123.userservice.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * MyTimeUtil.
 *
 * <p>Class to call when need to calculate time. use the functions in this class to achieve
 * consistency for time calculation</p>
 */
public class MyTimeUtil {

  public static Long calculateExpiryDateInDays(int expiryTimeInDays) {
    return LocalDateTime.now().plusDays(expiryTimeInDays).atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli();
  }

  public static Long calculateExpiryDateInMinutes(int expiryTimeInMinutes) {
    return LocalDateTime.now().plusMinutes(expiryTimeInMinutes).atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli();
  }

  public static Long calculateNow() {
    return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli();
  }
}
