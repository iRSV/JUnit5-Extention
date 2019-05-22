package JUnit.practice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openjdk.jmh.annotations.Benchmark;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(TestExtention.class)
class LogicTest {

    private final Logic logic = new Logic();


    @Test
//    @ExtendWith(JMHBenchmark.class)
    void fillArrayList() {
        assertEquals(logic.getListLength(), logic.fillArrayList().size());
    }

    @Test
    public void fillLinkedList() {
        assertEquals(logic.getListLength(), logic.fillLinkedList().size());
    }

}