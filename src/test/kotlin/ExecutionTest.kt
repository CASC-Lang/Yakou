import org.casc.lang.compilation.Compilation
import org.casc.lang.compilation.LocalPreference
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class ExecutionTest {
    @TestFactory
    fun testExecutionOutput(): List<DynamicTest> {
        val tests = mutableListOf<DynamicTest>()
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val fileMap = File(Compilation::class.java.classLoader.getResource("execution")!!.file)
            .listFiles()
            ?.groupBy { it.extension }

        System.setOut(printStream)

        val localPref = LocalPreference(compileAndRun = true)

        fileMap?.get("casc")?.forEach {
            val compilation = Compilation(it, localPref)
            compilation.compile()

            System.out.flush()

            val output = outputStream.toString().trimEnd()

            val outFile = fileMap["out"]?.find { outFile -> it.nameWithoutExtension == outFile.nameWithoutExtension }!!

            tests += DynamicTest.dynamicTest("test ${it.name} execution output is match") {
                Assertions.assertEquals(outFile.readText(Charsets.UTF_8).trimEnd(), output)
            }

            outputStream.reset()
        }

        return tests
    }
}