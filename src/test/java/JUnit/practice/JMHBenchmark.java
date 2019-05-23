package JUnit.practice;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class JMHBenchmark implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private final String JMHDIRECTORY = "C:\\Work\\JUnit5-Extention\\target";
    private final String JMHDIRECTORYSTRUCTURE = "\\src\\jmh\\java\\JUnit\\practice\\benchmark";

    // Создание среды для JMH, заменить путь на относительынй.
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        new File(JMHDIRECTORY + JMHDIRECTORYSTRUCTURE).mkdirs();
        createJMHExecutable("Код текущего теста.");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        deleteDirectory(new File(JMHDIRECTORY));
    }

    public void createJMHExecutable(String testMethod) {
//        File
//                ("
//                        // шаблон для файла
//        @BenchmarkMode(Mode.AverageTime)
//        @OutputTimeUnit(TimeUnit.MILLISECONDS)
//        @State(Scope.Benchmark)
//        @Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
//        @Warmup(iterations = 3)
//        @Measurement(iterations = 8)
//        public class BenchmarkLoop {
//
//            public void main(String[] args) throws RunnerException {
//
//                Options opt = new OptionsBuilder()
//                        .include(BenchmarkLoop.class.getSimpleName())
//                        .forks(1)
//                        .build();
//
//                new Runner(opt).run();
//            }
//
//            @Benchmark
//            " ,
//            // код теста
//            testMethod)
//
//        }
    }

    private void deleteDirectory(final File file){
        if (file.isDirectory()) {
            String[] files = file.list();
            if ((null == files) || (files.length == 0)) {
                file.delete();
            } else {
                for (final String filename : files) {
                    deleteDirectory(new File(file.getAbsolutePath() + File.separator + filename));
                }
                file.delete();
            }
        } else {
            file.delete();
        }
    }
}
