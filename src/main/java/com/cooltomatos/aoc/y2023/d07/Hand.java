package com.cooltomatos.aoc.y2023.d07;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;

import com.google.common.collect.Streams;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public record Hand(List<Card> cards) {

  enum Type {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
  }

  Hand(String input) {
    this(input.chars().mapToObj(value -> (char) value).map(Card::new).toList());
  }

  static Comparator<Hand> comparator(boolean jokerRule) {
    var cardComparator = Card.comparator(jokerRule);
    var comparator = Comparator.comparing((Hand hand) -> getType(hand.cards, jokerRule));
    for (int i = 0; i < 5; i++) {
      int j = i;
      comparator = comparator.thenComparing(hand -> hand.cards.get(j), cardComparator);
    }
    return comparator;
  }

  private static Type getType(List<Card> cards, boolean jokerRule) {
    var counts = cards.stream().collect(toImmutableMap(identity(), unused -> 1, Integer::sum));
    if (!jokerRule || !counts.containsKey(new Card('J')) || counts.size() == 1) {
      return switch (counts.size()) {
        case 1 -> Type.FIVE_OF_A_KIND;
        case 2 -> counts.containsValue(4) ? Type.FOUR_OF_A_KIND : Type.FULL_HOUSE;
        case 3 -> counts.containsValue(3) ? Type.THREE_OF_A_KIND : Type.TWO_PAIR;
        case 4 -> Type.ONE_PAIR;
        default -> Type.HIGH_CARD;
      };
    }
    var bestCard =
        Streams.findLast(
                counts.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals(new Card('J')))
                    .sorted(
                        Map.Entry.<Card, Integer>comparingByValue()
                            .thenComparing(Map.Entry::getKey, Card.comparator(jokerRule))))
            .orElseThrow()
            .getKey();
    return cards.stream()
        .map(card -> card.equals(new Card('J')) ? bestCard : card)
        .collect(collectingAndThen(toImmutableList(), newCards -> getType(newCards, jokerRule)));
  }
}
