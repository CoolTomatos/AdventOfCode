package com.cooltomatos.aoc.y2023.d07;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;

record Card(char label) {
  private static final List<Character> plainOrder =
      ImmutableList.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');

  private static final List<Character> jokerOrder =
      ImmutableList.of('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A');

  static Comparator<Card> comparator(boolean jokerRule) {
    return Comparator.comparingInt(
        card -> (jokerRule ? jokerOrder : plainOrder).indexOf(card.label));
  }
}
