package com.cooltomatos.aoc.y2024.d01;

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
    assertThat(day.part1()).isEqualTo(11);
  }

  @Test
  void part2() {
    assertThat(day.part2()).isNull();
  }
}
