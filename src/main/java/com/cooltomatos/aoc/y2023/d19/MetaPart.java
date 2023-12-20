package com.cooltomatos.aoc.y2023.d19;


import com.google.common.collect.RangeSet;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public record MetaPart(int id, Map<Rating, RangeSet<Integer>> ratings) {
  static final Random RANDOM = new Random();

  MetaPart(Map<Rating, RangeSet<Integer>> ratings) {
    this(RANDOM.nextInt(), ratings);
  }

  @Override
  public boolean equals(Object o) {
    return this == o || o instanceof MetaPart other && id == other.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
