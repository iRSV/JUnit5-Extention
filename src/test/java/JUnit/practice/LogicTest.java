package JUnit.practice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(TestExtention.class)
class LogicTest {

    private final Logic logic = new Logic();

    @Test
    @Benchmark
    void fillArrayList() {
        assertEquals(logic.getListLength(), logic.fillArrayList().size());
    }

    @Test
    public void fillLinkedList() {
        assertEquals(logic.getListLength(), logic.fillLinkedList().size());
    }

    public static void benchmarkRunner() throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(LogicTest.class.getSimpleName())  // место для перехвата имени нужного класса в расшинерии
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}