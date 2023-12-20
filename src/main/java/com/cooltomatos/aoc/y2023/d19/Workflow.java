package com.cooltomatos.aoc.y2023.d19;

import com.google.common.collect.ImmutableList;
import java.util.List;

public record Workflow(String name, ImmutableList<Rule> rules) {
  Workflow(String name, List<Rule> rules) {
    this(name, ImmutableList.copyOf(rules));
  }

  public record Rule(Rating rating, Operator operator, int value, String target) {}
}
