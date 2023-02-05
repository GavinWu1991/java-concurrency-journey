package org.jcj.riskofthread;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SafeSequence implements Sequence {
    @GuardedBy("this") private int nextValue;
    public synchronized int getNext() {
        return nextValue++;
    }
}
