package JUnit.practice;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class setupforjmh {
    public void createDir() {
        new File("/target/src/jmh/benchmark").mkdir();
    }
    public void createfile(String testMethod) {
        File
        ("
        // шаблон для файла
        @BenchmarkMode(Mode.AverageTime)
        @OutputTimeUnit(TimeUnit.MILLISECONDS)
        @State(Scope.Benchmark)
        @Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
        @Warmup(iterations = 3)
        @Measurement(iterations = 8)
        public class BenchmarkLoop {

            public void main(String[] args) throws RunnerException {

                Options opt = new OptionsBuilder()
                        .include(BenchmarkLoop.class.getSimpleName())
                        .forks(1)
                        .build();

                new Runner(opt).run();
            }

            @Benchmark
            " ,
            // код теста
            testMethod)

        }
    }
}
