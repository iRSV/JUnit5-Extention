package JUnit.practice;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RuntimeCompiler implements TestInstancePostProcessor, BeforeTestExecutionCallback, AfterTestExecutionCallback {
        // перехватываем выполнение метода
        // Создаем дирректорию project/target/src/jmh/benchmark
        // помещаем в нее исполняемый код по шаблону
        // обертка jmh внутрь код метода теста
        // запускаем код
        // сохраняем результат
        // удаляем дирректорию
        @Override
        public void beforeTestExecution(ExtensionContext context) throws Exception {
            new File("benchmark").mkdir();
        }
        @Override
        public void afterTestExecution(ExtensionContext context) throws Exception {
            context.getRequiredTestMethod();
        }
        @Override
        public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        }

    private String readCode(String sourcePath) throws FileNotFoundException {
        InputStream stream = new FileInputStream(sourcePath);
        String separator = System.getProperty("line.separator");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines().collect(Collectors.joining(separator));
    }

    private Path saveSource(String source) throws IOException {
        String tmpProperty = System.getProperty("java.io.tmpdir");
        Path sourcePath = Paths.get(tmpProperty, "Harmless.java");
        Files.write(sourcePath, source.getBytes(UTF_8));
        return sourcePath;
    }

    private Path compileSource(Path javaFile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, javaFile.toFile().getAbsolutePath());
        return javaFile.getParent().resolve("Harmless.class");
    }

    private void runClass(Path javaClass) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        URL classUrl = javaClass.getParent().toFile().toURI().toURL();
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classUrl});
        Class<?> clazz = Class.forName("Harmless", true, classLoader);
        clazz.newInstance();
    }

    public void runJMH(String sourcePath) throws Exception {
        String source = readCode(sourcePath);
        Path javaFile = saveSource(source);
        Path classFile = compileSource(javaFile);
        runClass(classFile);
    }

    public static void main(String... args) throws Exception {
        new RuntimeCompiler().runJMH(args[0]);
    }
}