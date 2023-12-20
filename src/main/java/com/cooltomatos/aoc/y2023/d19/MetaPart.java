package com.cooltomatos.aoc.y2023.d19;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Range;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

record MetaPart(int id, ImmutableMap<Rating, Range<Integer>> ratings) {
  static final Random RANDOM = new Random();

  MetaPart(Map<Rating, Range<Integer>> ratings) {
    this(RANDOM.nextInt(), ImmutableMap.copyOf(ratings));
  }

  long possibilities() {
    return ratings.values().stream()
        .mapToLong(range -> range.upperEndpoint() - range.lowerEndpoint() + 1)
        .reduce(1L, (l, r) -> l * r);
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
