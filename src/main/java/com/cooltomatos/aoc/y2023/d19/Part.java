package com.cooltomatos.aoc.y2023.d19;

import java.util.Map;

public record Part(Map<Rating, Integer> ratings) {
  int sum() {
    return ratings.values().stream().mapToInt(Integer::intValue).sum();
  }
}
