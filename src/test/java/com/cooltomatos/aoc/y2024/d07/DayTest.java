package com.cooltomatos.aoc.y2024.d07;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DayTest {
  private Day day;

  @BeforeEach
  void setUp() {
    day = new Day("test", "example");
  }

  @Test
  void part1() {
    assertThat(day.part1()).isEqualTo(3749);
  }

  @Test
  void part2() {
    assertThat(day.part2()).isEqualTo(11387);
  }
}
