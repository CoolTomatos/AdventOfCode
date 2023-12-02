package com.cooltomatos.aoc.y2023.d02;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.Maps.toImmutableEnumMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

record Game(int id, Map<Color, Integer> minimum) {
  private Game(int id, List<Map<Color, Integer>> sets) {
    this(
        id,
        sets.stream()
            .map(Map::entrySet)
            .flatMap(Set::stream)
            .collect(toImmutableEnumMap(Map.Entry::getKey, Map.Entry::getValue, Integer::max)));
  }

  static Game parse(String input) {
    Matcher idMatcher = Pattern.compile("Game (\\d+): (.*)").matcher(input);
    checkState(idMatcher.matches());
    int id = Integer.parseInt(idMatcher.group(1));
    List<Map<Color, Integer>> sets =
        Arrays.stream(idMatcher.group(2).split("; "))
            .map(
                set ->
                    Arrays.stream(set.split(", "))
                        .map(Pattern.compile("(\\d+) (red|green|blue)")::matcher)
                        .peek(matcher -> checkState(matcher.matches()))
                        .collect(
                            toImmutableEnumMap(
                                matcher -> Color.valueOf(matcher.group(2).toUpperCase()),
                                matcher -> Integer.parseInt(matcher.group(1)))))
            .collect(toImmutableList());
    return new Game(id, sets);
  }

  boolean possible(Map<Color, Integer> actual) {
    return Arrays.stream(Color.values())
        .allMatch(color -> actual.getOrDefault(color, 0) >= minimum.getOrDefault(color, 0));
  }

  int power() {
    return minimum.values().stream().reduce(1, (l, r) -> l * r);
  }
}
