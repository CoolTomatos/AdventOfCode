package com.cooltomatos.aoc.y2023.d04;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableSet.toImmutableSet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public record Card(int id, Set<Integer> winningNumbers, List<Integer> numbers, int matches) {
  public Card(int id, Set<Integer> winningNumbers, List<Integer> numbers) {
    this(
        id,
        winningNumbers,
        numbers,
        (int) numbers.stream().filter(winningNumbers::contains).count());
  }

  static Card parse(String input) {
    var matcher = Pattern.compile("Card\\s+(\\d+):\\s+(.*) \\|\\s+(.*)").matcher(input);
    checkState(matcher.matches());
    var id = Integer.parseInt(matcher.group(1));
    var winningNumbers =
        Arrays.stream(matcher.group(2).split("\\s+"))
            .map(Integer::parseInt)
            .collect(toImmutableSet());
    var numbers =
        Arrays.stream(matcher.group(3).split("\\s+"))
            .map(Integer::parseInt)
            .collect(toImmutableList());
    return new Card(id, winningNumbers, numbers);
  }

  int points() {
    return (int) Math.pow(2, matches - 1);
  }
}
