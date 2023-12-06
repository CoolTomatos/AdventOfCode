package com.cooltomatos.aoc.y2023.d06;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;

import com.cooltomatos.aoc.AbstractDay;
import java.util.Arrays;
import java.util.List;

public class Day extends AbstractDay {
  private final List<Integer> times;
  private final List<Integer> distances;

  public Day(String dir, String file) {
    super(2023, 6, dir, file);
    times =
        Arrays.stream(input.getFirst().substring(9).split("\\s+"))
            .filter(not(String::isEmpty))
            .map(Integer::parseInt)
            .collect(toImmutableList());
    distances =
        Arrays.stream(input.getLast().substring(9).split("\\s+"))
            .filter(not(String::isEmpty))
            .map(Integer::parseInt)
            .collect(toImmutableList());
  }

  @Override
  public Integer part1() {
    int result = 1;
    for (int i = 0; i < times.size() && i < distances.size(); i++) {
      int time = times.get(i);
      int distance = distances.get(i);
      int current = solve(time, distance);
      result *= current;
    }
    return result;
  }

  @Override
  public Integer part2() {
    var time = Long.parseLong(times.stream().map(String::valueOf).collect(joining()));
    var distance = Long.parseLong(distances.stream().map(String::valueOf).collect(joining()));
    return solve(time, distance);
  }

  private static int solve(long time, long distance) {
    long a = 1;
    long b = -time;
    long c = distance;
    var sqrt = Math.sqrt(b * b - 4 * a * c);
    int least = ((int) ((-b - sqrt) / (2 * a))) + 1;
    int most = ((int) Math.ceil((-b + sqrt) / (2 * a))) - 1;
    return most - least + 1;
  }
}
