package com.cooltomatos.aoc.common;

public final class MathFunctions {
  private MathFunctions() {}

  public static int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  public static long gcd(long a, long b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  public static int lcm(int a, int b) {
    return (a * b) / gcd(a, b);
  }

  public static long lcm(long a, long b) {
    return (a * b) / gcd(a, b);
  }
}
