package JUnit.practice;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.stream.Collectors;

// перехватываем выполнение метода
// Создаем дирректорию
// помещаем в нее исполняемый код по шаблону
// обертка jmh внутрь код метода теста
// запускаем код
// сохраняем результат
// удаляем дирректорию

public class JMHBenchmark implements BeforeTestExecutionCallback, AfterTestExecutionCallback {


    private static final String JMHDIRECTORY = "C:\\Work\\JUnit5-Extention\\target";
    private static final String JMHDIRECTORYSTRUCTURE = "\\src\\jmh\\java\\JUnit\\practice\\benchmark";
    private static final String FILENAME = "\\Benchmark.java";

    // Создание среды для JMH, заменить путь на относительынй.
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        new File(JMHDIRECTORY + JMHDIRECTORYSTRUCTURE).mkdirs();
        Path sourceCode = createJMHExecutable(context.getRequiredTestMethod().getName());
        Path compiledCode = compileJava(sourceCode);
        runClass(compiledCode);
    }

    // Удаление среды для JMH.
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        deleteDirectory(new File(JMHDIRECTORY));
    }

    // Создание исполняемого JMH файла.
     public Path createJMHExecutable(String testMethod) throws IOException {
         File file = new File(JMHDIRECTORY + JMHDIRECTORYSTRUCTURE + FILENAME);
         FileWriter fileWriter = new FileWriter(file, true);
         String methodCode = getSourceCode(testMethod);
         fileWriter.write("package JUnit.practice;\n" +
                 "\n" +
                 "@BenchmarkMode(Mode.AverageTime)\n" +
                 "@OutputTimeUnit(TimeUnit.MILLISECONDS)\n" +
                 "@State(Scope.Benchmark)\n" +
                 "@Fork(value = 2, jvmArgs = {\"-Xms2G\", \"-Xmx2G\"})\n" +
                 "@Warmup(iterations = 3)\n" +
                 "@Measurement(iterations = 8)\n" +
                 "public class Benchmark {\n" +
                 "\n" +
                 "public void main(String[] args) throws RunnerException {\n" +
                 "\n" +
                 "   Options opt = new OptionsBuilder()\n" +
                 "                   .include(Benchmark.class.getSimpleName())\n" +
                 "                   .forks(1)\n" +
                 "                   .build();\n" +
                 "\n" +
                 "   new Runner(opt).run();\n" +
                 "}\n" +
                 "\n" +
                 "@Benchmark\n" +
                 "public void " + methodCode + "\n" +
                 "}");
         fileWriter.close();
         return Paths.get(file.getAbsolutePath());
     }

    private Path compileJava(Path javaFile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, javaFile.toFile().getAbsolutePath());
        return javaFile.getParent().resolve("Benchmark.class");
    }

    private void runClass(Path javaClass) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        URL classUrl = javaClass.getParent().toFile().toURI().toURL();
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classUrl});
        Class<?> clazz = Class.forName("Benchmark", true, classLoader);
        clazz.newInstance();
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

    public String getSourceCode(String methodName) throws IOException {
        InputStream stream = new FileInputStream("C:\\Work\\JUnit5-Extention\\src\\test\\java\\JUnit\\practice\\LogicTest.java");
        String separator = System.getProperty("line.separator");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String sourceCode = reader.lines().collect(Collectors.joining(separator));

        String methodCode = sourceCode.substring(sourceCode.indexOf(methodName, sourceCode.length() - sourceCode.indexOf(methodName)));

        for (int i = 0; i < methodCode.length(); i++) {
            String code = methodCode.substring(0, i);
            long open = code.chars().filter(ch -> ch == '{').count() - 1;
            long close = code.chars().filter(ch -> ch == '}').count() - 1;

            if (open == close && open != 0) {
                methodCode = code;
                break;
            }
        }
        return methodCode;
    }
}

