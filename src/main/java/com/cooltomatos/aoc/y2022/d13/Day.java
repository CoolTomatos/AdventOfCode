package com.cooltomatos.aoc.y2022.d13;

import static com.google.common.collect.ImmutableList.toImmutableList;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.Stream;

public class Day extends AbstractDay {
  private final List<Pair> pairs;

  public Day(String dir, String file) {
    super(2022, 13, dir, file);
    var builder = ImmutableList.<Pair>builder();
    for (int i = 0; i < input.size(); ) {
      var left = Pair.parse(input.get(i++));
      var right = Pair.parse(input.get(i++));
      builder.add(new Pair(++i / 3, left, right));
    }
    pairs = builder.build();
  }

  @Override
  public Integer part1() {
    return pairs.stream().filter(Pair::inOrder).mapToInt(Pair::index).sum();
  }

  @Override
  public Integer part2() {
    var firstDivider = List.of(List.of(2));
    var secondDivider = List.of(List.of(6));
    var sortedPackets =
        Stream.concat(
                pairs.stream().flatMap(pair -> Stream.of(pair.left(), pair.right())),
                Stream.of(firstDivider, secondDivider))
            .sorted(Pair.packetComparator)
            .collect(toImmutableList());
    var firstIndex = sortedPackets.indexOf(firstDivider) + 1;
    var secondIndex = sortedPackets.indexOf(secondDivider) + 1;
    return firstIndex * secondIndex;
  }
}
