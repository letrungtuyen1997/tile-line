package com.ss.utils;

import com.badlogic.gdx.math.MathUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class StrUtils {
  public static List<Integer> parseListInt(String fmt) {
    String[] split = fmt.split(",");
    if (split.length != 4)
      return Arrays.asList(0,0,0,0);
    int p0,p1,p2,p3;
    try {
      p0 = Integer.parseInt(split[0]);
      p1 = Integer.parseInt(split[1]);
      p2 = Integer.parseInt(split[2]);
      p3 = Integer.parseInt(split[3]);
    }
    catch (Exception e) {
      return Arrays.asList(0,0,0,0);
    }
    return Arrays.asList(p0,p1,p2,p3);
  }

  public static String rand(int len) {
    Random random = new Random();
    StringBuilder buffer = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      int randomLimitedInt = MathUtils.random(97,123); //[a:z]
      buffer.append((char) randomLimitedInt);
    }
    return buffer.toString();
  }
}