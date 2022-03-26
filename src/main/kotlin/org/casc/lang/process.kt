package org.casc.lang

import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.compilation.Compilation
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.GlobalPreference
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val commandType = try {
        args.getOrNull(0)?.let {
            enumValueOf(it. uppercase ())
        } ?: if (args.size > 1)  CommandType.COMPILE else CommandType.HELP
    } catch (_: IllegalArgumentException) {
        CommandType.COMPILE
    }

    val result = CommandType.configure(commandType, args, GlobalPreference)

    if (result) {
        when (commandType) {
            CommandType.HELP -> {}
            CommandType.COMPILE, CommandType.RUN -> {
                val compilation = Compilation(GlobalPreference)
                compilation.compile()
            }
        }
    } else {
        exitProcess(1)
    }
}

enum class CommandType(val helpMessage: String) {
    COMPILE(
        """
        Usage: casc <source file / project source folder> [-o <output folder path>]
            - Default output path depends on the input:
                If the input source is file, then the output path will be the current folder 
                where the command was executed.
                If the input source is folder, then the output path will be the `out` folder 
                under current folder where the command was executed.
    """.trimIndent()
    ),
    RUN(
        """
        Usage: casc run <source file>
            - Currently `run` command could only be applied on source file but folder.
    """.trimIndent()
    ),
    HELP(
        """
        Available commands:
            - Compile file / folder: \tcasc <source file / project source folder> [-o <output folder path>]
            - Compile and run file: \tcasc run <source file>
    """.trimIndent()
    );

    companion object {
        fun configure(type: CommandType, args: Array<String>, preference: AbstractPreference): Boolean =
            when (type) {
                COMPILE, RUN -> {
                    val sourceFilePath = args.getOrNull(if (type == RUN) 1 else 0)
                    val outputFlagIndex = args.indexOf("-o")
                    val outputFolderPath =
                        if (outputFlagIndex != -1) args.getOrNull(outputFlagIndex + 1)
                        else null
                    if (sourceFilePath != null) {
                        if (type == RUN)
                            preference.compileAndRun = true
                        val sourceFile = File(sourceFilePath)

                        if (sourceFile.exists()) {
                            if (sourceFile.isDirectory && type == RUN) {
                                Error("Cannot compile and run a project source directory", true).printReport()
                                false
                            } else {
                                preference.sourceFile = sourceFile

                                if (outputFolderPath != null) {
                                    if (type == COMPILE) {
                                        val outputFolder = File(outputFolderPath)
                                        if (!outputFolder.exists() || outputFolder.isDirectory) {
                                            preference.outputDir = outputFolder
                                            true
                                        } else {
                                            Error("Output path `$outputFolderPath` is not a directory", true).printReport()
                                            false
                                        }
                                    } else {
                                        Error("Command `run` does not have flag -o", true).printReport()
                                        false
                                    }
                                } else true
                            }
                        } else {
                            Error("Source file `$sourceFilePath` does not exist", true).printReport()
                            false
                        }
                    } else {
                        println(type?.helpMessage ?: HELP.helpMessage)
                        false
                    }
                }
                HELP -> {
                    println(type.helpMessage)
                    false
                }
            }
    }
}
