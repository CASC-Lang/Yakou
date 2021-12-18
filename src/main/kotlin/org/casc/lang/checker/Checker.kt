package org.casc.lang.checker

import org.casc.lang.ast.Class
import org.casc.lang.ast.File
import org.casc.lang.ast.Function
import org.casc.lang.ast.Parameter
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report
import org.casc.lang.table.PrimitiveType
import org.casc.lang.table.Reference
import org.casc.lang.table.Scope
import org.casc.lang.table.Type

class Checker {
    companion object {
        val globalScope: Scope = Scope()
    }

    private var reports: MutableSet<Report> = mutableSetOf()

    fun check(files: List<File>): Pair<List<Report>, List<File>> {
        val checkedFiles = files.map(::checkFile)

        return reports.toList() to checkedFiles
    }

    private fun checkFile(file: File): File {
        file.clazz = checkClass(file.clazz)

        return file
    }

    private fun checkClass(clazz: Class): Class {
        val classScope = Scope(globalScope)

        clazz.functions = clazz.functions.map {
            checkFunction(it, classScope)
        }

        return clazz
    }

    private fun checkFunction(function: Function, scope: Scope): Function {
        // Validate types first then register it to scope
        // Check if parameter has duplicate names
        val duplicateParameters = function.parameters
            .groupingBy { it.name }
            .eachCount()
            .filter { it.value > 1 }
        var validationPass = true

        if (duplicateParameters.isNotEmpty()) {
            duplicateParameters.forEach { (parameter, _) ->
                reports += Error(
                    parameter!!.pos,
                    "Parameter ${parameter.literal} is already declared in function ${function.name!!.literal}"
                )
            }

            validationPass = false
        } else {
            function.parameterTypes = function.parameters.map {
                val type = checkParameter(it, scope)

                if (type == null) {
                    reports.reportUnknownTypeSymbol(it.type!!)

                    return@map null
                }

                type
            }
        }

        function.returnType =
            if (function.returnTypeReference == null) PrimitiveType.Unit
            else {
                val type = checkType(function.returnTypeReference, scope)

                if (type == null) {
                    reports.reportUnknownTypeSymbol(function.returnTypeReference)
                    validationPass = false

                    null
                } else type
            }

        if (validationPass) {
            scope.registerFunctionSignature(function)
        }

        return function
    }

    private fun checkParameter(parameter: Parameter, scope: Scope): Type? =
        checkType(parameter.type, scope)

    private fun checkType(reference: Reference?, scope: Scope): Type? =
        scope.findType(reference)
}