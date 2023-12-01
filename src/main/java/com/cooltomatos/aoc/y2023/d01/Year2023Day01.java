package com.cooltomatos.aoc.y2023.d01;

import static java.util.function.Predicate.not;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public final class Year2023Day01 extends AbstractDay {

  public Year2023Day01(String dir, String file) {
    super(2023, 1, dir, file);
  }

  private static final Map<String, Integer> digitToInteger =
      ImmutableMap.of("1", 1, "2", 2, "3", 3, "4", 4, "5", 5, "6", 6, "7", 7, "8", 8, "9", 9);

  private static final Map<String, Integer> realDigitToInteger =
      ImmutableMap.<String, Integer>builder()
          .putAll(digitToInteger)
          .put("one", 1)
          .put("two", 2)
          .put("three", 3)
          .put("four", 4)
          .put("five", 5)
          .put("six", 6)
          .put("seven", 7)
          .put("eight", 8)
          .put("nine", 9)
          .build();

  private int calibrate(Map<String, Integer> lookupTable) {
    return input.stream()
        .mapToInt(
            line -> {
              int first =
                  Stream.iterate(line, not(String::isEmpty), s -> s.substring(1))
                      .map(str -> Maps.filterKeys(lookupTable, str::startsWith))
                      .map(Map::values)
                      .flatMap(Collection::stream)
                      .findFirst()
                      .orElseThrow();
              int last =
                  Stream.iterate(line, not(String::isEmpty), s -> s.substring(0, s.length() - 1))
                      .map(str -> Maps.filterKeys(lookupTable, str::endsWith))
                      .map(Map::values)
                      .flatMap(Collection::stream)
                      .findFirst()
                      .orElseThrow();
              return first * 10 + last;
            })
        .sum();
  }

  @Override
  public Integer part1() {
    return calibrate(digitToInteger);
  }

  @Override
  public Integer part2() {
    return calibrate(realDigitToInteger);
  }
}
