package com.cooltomatos.aoc.common;

import java.util.HashSet;
import java.util.Set;

public record Coordinate(int x, int y) {
  public Set<Coordinate> neighbors4() {
    return Set.of(
        new Coordinate(x - 1, y),
        new Coordinate(x + 1, y),
        new Coordinate(x, y - 1),
        new Coordinate(x, y + 1));
  }

  public Set<Coordinate> neighbors4(int height, int width) {
    var result = new HashSet<Coordinate>();
    if (x > 0) {
      result.add(new Coordinate(x - 1, y));
    }
    if (x < height - 1) {
      result.add(new Coordinate(x + 1, y));
    }
    if (y > 0) {
      result.add(new Coordinate(x, y - 1));
    }
    if (y < width - 1) {
      result.add(new Coordinate(x, y + 1));
    }
    return Set.copyOf(result);
  }

  public Set<Coordinate> neighbors8() {
    return Set.of(
        new Coordinate(x - 1, y - 1),
        new Coordinate(x - 1, y),
        new Coordinate(x - 1, y + 1),
        new Coordinate(x, y - 1),
        new Coordinate(x, y + 1),
        new Coordinate(x + 1, y - 1),
        new Coordinate(x + 1, y),
        new Coordinate(x + 1, y + 1));
  }

  public Set<Coordinate> neighbors8(int height, int width) {
    var result = new HashSet<>(neighbors4(height, width));
    if (x > 0) {
      if (y > 0) {
        result.add(new Coordinate(x - 1, y - 1));
      }
      if (y < width - 1) {
        result.add(new Coordinate(x - 1, y + 1));
      }
    }
    if (x < height - 1) {
      if (y > 0) {
        result.add(new Coordinate(x + 1, y - 1));
      }
      if (y < width - 1) {
        result.add(new Coordinate(x + 1, y + 1));
      }
    }
    return Set.copyOf(result);
  }
}
