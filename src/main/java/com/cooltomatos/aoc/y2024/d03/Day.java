package com.cooltomatos.aoc.y2024.d03;

import com.cooltomatos.aoc.AbstractDay;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day extends AbstractDay {
  private final String memory;

  public Day(String dir, String file) {
    super(2024, 3, dir, file);
    memory = String.join("\n", input);
  }

  @Override
  public Integer part1() {
    return solve(memory, false);
  }

  @Override
  public Integer part2() {
    return solve(memory, true);
  }

  private static int solve(String memory, boolean conditional) {
    boolean enabled = true;
    int result = 0;
    Matcher matcher =
        Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)").matcher(memory);
    while (matcher.find()) {
      switch (matcher.group()) {
        case "do()":
          enabled = true;
          continue;
        case "don't()":
          enabled = !conditional;
          continue;
        default:
          if (!enabled) {
            continue;
          }
          int left = Integer.parseInt(matcher.group(1));
          int right = Integer.parseInt(matcher.group(2));
          result += left * right;
      }
    }
    return result;
  }
}
