package JUnit.practice;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class JMHBenchmark implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        // Создание среды для JMH
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        // Сохранение результата JMH
    }
}
