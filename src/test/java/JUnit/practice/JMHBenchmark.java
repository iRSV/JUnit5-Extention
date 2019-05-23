package JUnit.practice;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.*;

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
    private static final String FILENAME = "\\SampleBenchmark.java";

    // Создание среды для JMH, заменить путь на относительынй.
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        deleteDirectory(new File(JMHDIRECTORY));
        new File(JMHDIRECTORY + JMHDIRECTORYSTRUCTURE).mkdirs();
        createJMHExecutable(context.getRequiredTestMethod().toString());
    }
    // Удаление среды для JMH.
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
//        deleteDirectory(new File(JMHDIRECTORY));
    }

    // Создание исполняемого JMH файла.
     public void createJMHExecutable(String testMethod) throws IOException {
         File file = new File(JMHDIRECTORY + JMHDIRECTORYSTRUCTURE + FILENAME);

         FileWriter fileWriter = new FileWriter(file, true);
         fileWriter.write("@BenchmarkMode(Mode.AverageTime)\n" +
                 "@OutputTimeUnit(TimeUnit.MILLISECONDS)\n" +
                 "@State(Scope.Benchmark)\n" +
                 "@Fork(value = 2, jvmArgs = {\"-Xms2G\", \"-Xmx2G\"})\n" +
                 "@Warmup(iterations = 3)\n" +
                 "@Measurement(iterations = 8)\n" +
                 "public class BenchmarkLoop {\n" +
                 "\n" +
                 "public void main(String[] args) throws RunnerException {\n" +
                 "\n" +
                 "   Options opt = new OptionsBuilder()\n" +
                 "                   .include(BenchmarkLoop.class.getSimpleName())\n" +
                 "                   .forks(1)\n" +
                 "                   .build();\n" +
                 "\n" +
                 "   new Runner(opt).run();\n" +
                 "}\n" +
                 "\n" +
                 "@Benchmark\n" +
                 "\n" +
                 testMethod +
                 "}");
         fileWriter.close();

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
//            " +
//            testMethod
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
