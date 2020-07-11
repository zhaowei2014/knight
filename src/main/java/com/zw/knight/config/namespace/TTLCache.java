package com.zw.knight.config.namespace;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO:DOCUMENT ME!
 *
 * @author zw
 * @date 2020/7/11
 */
public class TTLCache<K, V> {
    private Map<K, CacheValue<V>> store = new HashMap();
    private long ttl;

    public TTLCache() {
    }

    public TTLCache(long ttl) {
        this.ttl = ttl;
    }

    public V get(K key) {
        TTLCache<K, V>.CacheValue<V> CacheValue = (TTLCache.CacheValue)this.store.get(key);
        return CacheValue != null && !this.isExpired(CacheValue) ? CacheValue.getValue() : null;
    }

    public void put(K key, V value) {
        this.store.put(key, new TTLCache.CacheValue(value, System.currentTimeMillis()));
    }

    public boolean containsKey(K key) {
        return this.store.containsKey(key);
    }

    private boolean isExpired(TTLCache<K, V>.CacheValue<V> CacheValue) {
        return System.currentTimeMillis() - CacheValue.getTimestamp() > this.ttl;
    }

    public boolean isExpired(K key) {
        TTLCache<K, V>.CacheValue<V> CacheValue = (TTLCache.CacheValue)this.store.get(key);
        return System.currentTimeMillis() - CacheValue.getTimestamp() > this.ttl;
    }

    class CacheValue<T> {
        private T value;
        private long timestamp;

        public CacheValue(T value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }

        public T getValue() {
            return this.value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
