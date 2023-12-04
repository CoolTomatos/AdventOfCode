package com.cooltomatos.aoc.y2023.d04;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableSet.toImmutableSet;

import com.cooltomatos.aoc.AbstractDay;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Day extends AbstractDay {
  private final List<Card> cards;

  public Day(String dir, String file) {
    super(2023, 4, dir, file);
    cards =
        input.stream()
            .map(
                line -> {
                  var matcher =
                      Pattern.compile("Card\\s+(\\d+):\\s+(.*)\\s+\\|\\s+(.*)").matcher(line);
                  matcher.matches();
                  return new Card(
                      Integer.parseInt(matcher.group(1)),
                      Arrays.stream(matcher.group(2).split("\\s+"))
                          .map(Integer::parseInt)
                          .collect(toImmutableSet()),
                      Arrays.stream(matcher.group(3).split("\\s+"))
                          .map(Integer::parseInt)
                          .collect(toImmutableList()));
                })
            .collect(toImmutableList());
  }

  @Override
  public int part1() {
    return cards.stream().mapToInt(Card::points).sum();
  }

  @Override
  public int part2() {
    var indexed = Maps.uniqueIndex(cards, Card::id);
    var hand = HashMultiset.create(cards);
    for (int i = 1; i <= cards.size(); i++) {
      var card = indexed.get(i);
      var count = hand.count(card);
      for (int j = 1; j <= card.matches(); j++) {
        hand.add(indexed.get(i + j), count);
      }
    }
    return hand.size();
  }
}
