package org.jcj.ch1.riskofthread;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class UnsafeSequence implements Sequence {
    private int value;

    /**
     * Returns a unique value.
     */
    public int getNext() {
        return value++;
    }
}
