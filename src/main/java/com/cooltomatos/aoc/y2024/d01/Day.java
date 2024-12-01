package com.cooltomatos.aoc.y2024.d01;

import static com.google.common.base.Preconditions.checkState;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Streams;
import java.util.regex.Pattern;

public class Day extends AbstractDay {
  private final Multiset<Integer> left;
  private final Multiset<Integer> right;

  public Day(String dir, String file) {
    super(2024, 1, dir, file);
    var left = ImmutableMultiset.<Integer>builder();
    var right = ImmutableMultiset.<Integer>builder();
    input.stream()
        .map(Pattern.compile("(\\d+)\\s+(\\d+)")::matcher)
        .peek(matcher -> checkState(matcher.matches()))
        .forEach(
            matcher -> {
              left.add(Integer.parseInt(matcher.group(1)));
              right.add(Integer.parseInt(matcher.group(2)));
            });
    this.left = left.build();
    this.right = right.build();
  }

  @Override
  public Integer part1() {
    return Streams.zip(
            left.stream().sorted(),
            right.stream().sorted(),
            (left, right) -> Math.abs(left - right))
        .reduce(0, Integer::sum);
  }

  @Override
  public Integer part2() {
    return left.elementSet().stream()
        .map(element -> element * left.count(element) * right.count(element))
        .reduce(0, Integer::sum);
  }
}
