package com.cooltomatos.aoc.y2023.d08;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DayTest {
  @Test
  void part1example1() {
    assertThat(new Day("test", "example1").part1()).isEqualTo(2);
  }

  @Test
  void part1example2() {
    assertThat(new Day("test", "example2").part1()).isEqualTo(6);
  }

  @Test
  void part2() {
    assertThat(new Day("test", "example3").part2()).isEqualTo(6);
  }
}
