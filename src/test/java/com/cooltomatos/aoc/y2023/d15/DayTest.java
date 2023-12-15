package com.cooltomatos.aoc.y2023.d15;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DayTest {

  @Test
  void part1() {
    assertThat(new Day("test", "example").part1()).isEqualTo(1320);
  }
}
