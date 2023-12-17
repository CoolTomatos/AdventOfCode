package com.cooltomatos.aoc.common;

import java.util.Comparator;

public record Coordinate(int x, int y) implements Comparable<Coordinate> {
  @Override
  public int compareTo(Coordinate o) {
    return Comparator.comparingInt(Coordinate::x).thenComparingInt(Coordinate::y).compare(this, o);
  }
}
