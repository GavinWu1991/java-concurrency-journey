package org.jcj.ch5.performance;

import org.openjdk.jmh.annotations.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SynchronizedMapJmhTestPlan {


    @State(Scope.Benchmark)
    public static class MapState {
        public Map<UUID, UUID> map;

        @Setup
        public void setup() {
            map = Collections.synchronizedMap(new HashMap<>());
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
