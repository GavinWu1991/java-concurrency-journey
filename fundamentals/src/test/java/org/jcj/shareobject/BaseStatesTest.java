package org.jcj.shareobject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseStatesTest {
    protected static final int NUMBER_OF_THREAD = Runtime.getRuntime().availableProcessors() * 2;
    protected static final String[] EXPECTED_STATES = new String[] {
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY",
            "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH",
            "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
    };

    protected void modifyStates(States sharedStates, int threadIdx) {
        String[] localStates = sharedStates.getStates();
        localStates[0] = String.format("%s (modified by thread[%d])", localStates[0], threadIdx);
    }

    protected void modifyStatesInMultipleThread(States sharedStates) throws InterruptedException {
        List<Thread> threads = IntStream
                .range(0, NUMBER_OF_THREAD)
                .mapToObj((idx) -> new Thread(() -> modifyStates(sharedStates, idx)))
                .collect(Collectors.toList());

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
