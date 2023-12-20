package com.cooltomatos.aoc.y2023.d19;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.util.function.Function.identity;

import com.cooltomatos.aoc.AbstractDay;
import com.cooltomatos.aoc.y2023.d19.Workflow.Rule;
import com.google.common.base.Predicates;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BoundType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Day extends AbstractDay {
  private final Map<String, Workflow> workflows;
  private final ListMultimap<String, Part> parts = ArrayListMultimap.create();

  public Day(String dir, String file) {
    super(2023, 19, dir, file);
    var cut = input.indexOf("");
    workflows =
        input.subList(0, cut).stream()
            .map(Pattern.compile("(.+)\\{(.+)}")::matcher)
            .peek(matcher -> checkState(matcher.matches()))
            .map(
                matcher -> {
                  var name = matcher.group(1);
                  var rulesRaw = matcher.group(2).split(",");
                  List<Rule> rules =
                      Arrays.stream(rulesRaw, 0, rulesRaw.length - 1)
                          .map(
                              rule -> {
                                var ruleMatcher =
                                    Pattern.compile("([xmas])([<>])(\\d+):(.+)").matcher(rule);
                                checkState(ruleMatcher.matches());
                                return new Rule(
                                    Rating.valueOf(ruleMatcher.group(1).toUpperCase()),
                                    switch (ruleMatcher.group(2).charAt(0)) {
                                      case '<' -> Operator.LESS;
                                      case '>' -> Operator.MORE;
                                      default -> throw new IllegalStateException();
                                    },
                                    Integer.parseInt(ruleMatcher.group(3)),
                                    ruleMatcher.group(4));
                              })
                          .toList();
                  var fallback = rulesRaw[rulesRaw.length - 1];
                  return new Workflow(name, rules, fallback);
                })
            .collect(toImmutableMap(Workflow::name, identity()));
    input.subList(cut + 1, input.size()).stream()
        .map(Pattern.compile("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}")::matcher)
        .peek(matcher -> checkState(matcher.matches()))
        .map(
            matcher ->
                new Part(
                    ImmutableMap.of(
                        Rating.X, Integer.parseInt(matcher.group(1)),
                        Rating.M, Integer.parseInt(matcher.group(2)),
                        Rating.A, Integer.parseInt(matcher.group(3)),
                        Rating.S, Integer.parseInt(matcher.group(4)))))
        .forEach(part -> parts.put("in", part));
  }

  @Override
  public Integer part1() {
    var toProcess =
        Multimaps.filterKeys(parts, Predicates.not(ImmutableSet.of("A", "R")::contains));
    while (!toProcess.isEmpty()) {
      var first = toProcess.entries().stream().findFirst().orElseThrow();
      var workflow = workflows.get(first.getKey());
      var part = first.getValue();
      parts.remove(workflow.name(), part);
      for (Rule rule : workflow.rules()) {
        if (switch (rule.operator()) {
          case LESS -> part.ratings().get(rule.rating()) < rule.value();
          case MORE -> part.ratings().get(rule.rating()) > rule.value();
        }) {
          parts.put(rule.target(), part);
          break;
        }
      }
      if (!parts.containsValue(part)) {
        parts.put(workflow.fallback(), part);
      }
    }
    return parts.get("A").stream().mapToInt(Part::sum).sum();
  }

  @Override
  public Long part2() {
    ListMultimap<String, MetaPart> metaParts = ArrayListMultimap.create();
    metaParts.put(
        "in",
        new MetaPart(
            ImmutableMap.of(
                Rating.X, TreeRangeSet.create(List.of(Range.closed(1, 4000))),
                Rating.M, TreeRangeSet.create(List.of(Range.closed(1, 4000))),
                Rating.A, TreeRangeSet.create(List.of(Range.closed(1, 4000))),
                Rating.S, TreeRangeSet.create(List.of(Range.closed(1, 4000))))));
    var toProcess =
        Multimaps.filterKeys(metaParts, Predicates.not(ImmutableSet.of("A", "R")::contains));
    while (!toProcess.isEmpty()) {
      var first = toProcess.entries().stream().findFirst().orElseThrow();
      var workflow = workflows.get(first.getKey());
      var metaPart = first.getValue();
      metaParts.remove(workflow.name(), metaPart);
      for (Rule rule : workflow.rules()) {
        RangeSet<Integer> range = metaPart.ratings().get(rule.rating());
        RangeSet<Integer> trueSet;
        RangeSet<Integer> falseSet;
        switch (rule.operator()) {
          case LESS -> {
            trueSet = TreeRangeSet.create(range.subRangeSet(Range.closed(1, rule.value() - 1)));
            falseSet = TreeRangeSet.create(range.subRangeSet(Range.closed(rule.value(), 4000)));
          }
          case MORE -> {
            trueSet = TreeRangeSet.create(range.subRangeSet(Range.closed(rule.value() + 1, 4000)));
            falseSet = TreeRangeSet.create(range.subRangeSet(Range.closed(1, rule.value())));
          }
          default -> throw new IllegalStateException("Unexpected value: " + rule.operator());
        }
        var copy = ImmutableMap.<Rating, RangeSet<Integer>>builder();
        metaPart
            .ratings()
            .forEach((rating, rangeSet) -> copy.put(rating, TreeRangeSet.create(rangeSet)));
        if (!trueSet.isEmpty()) {
          copy.put(rule.rating(), trueSet);
          metaParts.put(rule.target(), new MetaPart(copy.buildKeepingLast()));
        }
        copy.put(rule.rating(), falseSet);
        metaPart = new MetaPart(copy.buildKeepingLast());
        if (falseSet.isEmpty()) {
          break;
        }
      }
      if (metaPart.valid()) {
        metaParts.put(workflow.fallback(), metaPart);
      }
    }
    return metaParts.get("A").stream()
        .mapToLong(
            part ->
                part.ratings().values().stream()
                    .mapToLong(
                        rangeSet ->
                            rangeSet.asRanges().stream()
                                .mapToLong(
                                    range -> {
                                      int upper =
                                          range.upperBoundType() == BoundType.CLOSED
                                              ? range.upperEndpoint()
                                              : range.upperEndpoint() - 1;
                                      int lower =
                                          range.lowerBoundType() == BoundType.CLOSED
                                              ? range.lowerEndpoint()
                                              : range.lowerEndpoint() + 1;
                                      return upper - lower + 1;
                                    })
                                .sum())
                    .reduce(1L, (l, r) -> l * r))
        .sum();
  }
}
