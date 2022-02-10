package org.casc.lang.table

import org.casc.lang.ast.Accessor
import org.casc.lang.ast.Field
import org.casc.lang.ast.Function
import org.casc.lang.compilation.AbstractPreference
import java.lang.Class
import java.lang.reflect.Modifier
import org.casc.lang.ast.Class as Cls

data class Scope(
    val preference: AbstractPreference,
    val isGlobalScope: Boolean = true,
    var classPath: String = "",
    var usages: MutableSet<Reference> = mutableSetOf(),
    var classes: MutableSet<Cls> = mutableSetOf(), // Stores all cached classes, but not guarantee that it's compiled, cached files compilation is handled by TypeUtil
    var fields: MutableSet<ClassField> = mutableSetOf(),
    var functions: MutableSet<FunctionSignature> = mutableSetOf(),
    var variables: MutableList<Variable> = mutableListOf(),
    var isCompScope: Boolean = false
) {
    constructor(parent: Scope, classPath: String = "", isCompScope: Boolean = false) : this(
        parent.preference,
        false,
        classPath,
        parent.usages.toMutableSet(),
        parent.classes.toMutableSet(),
        parent.fields.toMutableSet(),
        parent.functions.toMutableSet(),
        parent.variables.toMutableList(),
        isCompScope
    ) {
        if (variables.isEmpty() && !isCompScope)
            registerVariable(false, "self", ClassType(classPath))
    }

    fun registerField(field: Field) {
        fields += field.asClassField()
    }

    fun findField(ownerPath: String?, name: String): ClassField? =
        if (ownerPath == null || ownerPath == classPath) fields.find { it.name == name }
        else {
            val ownerType = TypeUtil.asType(ownerPath, preference)

            if (ownerType == null) null
            else {
                val ownerClass = ownerType.type()!!

                try {
                    val field = ownerClass.getField(name)

                    ClassField(
                        Reference.fromClass(ownerClass),
                        Modifier.isStatic(field.modifiers),
                        Modifier.isFinal(field.modifiers),
                        Accessor.fromModifier(field.modifiers),
                        name,
                        TypeUtil.asType(field.type, preference)!!
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }

    /**
     * registerFunctionSignature must be called after checker assigned types to function object
     */
    fun registerFunctionSignature(function: Function) {
        functions += function.asSignature()
    }

    fun findFunctionInSameClass(name: String, argumentTypes: List<Type?>): FunctionSignature? =
        if (isGlobalScope) null
        else functions.find { it.name == name && it.parameters.zip(argumentTypes).all { (t1, t2) -> t1 == t2 } }

    fun findFunction(ownerPath: String?, name: String, argumentTypes: List<Type?>): FunctionSignature? =
        if (ownerPath == null || ownerPath == classPath) findFunctionInSameClass(name, argumentTypes)
        else {
            val ownerType = findType(ownerPath)

            if (ownerType == null) {
                classes.find { it.packageReference?.path == ownerPath }
                    ?.functions
                    ?.find { it.name?.literal == name }
                    ?.asSignature()
            } else {
                val argTypes = argumentTypes.mapNotNull { it }

                try {
                    val (ownerClass, argumentClasses) = retrieveExecutableInfo(ownerType, argTypes)
                    val function = ownerClass.getMethod(name, *argumentClasses)

                    FunctionSignature(
                        Reference.fromClass(ownerClass),
                        Modifier.isStatic(function.modifiers),
                        Modifier.isFinal(function.modifiers),
                        Accessor.fromModifier(function.modifiers),
                        name,
                        argTypes,
                        TypeUtil.asType(function.returnType, preference)!!
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }


    fun registerVariable(mutable: Boolean, name: String, type: Type?): Boolean {
        val index = if (variables.isEmpty()) 0
        else {
            val (_, _, lastType, lastIndex) = variables.last()

            if (lastType == PrimitiveType.F64 || lastType == PrimitiveType.I64) lastIndex + 2 // f32 and f64 occupy two stack
            else lastIndex + 1
        }
        val variable = Variable(
            mutable,
            name,
            type,
            index
        )

        if (variables.contains(variable)) return false

        return variables.add(variable)
    }

    fun findVariable(name: String): Variable? =
        variables.find { it.name == name }

    fun findVariableIndex(name: String): Int? {
        var index: Int? = null

        variables.forEach {
            if (it.name == name) index = it.index
        }

        return index
    }

    fun findType(className: String?): Type? =
        if (className == null) null
        else {
            val clazzName = usages.find {
                it.className == className
            }
            val cachedClass = classes.find { it.packageReference?.path == (clazzName ?: className) }

            if (cachedClass != null) ClassType(cachedClass.packageReference!!.path)
            else if (clazzName != null) TypeUtil.asType(clazzName, preference)
            else TypeUtil.asType(className, preference)
        }

    fun findType(reference: Reference?): Type? =
        if (reference == null) null
        else TypeUtil.asType(usages.find {
            it.className == reference.className
        }, preference) ?: TypeUtil.asType(reference, preference)

    private fun retrieveExecutableInfo(ownerType: Type, argumentTypes: List<Type>): Pair<Class<*>, Array<Class<*>>> =
        ownerType.type()!! to argumentTypes.mapNotNull(Type::type).toTypedArray()
}
