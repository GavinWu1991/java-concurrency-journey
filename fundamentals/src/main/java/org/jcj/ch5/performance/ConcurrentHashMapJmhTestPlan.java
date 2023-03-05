package org.jcj.ch5.performance;

import org.openjdk.jmh.annotations.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapJmhTestPlan {

    @State(Scope.Benchmark)
    public static class MapState {
        public Map<UUID, UUID> map;

        @Setup
        public void setup() {
            map = new ConcurrentHashMap<>();
        }
    }

    @Benchmark
    public void testPut(MapState mapState) {
        UUID uuid = UUID.randomUUID();
        mapState.map.put(uuid, uuid);
    }

    @Benchmark
    public void testPutIfAbsent(MapState mapState) {
        UUID uuid = UUID.randomUUID();
        mapState.map.putIfAbsent(uuid, uuid);
    }
}
