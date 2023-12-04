package com.cooltomatos.aoc.y2023.d04;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DayTest {
  @Test
  void part1() {
    assertThat(new Day("test", "example1").part1()).isEqualTo(13);
  }

  @Test
  void part2() {
    assertThat(new Day("test", "example2").part2()).isEqualTo(30);
  }
}
