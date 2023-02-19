package org.jcj.ch4.safemap;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.HashMap;

/**
 * Refer to {@link Collections.SynchronizedMap} to implement this  thread safe map
 */
@ThreadSafe
public class ImprovedBySynchronizedHashMap<K, V> extends HashMap<K, V>{

    private final HashMap<K, V> innerMap = new HashMap<>();

    @GuardedBy("innerMap")
    @Override
    public V put(K key, V value) {
        synchronized (innerMap) {
            return innerMap.put(key, value);
        }
    }

    @GuardedBy("innerMap")
    @Override
    public int size() {
        synchronized (innerMap) {
            return innerMap.size();
        }
    }
}
