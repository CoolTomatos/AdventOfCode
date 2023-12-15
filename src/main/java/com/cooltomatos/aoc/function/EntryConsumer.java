package com.cooltomatos.aoc.function;

import java.util.Map.Entry;
import java.util.function.Consumer;

@FunctionalInterface
public interface EntryConsumer<K, V> extends Consumer<Entry<K, V>> {
  void accept(K key, V value);

  @Override
  default void accept(Entry<K, V> entry) {
    accept(entry.getKey(), entry.getValue());
  }
}
