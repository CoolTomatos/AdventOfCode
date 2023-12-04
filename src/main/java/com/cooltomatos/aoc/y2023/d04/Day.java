package com.cooltomatos.aoc.y2023.d04;

import static com.google.common.collect.ImmutableList.toImmutableList;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.HashMultiset;
import java.util.List;

public class Day extends AbstractDay {
  private final List<Card> cards;

  public Day(String dir, String file) {
    super(2023, 4, dir, file);
    cards = input.stream().map(Card::parse).collect(toImmutableList());
  }

  @Override
  public int part1() {
    return cards.stream().mapToInt(Card::points).sum();
  }

  @Override
  public int part2() {
    var counts = HashMultiset.<Integer>create();
    cards.forEach(
        card -> {
          counts.add(card.id());
          var count = counts.count(card.id());
          for (int i = 1; i <= card.matches(); i++) {
            counts.add(card.id() + i, count);
          }
        });
    return counts.size();
  }
}
