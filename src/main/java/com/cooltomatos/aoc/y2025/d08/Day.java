package com.cooltomatos.aoc.y2025.d08;

import com.cooltomatos.aoc.AbstractDay;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.IntStream;

public class Day extends AbstractDay {
  private final List<Edge> sortedEdges;

  public Day(String dir, String file) {
    super(2025, 8, dir, file);
    var points =
        input.stream()
            .map(point -> point.split(","))
            .map(
                point ->
                    new Point(
                        Long.parseLong(point[0]),
                        Long.parseLong(point[1]),
                        Long.parseLong(point[2])))
            .toList();
    sortedEdges =
        IntStream.range(0, points.size())
            .boxed()
            .flatMap(
                i -> IntStream.range(0, i).mapToObj(j -> new Edge(points.get(i), points.get(j))))
            .sorted(Comparator.comparingLong(Edge::distanceSquared))
            .toList();
  }

  @Override
  public Long part1() {
    return solve(OptionalInt.of(1000));
  }

  @Override
  public Long part2() {
    return solve(OptionalInt.empty());
  }

  long solve(OptionalInt limit) {
    Set<Set<Point>> circuits = new HashSet<>();
    int count = 0;
    for (var edge : sortedEdges) {
      if (limit.isPresent() && limit.getAsInt() == count++) {
        break;
      }
      var left = edge.left();
      var right = edge.right();

      var leftSet =
          circuits.stream()
              .filter(circuit -> circuit.contains(left))
              .findAny()
              .orElseGet(HashSet::new);
      var rightSet =
          circuits.stream()
              .filter(circuit -> circuit.contains(right))
              .findAny()
              .orElseGet(HashSet::new);
      if (leftSet.isEmpty() && rightSet.isEmpty()) {
        leftSet.add(left);
        leftSet.add(right);
        circuits.add(leftSet);
      } else if (leftSet.isEmpty()) {
        rightSet.add(left);
      } else if (rightSet.isEmpty()) {
        leftSet.add(right);
      } else if (!leftSet.equals(rightSet)) {
        leftSet.addAll(rightSet);
        rightSet.clear();
      }
      if (input.size() == leftSet.size() + rightSet.size()) {
        return left.x() * right.x();
      }
    }
    return circuits.stream()
        .map(Collection::size)
        .sorted(Comparator.reverseOrder())
        .limit(3)
        .reduce(1, (l, r) -> l * r);
  }

  private record Point(long x, long y, long z) {}

  private record Edge(Day.Point left, Day.Point right) {
    public long distanceSquared() {
      return (left.x() - right.x()) * (left.x() - right.x())
          + (left.y() - right.y()) * (left.y() - right.y())
          + (left.z() - right.z()) * (left.z() - right.z());
    }
  }
}
