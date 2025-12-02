package com.cooltomatos.aoc.y2025.d02;

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
    assertThat(day.part1()).isEqualTo(1227775554);
  }

  @Test
  void part2() {
    assertThat(day.part2()).isEqualTo(4174379265L);
  }
}
