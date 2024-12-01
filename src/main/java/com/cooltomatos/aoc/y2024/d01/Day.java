package com.cooltomatos.aoc.y2024.d01;


import static com.google.common.base.Preconditions.checkState;

import com.cooltomatos.aoc.AbstractDay;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Day extends AbstractDay {
  private final int[] left;
  private final int[] right;

  public Day(String dir, String file) {
    super(2024, 1, dir, file);
    left = new int[input.size()];
    right = new int[input.size()];
    var pattern = Pattern.compile("(\\d+)\\s+(\\d+)");
    for (var i = 0; i < input.size(); i++) {
      var matcher = pattern.matcher(input.get(i));
      checkState(matcher.matches());
      left[i] = Integer.parseInt(matcher.group(1));
      right[i] = Integer.parseInt(matcher.group(2));
    }
  }

  @Override
  public Integer part1() {
    Arrays.sort(left);
    Arrays.sort(right);
    int total = 0;
    for (var i = 0; i < input.size(); i++) {
      total += Math.abs(left[i] - right[i]);
    }
    return total;
  }

  @Override
  public Number part2() {
    return null;
  }
}
