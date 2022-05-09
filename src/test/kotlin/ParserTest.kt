import org.casc.lang.compilation.Compilation
import org.casc.lang.compilation.LocalPreference
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class ParserTest {
    @TestFactory
    fun testCompilationOutput(): List<DynamicTest> {
        val tests = mutableListOf<DynamicTest>()
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val fileMap = File(Compilation::class.java.classLoader.getResource("compilation")!!.file)
            .listFiles()
            ?.groupBy { it.extension }

        System.setOut(printStream)
        val localPreference = LocalPreference(enableColor = false, noEmit = true)

        fileMap?.get("casc")?.forEach {
            localPreference.sourceFile = it
            val compilation = Compilation(localPreference)
            compilation.compile()

            System.out.flush()

            val output = outputStream.toString().trim()

            val outFile = fileMap["out"]?.find { outFile -> it.nameWithoutExtension == outFile.nameWithoutExtension }

            tests += if (outFile == null) {
                DynamicTest.dynamicTest("test ${it.name} compilation output is clear") {
                    Assertions.assertEquals("", output)
                }
            } else {
                DynamicTest.dynamicTest("test ${it.name} compilation output has warning or error") {
                    Assertions.assertEquals(outFile.readText(Charsets.UTF_8).trim().replace("\r", ""), output)
                }
            }

            outputStream.reset()
        }

        return tests
    }

    @Test
    fun testTestFolderCompilation() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)

        System.setOut(printStream)
        val localPreference = LocalPreference(
            enableColor = false,
            sourceFile = File(Compilation::class.java.classLoader.getResource("parser/project").file),
            noEmit = true
        )

        val compilation = Compilation(localPreference)
        compilation.compile()

        System.out.flush()

        val output = outputStream.toString().trim()

        Assertions.assertTrue(output.isBlank())
    }

    @Test
    fun testExampleFolderCompilation() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)

        System.setOut(printStream)
        val localPreference = LocalPreference(
            enableColor = false,
            sourceFile = File(Compilation::class.java.classLoader.getResource("parser/example").file),
            noEmit = true
        )

        val compilation = Compilation(localPreference)
        compilation.compile()

        System.out.flush()

        val output = outputStream.toString().trim()

        Assertions.assertTrue(output.isBlank())
    }
}
