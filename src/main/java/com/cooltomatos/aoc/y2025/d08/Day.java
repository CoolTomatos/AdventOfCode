package com.cooltomatos.aoc.y2025.d08;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day extends AbstractDay {
  private final List<Point> points;

  public Day(String dir, String file) {
    super(2025, 8, dir, file);
    points =
        input.stream()
            .map(point -> point.split(","))
            .map(
                point ->
                    new Point(
                        Long.parseLong(point[0]),
                        Long.parseLong(point[1]),
                        Long.parseLong(point[2])))
            .toList();
  }

  @Override
  public Integer part1() {
    return part1(1000);
  }

  int part1(int limit) {
    var distanceTableBuilder = ImmutableTable.<Point, Point, Long>builder();
    for (int i = 0; i < points.size(); i++) {
      Point left = points.get(i);
      for (int j = 0; j < i; j++) {
        Point right = points.get(j);
        distanceTableBuilder.put(left, right, left.distanceSquared(right));
      }
    }
    var distances = distanceTableBuilder.build();
    var sortedEdges =
        distances.cellSet().stream()
            .sorted(Comparator.comparingLong(Table.Cell::getValue))
            .toList();

    int count = 0;
    Set<Set<Point>> cliques = new HashSet<>();
    for (Table.Cell<Point, Point, Long> edge : sortedEdges) {
      if (count == limit) {
        break;
      }
      count++;
      var left = edge.getRowKey();
      var right = edge.getColumnKey();

      var leftSet = cliques.stream().filter(set -> set.contains(left)).findAny();
      var rightSet = cliques.stream().filter(set -> set.contains(right)).findAny();
      if (leftSet.isEmpty() && rightSet.isEmpty()) {
        var newSet = new HashSet<Point>();
        newSet.add(left);
        newSet.add(right);
        cliques.add(newSet);
      } else if (leftSet.isEmpty()) {
        rightSet.get().add(left);
      } else if (rightSet.isEmpty()) {
        leftSet.get().add(right);
      } else if (!leftSet.get().equals(rightSet.get())) {
        leftSet.get().addAll(rightSet.get());
        rightSet.get().clear();
      }
    }

    return cliques.stream()
        .map(Set::size)
        .sorted(Comparator.reverseOrder())
        .limit(3)
        .reduce(1, (l, r) -> l * r);
  }

  @Override
  public Number part2() {
    return null;
  }

  private record Point(long x, long y, long z) {
    long distanceSquared(Point other) {
      return (x - other.x) * (x - other.x)
          + (y - other.y) * (y - other.y)
          + (z - other.z) * (z - other.z);
    }
  }
}
