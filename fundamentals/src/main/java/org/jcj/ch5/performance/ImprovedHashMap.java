package org.jcj.ch5.performance;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

/**
 * Refer to {@link Collections.SynchronizedMap} to implement this  thread safe map
 */
@ThreadSafe
public class ImprovedHashMap<K, V> implements Map<K, V> {

    private final HashMap<K, V> innerMap = new HashMap<>();

    @GuardedBy("innerMap")
    @Override
    public V put(K key, V value) {
        synchronized (innerMap) {
            return innerMap.put(key, value);
        }
    }

    @Override
    public int size() {
        return innerMap.size();
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized V get(Object key) {
        return innerMap.get(key);
    }
}
