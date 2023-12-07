package com.cooltomatos.aoc.y2023.d07;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableMap.toImmutableMap;

import com.cooltomatos.aoc.AbstractDay;
import java.util.Map;
import java.util.regex.Pattern;

public class Day extends AbstractDay {
  private final Map<Hand, Integer> handToBid;

  public Day(String dir, String file) {
    super(2023, 7, dir, file);
    handToBid =
        input.stream()
            .map(Pattern.compile("(.{5}) (\\d+)")::matcher)
            .peek(matcher -> checkState(matcher.matches()))
            .collect(
                toImmutableMap(
                    matcher -> new Hand(matcher.group(1)),
                    matcher -> Integer.parseInt(matcher.group(2))));
  }

  @Override
  public Integer part1() {
    var sorted =
        handToBid.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(Hand.comparator(false)))
            .toList();
    int result = 0;
    for (int i = 0; i < sorted.size(); ) {
      result += sorted.get(i).getValue() * ++i;
    }
    return result;
  }

  @Override
  public Integer part2() {
    var sorted =
        handToBid.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(Hand.comparator(true)))
            .toList();
    int result = 0;
    for (int i = 0; i < sorted.size(); ) {
      result += sorted.get(i).getValue() * ++i;
    }
    return result;
  }
}
