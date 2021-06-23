package org.casclang.casc.compilation

import org.antlr.v4.kotlinruntime.*
import org.casclang.casc.CASCLexer
import org.casclang.casc.CASCParser
import org.casclang.casc.bytecode.BytecodeFactory
import org.casclang.casc.parsing.CompilationUnit
import org.casclang.casc.visitor.CompilationUnitVisitor
import java.io.File
import kotlin.system.exitProcess

class Parser(private val filePath: String) {
    private lateinit var compilationUnit: CompilationUnit
    private lateinit var compiledFilePath: String
    private lateinit var compiledFile: File

    init {
        Compiler.compilation.currentFile = File(filePath)
    }

    fun parseFile(): Parser {
        val charStream = CharStreams.fromFileName(filePath, Charsets.UTF_8)
        val lexer = CASCLexer(charStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = CASCParser(tokenStream)
        parser.removeErrorListeners()
        parser.addErrorListener(ErrorHandler)
        val compilationUnitVisitor = CompilationUnitVisitor()

        compilationUnit = parser.compilationUnit().accept(compilationUnitVisitor)

        if (DiagnosticHandler.success()) {
            return this
        } else {
            DiagnosticHandler.printErrors()
            exitProcess(-1)
        }
    }

    fun emitBytecode(): Parser {
        val generator = BytecodeFactory()
        val bytecode = generator.generate(compilationUnit)
        var packagePath = Compiler.compilation.source.toURI().relativize(File(filePath).toURI()).path
        val dotIndex = packagePath.lastIndexOf('.')
        packagePath = "${packagePath.substring(0, dotIndex)}.class"

        compiledFilePath = "${Compiler.compilation.outputDirectory}/$packagePath"
        compiledFile = File(compiledFilePath)

        compiledFile.parentFile.mkdirs()
        compiledFile.writeBytes(bytecode)

        return this
    }

    private object ErrorHandler : BaseErrorListener() {
        override fun syntaxError(
            recognizer: Recognizer<*, *>,
            offendingSymbol: Any?,
            line: Int,
            charPositionInLine: Int,
            msg: String,
            e: RecognitionException?
        ) {
            var sourceName = recognizer.readInputStream()?.sourceName
            if (sourceName?.isEmpty() != true)
                sourceName = "%s:%d:%d: ".format(sourceName, line, charPositionInLine)

            System.err.println("${sourceName}line $line:$charPositionInLine $msg")
        }
    }
}