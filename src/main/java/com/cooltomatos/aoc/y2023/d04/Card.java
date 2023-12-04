package com.cooltomatos.aoc.y2023.d04;

import java.util.List;
import java.util.Set;

public record Card(
    int id, Set<Integer> winningNumbers, List<Integer> numbers, int points, long matches) {
  public Card(int id, Set<Integer> winningNumbers, List<Integer> numbers) {
    this(
        id,
        winningNumbers,
        numbers,
        numbers.stream().filter(winningNumbers::contains).reduce(0, (l, r) -> l == 0 ? 1 : 2 * l),
        numbers.stream().filter(winningNumbers::contains).count());
  }
}
