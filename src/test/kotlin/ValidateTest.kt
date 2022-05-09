import org.casc.lang.compilation.Compilation
import org.casc.lang.compilation.LocalPreference
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class ValidateTest {
    @Test
    fun testTestFolderCompilation() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)

        System.setOut(printStream)
        val localPreference = LocalPreference(
            enableColor = false,
            sourceFile = File(Compilation::class.java.classLoader.getResource("validate/project").file),
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
            sourceFile = File(Compilation::class.java.classLoader.getResource("validate/example").file),
            noEmit = true
        )

        val compilation = Compilation(localPreference)
        compilation.compile()

        System.out.flush()

        val output = outputStream.toString().trim()

        Assertions.assertTrue(output.isBlank())
    }
}
