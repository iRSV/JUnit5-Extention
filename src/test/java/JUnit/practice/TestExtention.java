package JUnit.practice;


import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.util.logging.LogManager;
import java.util.logging.Logger;


public class TestExtention implements TestInstancePostProcessor {
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        Logger logger = LogManager.getLogger(testInstance.getClass());
        testInstance.getClass()
                .getMethod("setLogger", Logger.class)
                .invoke(testInstance, logger);
    }
}
