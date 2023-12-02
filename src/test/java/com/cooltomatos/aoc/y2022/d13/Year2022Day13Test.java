package com.cooltomatos.aoc.y2022.d13;

import static org.assertj.core.api.Assertions.assertThat;

import com.cooltomatos.aoc.AbstractDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Year2022Day13Test {
  AbstractDay day;

  @BeforeEach
  void setUp() {
    day = new Year2022Day13("test", "example");
  }

  @Test
  void part1() {
    assertThat(day.part1()).isEqualTo(13);
  }

  @Test
  void part2() {
    assertThat(day.part2()).isEqualTo(140);
  }
}
