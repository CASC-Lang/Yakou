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
    fun String.trimBeforeEveryLineBreaks(): String {
        if (this.isBlank()) return ""

        val new = StringBuilder()

        this.split('\n').forEach {
            new.append(it.trimEnd() + '\n')
        }

        return new.toString()
    }

    @TestFactory
    fun testCompilationOutput(): List<DynamicTest> {
        val tests = mutableListOf<DynamicTest>()
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val fileMap = File(Compilation::class.java.classLoader.getResource("parser")!!.file)
            .listFiles()
            ?.groupBy { it.extension }

        System.setOut(printStream)
        val localPreference = LocalPreference(enableColor = false)

        fileMap?.get("casc")?.forEach {
            localPreference.sourceFile = it
            val compilation = Compilation(localPreference)
            compilation.compile()

            System.out.flush()

            val output = outputStream.toString().trimBeforeEveryLineBreaks()

            val outFile = fileMap["out"]?.find { outFile -> it.nameWithoutExtension == outFile.nameWithoutExtension }

            tests += if (outFile == null) {
                DynamicTest.dynamicTest("test ${it.name} compilation output is clear") {
                    Assertions.assertEquals("", output)
                }
            } else {
                DynamicTest.dynamicTest("test ${it.name} compilation output has warning or error") {
                    Assertions.assertEquals(outFile.readText(Charsets.UTF_8).trimBeforeEveryLineBreaks(), output)
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

        val output = outputStream.toString().trimBeforeEveryLineBreaks()

        Assertions.assertTrue(output.isBlank())
    }
}
