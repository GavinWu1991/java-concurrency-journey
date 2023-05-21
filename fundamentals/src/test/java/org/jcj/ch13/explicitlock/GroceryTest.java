package org.jcj.ch13.explicitlock;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

class GroceryTest {
    @Test
    void testWithWarmup() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*GroceryTestPlan")
                .forks(1).warmupForks(1)
                .warmupIterations(1).warmupTime(TimeValue.milliseconds(100))
                .measurementIterations(3).measurementTime(TimeValue.seconds(2))
                .timeUnit(TimeUnit.MILLISECONDS)
                .mode(Mode.Throughput)
                .threads(Runtime.getRuntime().availableProcessors())
//                .addProfiler(GCProfiler.class)
                .shouldDoGC(false)
                .resultFormat(ResultFormatType.SCSV)
                .result("jmh-result-warmup.csv")
                .build();

        new Runner(opt).run();
    }

}
