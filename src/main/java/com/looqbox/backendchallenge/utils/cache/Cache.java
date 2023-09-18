package com.looqbox.backendchallenge.utils.cache;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Cache<K, V> implements ICache<K, V> {
    public static final Long DEFAULT_CACHE_TIMEOUT = 60000L;

    protected Map<K, CacheValue<V>> cacheMap;
    protected Long cacheTimeout;

    public Cache() {
        this.cacheTimeout = DEFAULT_CACHE_TIMEOUT;
        this.clear();
    }
    private void clean() {
        for(K key: this.getExpiredKeys()) {
            this.remove(key);
        }
    }

    private Set<K> getExpiredKeys() {
        return this.cacheMap.keySet().parallelStream()
                .filter(this::isExpired)
                .collect(Collectors.toSet());
    }

    private boolean isExpired(K key) {
        LocalDateTime expirationDateTime = this.cacheMap.get(key).getCreatedAt().plus(this.cacheTimeout, ChronoUnit.MILLIS);
        return LocalDateTime.now().isAfter(expirationDateTime);
    }

    private void clear() {
        this.cacheMap = new HashMap<>();
    }

    @Override
    public Optional get(K key) {
        this.clean();
        return Optional.ofNullable(this.cacheMap.get(key))
                        .map(CacheValue::getValue);
    }

    @Override
    public void put(K key, V value) {
        this.cacheMap.put(key, this.createCacheValue(value));
    }

    private CacheValue<V> createCacheValue(V value) {
        LocalDateTime now = LocalDateTime.now();
        return new CacheValue<V>() {
            @Override
            public V getValue() {
                return value;
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return now;
            }
        };
    }

    private void remove(K key) {
        this.cacheMap.remove(key);
    }

    private interface CacheValue<V> {
        V getValue();
        LocalDateTime getCreatedAt();
    }
}
