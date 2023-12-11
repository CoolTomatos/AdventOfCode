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

  public static int manhattanDistance(int x1, int y1, int x2, int y2) {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
  }

  public static long manhattanDistance(long x1, long y1, long x2, long y2) {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
  }
}
