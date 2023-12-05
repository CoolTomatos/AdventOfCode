package com.cooltomatos.aoc.y2023.d05;

import static com.google.common.collect.ImmutableList.toImmutableList;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.BoundType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
    return seeds.stream()
        .mapToLong(
            seed ->
                maps.stream()
                    .reduce(
                        seed,
                        (mid, map) -> mid + Objects.requireNonNullElse(map.get(mid), 0L),
                        (mid, result) -> result))
        .reduce(Long.MAX_VALUE, Long::min);
  }

  @Override
  public Long part2() {
    var seedMap = TreeRangeMap.<Long, Long>create();
    for (int i = 0; i < seeds.size(); ) {
      var start = seeds.get(i++);
      var count = seeds.get(i++);
      seedMap.put(Range.closedOpen(start, start + count), 0L);
    }
    return maps.stream().reduce(seedMap, reducer).asMapOfRanges().entrySet().stream()
        .filter(
            entry ->
                entry.getKey().lowerBoundType() != BoundType.OPEN
                    || entry.getKey().upperBoundType() != BoundType.OPEN
                    || entry.getKey().lowerEndpoint() + 1 < entry.getKey().upperEndpoint())
        .mapToLong(
            entry -> {
              var range = entry.getKey();
              var diff = entry.getValue();
              long left;
              if (range.lowerBoundType() == BoundType.CLOSED
                  || range.lowerEndpoint().equals(range.upperEndpoint())) {
                left = range.lowerEndpoint();
              } else {
                left = range.lowerEndpoint() + 1;
              }
              return left + diff;
            })
        .reduce(Long.MAX_VALUE, Long::min);
  }

  BinaryOperator<RangeMap<Long, Long>> reducer =
      (left, right) -> {
        var rightCopy = TreeRangeMap.<Long, Long>create();
        rightCopy.putAll(right);
        var map = ImmutableRangeMap.<Long, Long>builder();
        left.asMapOfRanges()
            .forEach(
                (range, leftDiff) -> {
                  var shifted = shiftRange(range, leftDiff);
                  rightCopy.merge(shifted, leftDiff, Long::sum);
                  rightCopy
                      .subRangeMap(shifted)
                      .asMapOfRanges()
                      .forEach(
                          (rightRange, newDiff) ->
                              map.put(shiftRange(rightRange, -leftDiff), newDiff));
                });
        return map.build();
      };

  private static Range<Long> shiftRange(Range<Long> range, long diff) {
    return Range.range(
        range.lowerEndpoint() + diff,
        range.lowerBoundType(),
        range.upperEndpoint() + diff,
        range.upperBoundType());
  }
}
