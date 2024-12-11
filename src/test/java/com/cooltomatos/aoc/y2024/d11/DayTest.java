package com.cooltomatos.aoc.y2024.d11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayTest {
    private Day day;

    @BeforeEach
    void setUp() {
    day = new Day("test", "example");
      }

  @Test
  void part1() {
        assertThat(day.part1()).isEqualTo(55312);
  }
}
