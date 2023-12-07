package com.cooltomatos.aoc.y2023.d07;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.util.Map.Entry.comparingByKey;
import static java.util.function.Function.identity;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class Day extends AbstractDay {
  private static final List<Character> plainOrder =
      ImmutableList.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
  private static final Character JOKER = 'J';
  private static final List<Character> jokerOrder =
      ImmutableList.of(JOKER, '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A');

  private final Map<String, Integer> handToBid;

  public Day(String dir, String file) {
    super(2023, 7, dir, file);
    handToBid =
        input.stream()
            .map(Pattern.compile("(.{5}) (\\d+)")::matcher)
            .peek(matcher -> checkState(matcher.matches()))
            .collect(
                toImmutableMap(
                    matcher -> matcher.group(1), matcher -> Integer.parseInt(matcher.group(2))));
  }

  @Override
  public Integer part1() {
    return solve(handToBid, false);
  }

  @Override
  public Integer part2() {
    return solve(handToBid, true);
  }

  private static int solve(Map<String, Integer> handToBid, boolean jokerRule) {
    return Streams.mapWithIndex(
            handToBid.entrySet().stream()
                .sorted(comparingByKey(handComparator(jokerRule)))
                .map(Entry::getValue),
            (bid, index) -> bid * ((int) index + 1))
        .reduce(0, Integer::sum);
  }

  private static Comparator<String> handComparator(boolean jokerRule) {
    var cardComparator = cardComparator(jokerRule);
    return Comparator.<String, HandType>comparing(hand1 -> getHandType(hand1, jokerRule))
        .thenComparing(hand -> hand.charAt(0), cardComparator)
        .thenComparing(hand -> hand.charAt(1), cardComparator)
        .thenComparing(hand -> hand.charAt(2), cardComparator)
        .thenComparing(hand -> hand.charAt(3), cardComparator)
        .thenComparing(hand -> hand.charAt(4), cardComparator);
  }

  private static Comparator<Character> cardComparator(boolean jokerRule) {
    return Comparator.comparingInt((jokerRule ? jokerOrder : plainOrder)::indexOf);
  }

  private static HandType getHandType(String hand, boolean jokerRule) {
    var cardToCount =
        hand.chars()
            .mapToObj(value -> (char) value)
            .collect(toImmutableMap(identity(), unused -> 1, Integer::sum));
    if (cardToCount.size() == 1) {
      return HandType.FIVE_OF_A_KIND;
    }
    if (jokerRule && cardToCount.containsKey(JOKER)) {
      var replacementComparator =
          Entry.<Character, Integer>comparingByValue()
              .thenComparing(Entry::getKey, cardComparator(true));
      char replacement =
          Maps.filterKeys(cardToCount, not(JOKER::equals)).entrySet().stream()
              .max(replacementComparator)
              .map(Entry::getKey)
              .orElseThrow();
      return getHandType(hand.replace(JOKER, replacement), false);
    }
    return switch (cardToCount.size()) {
      case 2 -> cardToCount.containsValue(4) ? HandType.FOUR_OF_A_KIND : HandType.FULL_HOUSE;
      case 3 -> cardToCount.containsValue(3) ? HandType.THREE_OF_A_KIND : HandType.TWO_PAIR;
      case 4 -> HandType.ONE_PAIR;
      default -> HandType.HIGH_CARD;
    };
  }

  private enum HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
  }
}
