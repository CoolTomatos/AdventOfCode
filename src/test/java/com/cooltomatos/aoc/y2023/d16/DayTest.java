package com.cooltomatos.aoc.y2023.d16;

import static org.assertj.core.api.Assertions.assertThat;

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
    assertThat(day.part1()).isEqualTo(46);
  }

  @Test
  void part2() {
    assertThat(day.part2()).isNull();
  }
}
