package io.github.chaosunity.casc.compilation

import io.github.chaosunity.casc.CASCLexer
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.bytecode.BytecodeFactory
import io.github.chaosunity.casc.parsing.CompilationUnit
import io.github.chaosunity.casc.util.JarFactory
import io.github.chaosunity.casc.visitor.CompilationUnitVisitor
import org.antlr.v4.kotlinruntime.CharStreams
import org.antlr.v4.kotlinruntime.CommonTokenStream
import java.io.File
import java.net.URL
import java.net.URLClassLoader

class Parser(private val filePath: String) {
    private lateinit var compilationUnit: CompilationUnit
    private lateinit var compiledFilePath: String
    private lateinit var compiledFile: File

    fun parseFile(): Parser {
        val charStream = CharStreams.fromFileName(filePath, Charsets.UTF_8)
        val lexer = CASCLexer(charStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = CASCParser(tokenStream)
        val compilationUnitVisitor = CompilationUnitVisitor()

        compilationUnit = parser.compilationUnit().accept(compilationUnitVisitor)

        return this
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
}