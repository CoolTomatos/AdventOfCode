package com.cooltomatos.aoc.y2023.d02;

import static org.assertj.core.api.Assertions.assertThat;

import com.cooltomatos.aoc.AbstractDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Year2023Day02Test {
  AbstractDay day;

  @BeforeEach
  void setUp() {
    day = new Year2023Day02("test", "example");
  }

  @Test
  void part1() {
    assertThat(day.part1()).isEqualTo(8);
  }

  @Test
  void part2() {
    assertThat(day.part2()).isEqualTo(2286);
  }
}
