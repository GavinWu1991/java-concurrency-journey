package org.jcj.ch1.riskofthread;

public class SequenceInvoker {

    private SequenceInvoker() {
        throw new UnsupportedOperationException("Disallow construct utility class!");
    }

    public static void increaseSequence(Sequence sequence, int steps) {
        while (steps-- > 0) {
            sequence.getNext();
        }
    }

}
