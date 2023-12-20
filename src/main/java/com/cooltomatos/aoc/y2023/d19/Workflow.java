package com.cooltomatos.aoc.y2023.d19;

import java.util.List;

public record Workflow(String name, List<Rule> rules, String fallback) {
  record Rule(Rating rating, Operator operator, int value, String target) {}
}
