package org.yakou.lang.gen.jvm

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.yakou.lang.ast.*
import org.yakou.lang.bind.PrimitiveType
import org.yakou.lang.bind.TypeInfo
import org.yakou.lang.compilation.CompilationSession
import org.yakou.lang.compilation.CompilationUnit
import java.io.File

class JvmBytecodeGenerator(private val compilationSession: CompilationSession) {
    private val classWriters: HashMap<TypeInfo.Class, ClassWriter> = hashMapOf()
    private val staticBlockWriters: HashMap<TypeInfo.Class, MethodVisitor> = hashMapOf()

    fun gen(compilationUnit: CompilationUnit) {
        genYkFile(compilationUnit.ykFile!!)
    }

    fun finalize() {
        for ((_, methodVisitor) in staticBlockWriters) {
            methodVisitor.visitInsn(Opcodes.RETURN)
            methodVisitor.visitMaxs(-1, -1)
            methodVisitor.visitEnd()
        }

        for ((classTypeInfo, classWriter) in classWriters) {
            val bytecode = classWriter.toByteArray()
            val classFile = File(
                compilationSession.preference.outputFolder,
                "${classTypeInfo.standardTypePath.replace("::", "/")}.class"
            )

            if (!classFile.parentFile.exists()) {
                classFile.parentFile.mkdirs()
            }

            if (!classFile.exists()) {
                classFile.createNewFile()
            }

            classFile.writeBytes(bytecode)
        }
    }

    private fun genYkFile(ykFile: YkFile) {
        for (item in ykFile.items)
            genItem(item)
    }

    private fun genItem(item: Item) {
        when (item) {
            is Item.Package -> {
                if (item.items != null)
                    for (innerItem in item.items)
                        genItem(innerItem)
            }
            is Item.Const -> genConst(item)
            is Item.StaticField -> genStaticField(item)
            is Item.Class -> genClass(item)
            is Item.Function -> genFunction(item)
        }
    }

    private fun genConst(const: Item.Const) {
        val classWriter = getClassWriter(const.fieldInstance.ownerTypeInfo)
        // Declare static field based on value
        if (const.expression is Expression.NumberLiteral) {
            // Value is inlinable, thus we use ConstantValue attribute in this case
            val expression = const.expression as Expression.NumberLiteral

            classWriter.visitField(
                const.fieldInstance.access,
                const.fieldInstance.name,
                const.fieldInstance.descriptor,
                null,
                castNumber(expression.value, (expression.finalType as TypeInfo.Primitive).type)
            ).visitEnd()
        }
    }

    private fun genStaticField(staticField: Item.StaticField) {
        val classWriter = getClassWriter(staticField.fieldInstance.ownerTypeInfo)
        val fieldVisitor = classWriter.visitField(
            staticField.fieldInstance.access,
            staticField.fieldInstance.name,
            staticField.fieldInstance.descriptor,
            null,
            null
        )

        fieldVisitor.visitEnd()

        // Assign value to constant field in <clinit> method
        val methodVisitor = getStaticBlockWriter(staticField.fieldInstance.ownerTypeInfo)

        genExpression(methodVisitor, staticField.expression)
        methodVisitor.visitFieldInsn(
            Opcodes.PUTSTATIC,
            staticField.fieldInstance.qualifiedOwnerPath,
            staticField.fieldInstance.name,
            staticField.fieldInstance.descriptor
        )
    }

    private fun genClass(clazz: Item.Class) {
        if (clazz.classItems != null)
            for (classItem in clazz.classItems)
                genClassItem(classItem)
    }

    private fun genClassItem(classItem: ClassItem) {
        when (classItem) {
            is ClassItem.Field -> genField(classItem)
        }
    }

    private fun genField(field: ClassItem.Field) {
        val classWriter = getClassWriter(field.fieldInstance.ownerTypeInfo)
        val fieldWriter = classWriter.visitField(
            field.fieldInstance.access,
            field.fieldInstance.name,
            field.fieldInstance.descriptor,
            null,
            null
        )

        fieldWriter.visitEnd()
    }

    private fun genFunction(function: Item.Function) {
        val classWriter = getClassWriter(function.functionInstance.ownerTypeInfo)
        val methodVisitor = classWriter.visitMethod(
            function.functionInstance.access,
            function.functionInstance.name,
            function.functionInstance.descriptor,
            null,
            null
        )

        methodVisitor.visitCode()

        if (function.body != null)
            genFunctionBody(methodVisitor, function.body)

        methodVisitor.visitMaxs(-1, -1)
        methodVisitor.visitEnd()
    }

    private fun genFunctionBody(methodVisitor: MethodVisitor, functionBody: FunctionBody) {
        when (functionBody) {
            is FunctionBody.BlockExpression -> {
                for (statement in functionBody.statements) {
                    genStatement(methodVisitor, statement)
                }

                methodVisitor.visitInsn(Opcodes.RETURN) // TODO: Remove hard code
            }
            is FunctionBody.SingleExpression -> {
                genExpression(methodVisitor, functionBody.expression)

                methodVisitor.visitInsn(functionBody.expression.finalType.returnOpcode)
            }
        }
    }

    private fun genStatement(methodVisitor: MethodVisitor, statement: Statement) {
        when (statement) {
            is Statement.VariableDeclaration -> genVariableDeclaration(methodVisitor, statement)
            is Statement.ExpressionStatement -> genExpression(methodVisitor, statement.expression)
        }
    }

    private fun genVariableDeclaration(
        methodVisitor: MethodVisitor,
        variableDeclaration: Statement.VariableDeclaration
    ) {
        genExpression(methodVisitor, variableDeclaration.expression)

        if (!variableDeclaration.ignore) {
            methodVisitor.visitVarInsn(variableDeclaration.expression.finalType.storeOpcode, variableDeclaration.index)
        } else {
            // Discard return value if need
            when (val type = variableDeclaration.expression.finalType) {
                is TypeInfo.Primitive -> {
                    if (type.type != PrimitiveType.Unit) {
                        when (type.type) {
                            PrimitiveType.I64, PrimitiveType.F64 -> {
                                methodVisitor.visitInsn(Opcodes.POP2)
                            }
                            else -> {
                                methodVisitor.visitInsn(Opcodes.POP)
                            }
                        }
                    }
                }
                else -> {
                    methodVisitor.visitInsn(Opcodes.POP)
                }
            }
        }
    }

    private fun genExpression(methodVisitor: MethodVisitor, expression: Expression) {
        when (expression) {
            is Expression.NumberLiteral -> genNumberLiteral(methodVisitor, expression)
            is Expression.BinaryExpression -> genBinaryExpression(methodVisitor, expression)
            Expression.Undefined -> TODO("UNREACHABLE")
        }

        // TODO: Generate casting if Expression#originalType does not match Expression#finalType
    }

    private fun genBinaryExpression(methodVisitor: MethodVisitor, binaryExpression: Expression.BinaryExpression) {
        genExpression(methodVisitor, binaryExpression.leftExpression)
        genExpression(methodVisitor, binaryExpression.rightExpression)

        if (binaryExpression.finalType is TypeInfo.Primitive) {
            val primitiveType = binaryExpression.finalType as TypeInfo.Primitive

            when (binaryExpression.operation) {
                Expression.BinaryExpression.BinaryOperation.Addition -> methodVisitor.visitInsn(primitiveType.addOpcode)
                Expression.BinaryExpression.BinaryOperation.Subtraction -> methodVisitor.visitInsn(primitiveType.subOpcode)
                Expression.BinaryExpression.BinaryOperation.Multiplication -> methodVisitor.visitInsn(primitiveType.mulOpcode)
                Expression.BinaryExpression.BinaryOperation.Division -> methodVisitor.visitInsn(primitiveType.divOpcode)
                Expression.BinaryExpression.BinaryOperation.UnsignedRightShift -> methodVisitor.visitInsn(primitiveType.ushrOpcode)
                Expression.BinaryExpression.BinaryOperation.RightShift -> methodVisitor.visitInsn(primitiveType.shrOpcode)
                Expression.BinaryExpression.BinaryOperation.LeftShift -> methodVisitor.visitInsn(primitiveType.shlOpcode)
                else -> {}
            }
        }
    }

    private fun genNumberLiteral(methodVisitor: MethodVisitor, numberLiteral: Expression.NumberLiteral) {
        methodVisitor.visitLdcInsn(
            castNumber(
                numberLiteral.value,
                (numberLiteral.finalType as TypeInfo.Primitive).type
            )
        )
    }

    private fun getStaticBlockWriter(clazz: TypeInfo.Class): MethodVisitor =
        staticBlockWriters[clazz] ?: run {
            val classWriter = getClassWriter(clazz)
            val methodVisitor = classWriter.visitMethod(
                Opcodes.ACC_STATIC,
                "<clinit>",
                "()V",
                null,
                null
            )

            staticBlockWriters[clazz] = methodVisitor

            methodVisitor.visitCode()

            methodVisitor
        }

    private fun getClassWriter(clazz: TypeInfo.Class): ClassWriter =
        classWriters[clazz] ?: run {
            val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)

            classWriters[clazz] = classWriter

            classWriter.visit(
                Opcodes.V17,
                clazz.access + Opcodes.ACC_SUPER,
                clazz.internalName,
                null, // TODO: Implement generic
                clazz.superClassType?.internalName.orEmpty(),
                clazz.interfaceTypes.map(TypeInfo.Class::internalName).toTypedArray()
            )

            classWriter
        }

    private fun castNumber(originalNumber: Number, actualType: PrimitiveType): Number {
        return when (actualType) {
            PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> originalNumber.toInt()
            PrimitiveType.I64 -> originalNumber.toLong()
            PrimitiveType.F32 -> originalNumber.toFloat()
            PrimitiveType.F64 -> originalNumber.toDouble()
            else -> TODO("UNREACHABLE")
        }
    }
}