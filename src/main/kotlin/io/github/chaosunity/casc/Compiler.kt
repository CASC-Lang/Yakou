package io.github.chaosunity.casc

import com.sun.org.slf4j.internal.LoggerFactory
import io.github.chaosunity.casc.bytecodegen.instructions.BytecodeGenerator
import io.github.chaosunity.casc.parsing.global.CompilationUnit
import java.io.File

// TODO
class Compiler {
    private val LOGGER = LoggerFactory.getLogger(javaClass)

    fun compile(args: Array<String>) {
        val problem = diagnoseProblem(args)

        if (problem != ExecutingError.SAFE) {
            LOGGER.error("", problem)
            return
        }

        val cascFile = File(args[0])
        val absolutePath = cascFile.absolutePath
        val  = Parser().getCompilationUnit(absolutePath)
        saveBytecode(compilationUnit)
    }

    fun diagnoseProblem(args: Array<String>): ExecutingError =
        if (args.isEmpty()) ExecutingError.NO_FILE_PROVIDED
        else if (!args[0].endsWith(".casc") || !args[0].endsWith(".cas")) ExecutingError.BAD_FILE_EXTENSION
        else ExecutingError.SAFE


    fun saveBytecode(compilationUnit: CompilationUnit) {
        val generator = BytecodeGenerator()
        val bytecode = generator.generateBytecode(compilationUnit)
        val className = compilationUnit.className()
        val fileName = "$className.class"

        File(fileName).writeBytes(bytecode)
    }
}