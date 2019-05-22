package JUnit.practice;

import java.io.File;

public class setupforjmh {
    public void createDir() {
        new File("/target/src/jmh/benchmark").mkdir();
    }
}
