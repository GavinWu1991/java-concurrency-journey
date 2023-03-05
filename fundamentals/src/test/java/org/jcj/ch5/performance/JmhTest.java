package org.jcj.ch5.performance;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

public class JmhTest {

    @Test
    public void testWithWarmup() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*MapJmhTestPlan")
                .forks(1).warmupForks(1)
                .warmupIterations(1).warmupTime(TimeValue.milliseconds(100))
                .measurementIterations(3).measurementTime(TimeValue.seconds(2))
                .timeUnit(TimeUnit.MILLISECONDS)
                .mode(Mode.Throughput)
                .threads(Runtime.getRuntime().availableProcessors())
//                .addProfiler(GCProfiler.class)
                .shouldDoGC(false)
                .result("jmh-result-warmup.log")
                .build();

        new Runner(opt).run();
    }

    @Test
    public void testWithoutWarmup() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*MapJmhTestPlan")
                .forks(1)
                .warmupIterations(0)
                .measurementIterations(3).measurementTime(TimeValue.seconds(2))
                .timeUnit(TimeUnit.MILLISECONDS)
                .mode(Mode.Throughput)
                .threads(Runtime.getRuntime().availableProcessors())
//                .addProfiler(GCProfiler.class)
                .shouldDoGC(false)
                .result("jmh-result-no-warmup.log")
                .build();

        new Runner(opt).run();
    }
}
