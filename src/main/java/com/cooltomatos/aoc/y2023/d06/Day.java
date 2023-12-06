package com.cooltomatos.aoc.y2023.d06;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Day extends AbstractDay {
  private final List<Long> times;
  private final List<Long> distances;

  public Day(String dir, String file) {
    super(2023, 6, dir, file);
    times =
        Arrays.stream(input.getFirst().substring(9).split("\\s+"))
            .filter(not(String::isEmpty))
            .map(Long::parseLong)
            .collect(toImmutableList());
    distances =
        Arrays.stream(input.getLast().substring(9).split("\\s+"))
            .filter(not(String::isEmpty))
            .map(Long::parseLong)
            .collect(toImmutableList());
  }

  @Override
  public Integer part1() {
    return Streams.zip(times.stream(), distances.stream(), solve).reduce(1, (l, r) -> l * r);
  }

  @Override
  public Integer part2() {
    long time = Long.parseLong(times.stream().map(String::valueOf).collect(joining()));
    long distance = Long.parseLong(distances.stream().map(String::valueOf).collect(joining()));
    return solve.apply(time, distance);
  }

  private static final BiFunction<Long, Long, Integer> solve =
      (time, distance) -> {
        long a = 1;
        long b = -time;
        long c = distance;
        double sqrt = Math.sqrt(b * b - 4 * a * c);
        int least = (int) ((-b - sqrt) / (2 * a)) + 1;
        int most = (int) Math.ceil((-b + sqrt) / (2 * a)) - 1;
        return most - least + 1;
      };
}
