package com.looqbox.backendchallenge.utils.cache;

import java.util.Optional;

public interface ICache<K, V> {
    Optional<V> get(K key);
    void put(K key, V value);
}
