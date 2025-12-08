package com.cooltomatos.aoc.y2025.d08;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.OptionalInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DayTest {
  private Day day;

  @BeforeEach
  void setUp() {
    day = new Day("test", "example");
  }

  @Test
  void part1() {
    assertThat(day.solve(OptionalInt.of(10))).isEqualTo(40);
  }

  @Test
  void part2() {
    assertThat(day.part2()).isEqualTo(25272L);
  }
}
