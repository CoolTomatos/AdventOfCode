package com.cooltomatos.aoc.y2024.d11;

import static java.util.function.Function.identity;

import com.cooltomatos.aoc.AbstractDay;
import com.cooltomatos.aoc.function.EntryFunction;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day extends AbstractDay {
  private final List<Long> stones;
  private final ListMultimap<Long, Long> cache = ArrayListMultimap.create();

  public Day(String dir, String file) {
    super(2024, 11, dir, file);
    stones = Arrays.stream(input.getFirst().split("\\s+")).map(Long::parseLong).toList();
  }

  @Override
  public Long part1() {
    return solve(25);
  }

  @Override
  public Long part2() {
    return solve(75);
  }

  private long solve(int times) {
    Map<Long, Long> stonesAndCount =
        stones.stream().collect(Collectors.toMap(identity(), stone -> 1L, Long::sum));
    for (int i = 0; i < times; i++) {
      stonesAndCount =
          stonesAndCount.entrySet().stream()
              .flatMap(
                  (EntryFunction<Long, Long, Stream<Entry<Long, Long>>>)
                      (stone, count) ->
                          blink(stone).stream().map(newStone -> Map.entry(newStone, count)))
              .collect(Collectors.toMap(Entry::getKey, Entry::getValue, Long::sum));
    }
    return stonesAndCount.values().stream().mapToLong(Long::longValue).sum();
  }

  private List<Long> blink(long stone) {
    if (!cache.containsKey(stone)) {
      if (stone == 0) {
        cache.put(stone, 1L);
      } else {
        int digits = (int) (Math.floor(Math.log10(stone)) + 1);
        if (digits % 2 == 0) {
          long pow = (long) Math.pow(10, digits / 2);
          cache.put(stone, stone / pow);
          cache.put(stone, stone % pow);
        } else {
          cache.put(stone, stone * 2024);
        }
      }
    }
    return cache.get(stone);
  }
}
