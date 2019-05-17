package JUnit.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    private final Logic logic = new Logic();
    @Test
    @DisplayName("Create Array List.")
    void fillArrayList() {
        assertEquals(logic.getListLength(),logic.fillArrayList().size());
    }

    @Test
    @DisplayName("Create Linked List.")
    @ExtendWith(TestExtention.class)
    public void fillLinkedList(){
        assertEquals(logic.getListLength(),logic.fillLinkedList().size());
    }
}