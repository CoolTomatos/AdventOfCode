package com.cooltomatos.aoc.y2023.d08;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableListMultimap.flatteningToImmutableListMultimap;

import com.cooltomatos.aoc.AbstractDay;
import com.cooltomatos.aoc.common.MathFunctions;
import com.google.common.collect.ListMultimap;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day extends AbstractDay {
  private final List<Direction> instructions;

  private final ListMultimap<String, String> nodes;

  public Day(String dir, String file) {
    super(2023, 8, dir, file);
    instructions =
        input
            .getFirst()
            .chars()
            .mapToObj(
                value ->
                    switch (value) {
                      case 'L' -> Direction.LEFT;
                      case 'R' -> Direction.RIGHT;
                      default -> throw new IllegalArgumentException();
                    })
            .toList();
    nodes =
        input.stream()
            .skip(2)
            .map(Pattern.compile("(.{3}) = \\((.{3}), (.{3})\\)")::matcher)
            .peek(matcher -> checkState(matcher.matches()))
            .collect(
                flatteningToImmutableListMultimap(
                    matcher -> matcher.group(1),
                    matcher -> Stream.of(matcher.group(2), matcher.group(3))));
  }

  @Override
  public Integer part1() {
    return solve("AAA", "ZZZ"::equals);
  }

  @Override
  public Long part2() {
    return nodes.keySet().stream()
        .filter(node -> node.endsWith("A"))
        .mapToLong(start -> solve(start, node -> node.endsWith("Z")))
        .reduce(1, MathFunctions::lcm);
  }

  private int solve(String start, Predicate<String> goal) {
    int step = 0;
    int instructionIndex = 0;
    var current = start;
    while (goal.negate().test(current)) {
      var next = nodes.get(current);
      current =
          switch (instructions.get(instructionIndex)) {
            case LEFT -> next.get(0);
            case RIGHT -> next.get(1);
          };
      step++;
      instructionIndex = (instructionIndex + 1) % instructions.size();
    }
    return step;
  }

  private enum Direction {
    LEFT,
    RIGHT
  }
}
