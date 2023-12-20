package com.cooltomatos.aoc.y2023.d19;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static com.google.common.collect.Multimaps.toMultimap;
import static java.util.function.Function.identity;

import com.cooltomatos.aoc.AbstractDay;
import com.cooltomatos.aoc.y2023.d19.Workflow.Operator;
import com.cooltomatos.aoc.y2023.d19.Workflow.Rule;
import com.google.common.base.Predicates;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Range;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day extends AbstractDay {
  private final Map<String, Workflow> workflows;

  public Day(String dir, String file) {
    super(2023, 19, dir, file);
    workflows =
        input.subList(0, input.indexOf("")).stream()
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
                          .collect(Collectors.toList());
                  rules.add(new Rule(Rating.X, Operator.MORE, 0, rulesRaw[rulesRaw.length - 1]));
                  return new Workflow(name, rules);
                })
            .collect(toImmutableMap(Workflow::name, identity()));
  }

  @Override
  public Integer part1() {
    var parts =
        input.subList(input.indexOf("") + 1, input.size()).stream()
            .map(Pattern.compile("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}")::matcher)
            .peek(matcher1 -> checkState(matcher1.matches()))
            .collect(
                toMultimap(
                    unused -> "in",
                    matcher ->
                        new Part(
                            ImmutableMap.of(
                                Rating.X, Integer.parseInt(matcher.group(1)),
                                Rating.M, Integer.parseInt(matcher.group(2)),
                                Rating.A, Integer.parseInt(matcher.group(3)),
                                Rating.S, Integer.parseInt(matcher.group(4)))),
                    HashMultimap::create));

    var toProcess =
        Multimaps.filterKeys(parts, Predicates.not(ImmutableSet.of("A", "R")::contains));
    while (!toProcess.isEmpty()) {
      var first = toProcess.entries().stream().findFirst().orElseThrow();
      var workflow = workflows.get(first.getKey());
      var part = first.getValue();
      parts.remove(workflow.name(), part);

      for (Rule rule : workflow.rules()) {
        if (switch (rule.operator()) {
          case LESS -> Objects.requireNonNull(part.ratings().get(rule.rating())) < rule.value();
          case MORE -> Objects.requireNonNull(part.ratings().get(rule.rating())) > rule.value();
        }) {
          parts.put(rule.target(), part);
          break;
        }
      }
    }

    return parts.get("A").stream().mapToInt(Part::sum).sum();
  }

  @Override
  public Long part2() {
    var parts = HashMultimap.<String, MetaPart>create();
    parts.put(
        "in",
        new MetaPart(
            ImmutableMap.of(
                Rating.X, Range.closed(1, 4000),
                Rating.M, Range.closed(1, 4000),
                Rating.A, Range.closed(1, 4000),
                Rating.S, Range.closed(1, 4000))));

    var toProcess =
        Multimaps.filterKeys(parts, Predicates.not(ImmutableSet.of("A", "R")::contains));
    while (!toProcess.isEmpty()) {
      var first = toProcess.entries().stream().findFirst().orElseThrow();
      var workflow = workflows.get(first.getKey());
      var part = first.getValue();
      parts.remove(workflow.name(), part);

      Map<Rating, Range<Integer>> copy = new HashMap<>(part.ratings());
      for (Rule rule : workflow.rules()) {
        Range<Integer> range = copy.get(rule.rating());
        Range<Integer> toGo;
        Range<Integer> left;
        if (rule.operator() == Operator.LESS) {
          toGo = Range.atMost(rule.value() - 1);
          left = Range.atLeast(rule.value());
        } else {
          toGo = Range.atLeast(rule.value() + 1);
          left = Range.atMost(rule.value());
        }
        if (range.isConnected(toGo)) {
          copy.put(rule.rating(), range.intersection(toGo));
          parts.put(rule.target(), new MetaPart(copy));
        } else {
          continue;
        }
        if (range.isConnected(left)) {
          copy.put(rule.rating(), range.intersection(left));
        } else {
          break;
        }
      }
    }

    return parts.get("A").stream().mapToLong(MetaPart::possibilities).sum();
  }
}
