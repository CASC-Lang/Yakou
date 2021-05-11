package io.github.chaosunity.casc.compilation

import com.sun.org.slf4j.internal.LoggerFactory
import io.github.chaosunity.casc.bytecode.BytecodeFactory
import io.github.chaosunity.casc.parsing.CompilationUnit
import io.github.chaosunity.casc.security.CompilationError
import java.io.File

class Compiler {
    private var DEV_MODE = false

    fun compile(args: Array<String>) {
        DEV_MODE = args.contains("-dev")

        val problem = diagnoseProblem(args)

        if (problem != CompilationError.SAFE)
            error(problem.msg)

        val cascFile = File(args[0])
        val absolutePath = cascFile.absolutePath
        val compilationUnit = Parser().getCompilationUnit(absolutePath)
        saveBytecode(compilationUnit)
    }

    fun diagnoseProblem(args: Array<String>): CompilationError =
        if (args.isEmpty()) CompilationError.NO_FILE_PROVIDED
        else if (!args[0].endsWith(".casc") && !args[0].endsWith(".cas")) CompilationError.BAD_FILE_EXTENSION
        else CompilationError.SAFE


    fun saveBytecode(compilationUnit: CompilationUnit) {
        val generator = BytecodeFactory()
        val bytecode = generator.generate(compilationUnit)
        val className = compilationUnit.className
        val fileName = if (!DEV_MODE) "$className.class" else "examples/hello_world/$className.class"

        File(fileName).writeBytes(bytecode)
    }
}