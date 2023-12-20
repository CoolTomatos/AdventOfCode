package com.cooltomatos.aoc.y2023.d19;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

record Part(int id, ImmutableMap<Rating, Integer> ratings) {
  private static final Random RANDOM = new Random();

  Part(Map<Rating, Integer> ratings) {
    this(RANDOM.nextInt(), ImmutableMap.copyOf(ratings));
  }

  int sum() {
    return ratings.values().stream().mapToInt(Integer::intValue).sum();
  }

  @Override
  public boolean equals(Object o) {
    return this == o || o instanceof Part other && id == other.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
