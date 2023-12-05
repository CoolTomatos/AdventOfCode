package com.cooltomatos.aoc.y2023.d05;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.BoundType.CLOSED;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableRangeMap.toImmutableRangeMap;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

public class Day extends AbstractDay {
  private final List<Long> seeds;
  private final List<RangeMap<Long, Long>> maps;

  public Day(String dir, String file) {
    super(2023, 5, dir, file);
    seeds =
        Arrays.stream(input.getFirst().substring(7).split(" "))
            .map(Long::parseLong)
            .collect(toImmutableList());
    var mapsBuilder = ImmutableList.<RangeMap<Long, Long>>builder();
    var mapBuilder = ImmutableRangeMap.<Long, Long>builder();
    for (int i = 2; i < input.size(); i++) {
      var line = input.get(i);
      if (line.isEmpty()) {
        mapsBuilder.add(mapBuilder.build());
        mapBuilder = ImmutableRangeMap.builder();
      } else if (Character.isDigit(line.charAt(0))) {
        var values = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
        mapBuilder.put(Range.closed(values[1], values[1] + values[2] - 1), values[0] - values[1]);
      }
    }
    mapsBuilder.add(mapBuilder.build());
    maps = mapsBuilder.build();
  }

  @Override
  public Long part1() {
    var seedMap =
        seeds.stream().collect(toImmutableRangeMap(seed -> Range.closed(seed, seed), unused -> 0L));
    return solve(seedMap);
  }

  @Override
  public Long part2() {
    var seedMap = ImmutableRangeMap.<Long, Long>builder();
    for (int i = 0; i < seeds.size(); ) {
      var start = seeds.get(i++);
      var count = seeds.get(i++);
      seedMap.put(Range.closed(start, start + count - 1), 0L);
    }
    return solve(seedMap.build());
  }

  private long solve(RangeMap<Long, Long> seedMap) {
    return maps.stream().reduce(seedMap, reducer).asMapOfRanges().entrySet().stream()
        .mapToLong(entry -> entry.getKey().lowerEndpoint() + entry.getValue())
        .reduce(Long.MAX_VALUE, Long::min);
  }

  private static Range<Long> shift(Range<Long> range, long diff) {
    checkArgument(range.lowerBoundType() == CLOSED && range.upperBoundType() == CLOSED);
    return Range.closed(range.lowerEndpoint() + diff, range.upperEndpoint() + diff);
  }

  private static Optional<Range<Long>> tighten(Range<Long> range) {
    var lowerType = range.lowerBoundType();
    long lower = range.lowerEndpoint();
    var upperType = range.upperBoundType();
    long upper = range.upperEndpoint();
    if (lowerType == CLOSED || upperType == CLOSED || lower + 1 < upper) {
      return Optional.of(
          Range.closed(
              lowerType == CLOSED ? lower : lower + 1, upperType == CLOSED ? upper : upper - 1));
    }
    return Optional.empty();
  }

  private static final BinaryOperator<RangeMap<Long, Long>> reducer =
      (base, incoming) -> {
        var copy = TreeRangeMap.<Long, Long>create();
        copy.putAll(incoming);
        var map = ImmutableRangeMap.<Long, Long>builder();
        base.asMapOfRanges()
            .forEach(
                (oldRange, oldDiff) -> {
                  var shifted = shift(oldRange, oldDiff);
                  copy.merge(shifted, oldDiff, Long::sum);
                  copy.subRangeMap(shifted)
                      .asMapOfRanges()
                      .forEach(
                          (range, newDiff) ->
                              tighten(range)
                                  .map(tightened -> shift(tightened, -oldDiff))
                                  .ifPresent(newRange -> map.put(newRange, newDiff)));
                });
        return map.build();
      };
}
