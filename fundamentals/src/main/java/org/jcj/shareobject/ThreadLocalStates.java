package org.jcj.shareobject;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;

/**
 * Different from {@link CopyOnGetStates}, this implementation will only copy the state data if a new thread requires the get action
 * the data will not be copied if the action occurred in the same thread
 */
@ThreadSafe
@Immutable
public class ThreadLocalStates implements States {

    private final String[] states = new String[]{
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY",
            "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH",
            "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
    };

    private final ThreadLocal<String[]> statesHolder = ThreadLocal
            .withInitial(() -> Arrays.copyOf(states, states.length));

    public String[] getStates() {
        return statesHolder.get();
    }
}
