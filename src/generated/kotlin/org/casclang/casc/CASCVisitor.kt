// Generated from java-escape by ANTLR 4.7.1
package org.casclang.casc

import org.antlr.v4.kotlinruntime.tree.ParseTreeVisitor

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CASCParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
interface CASCVisitor<T> : ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CASCParser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitCompilationUnit(ctx : CASCParser.CompilationUnitContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#moduleDeclaraion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitModuleDeclaraion(ctx : CASCParser.ModuleDeclaraionContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#useReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitUseReference(ctx : CASCParser.UseReferenceContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitClassDeclaration(ctx : CASCParser.ClassDeclarationContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#primaryConstructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitPrimaryConstructor(ctx : CASCParser.PrimaryConstructorContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#constructorParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitConstructorParameter(ctx : CASCParser.ConstructorParameterContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#className}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitClassName(ctx : CASCParser.ClassNameContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitClassBody(ctx : CASCParser.ClassBodyContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitField(ctx : CASCParser.FieldContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#constructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitConstructor(ctx : CASCParser.ConstructorContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitConstructorDeclaration(ctx : CASCParser.ConstructorDeclarationContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitFunction(ctx : CASCParser.FunctionContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitFunctionDeclaration(ctx : CASCParser.FunctionDeclarationContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#functionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitFunctionName(ctx : CASCParser.FunctionNameContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#parameterSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitParameterSet(ctx : CASCParser.ParameterSetContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitParameter(ctx : CASCParser.ParameterContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitType(ctx : CASCParser.TypeContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#typeReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitTypeReference(ctx : CASCParser.TypeReferenceContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitFieldDeclaration(ctx : CASCParser.FieldDeclarationContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#outerAccessMods}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitOuterAccessMods(ctx : CASCParser.OuterAccessModsContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#innerAccessMods}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitInnerAccessMods(ctx : CASCParser.InnerAccessModsContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitPrimitiveType(ctx : CASCParser.PrimitiveTypeContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#classType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitClassType(ctx : CASCParser.ClassTypeContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitQualifiedName(ctx : CASCParser.QualifiedNameContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitBlock(ctx : CASCParser.BlockContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitStatement(ctx : CASCParser.StatementContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitVariableDeclaration(ctx : CASCParser.VariableDeclarationContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitAssignment(ctx : CASCParser.AssignmentContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitPrintStatement(ctx : CASCParser.PrintStatementContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#printlnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitPrintlnStatement(ctx : CASCParser.PrintlnStatementContext) : T
	/**
	 * Visit a parse tree produced by the {@code ReturnWithValue}
	 * labeled alternative in {@link CASCParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitReturnWithValue(ctx : CASCParser.ReturnWithValueContext) : T
	/**
	 * Visit a parse tree produced by the {@code ReturnVoid}
	 * labeled alternative in {@link CASCParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitReturnVoid(ctx : CASCParser.ReturnVoidContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitIfStatement(ctx : CASCParser.IfStatementContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitForStatement(ctx : CASCParser.ForStatementContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#forExpressions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitForExpressions(ctx : CASCParser.ForExpressionsContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#forRangedExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitForRangedExpression(ctx : CASCParser.ForRangedExpressionContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#forArrow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitForArrow(ctx : CASCParser.ForArrowContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#forLoopExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitForLoopExpression(ctx : CASCParser.ForLoopExpressionContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitName(ctx : CASCParser.NameContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitArgument(ctx : CASCParser.ArgumentContext) : T
	/**
	 * Visit a parse tree produced by the {@code add}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitAdd(ctx : CASCParser.AddContext) : T
	/**
	 * Visit a parse tree produced by the {@code negativeExpression}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitNegativeExpression(ctx : CASCParser.NegativeExpressionContext) : T
	/**
	 * Visit a parse tree produced by the {@code conditionalExpression}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitConditionalExpression(ctx : CASCParser.ConditionalExpressionContext) : T
	/**
	 * Visit a parse tree produced by the {@code subtract}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitSubtract(ctx : CASCParser.SubtractContext) : T
	/**
	 * Visit a parse tree produced by the {@code constructorCall}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitConstructorCall(ctx : CASCParser.ConstructorCallContext) : T
	/**
	 * Visit a parse tree produced by the {@code varRef}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitVarRef(ctx : CASCParser.VarRefContext) : T
	/**
	 * Visit a parse tree produced by the {@code indexEpxression}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitIndexEpxression(ctx : CASCParser.IndexEpxressionContext) : T
	/**
	 * Visit a parse tree produced by the {@code functionCall}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitFunctionCall(ctx : CASCParser.FunctionCallContext) : T
	/**
	 * Visit a parse tree produced by the {@code fieldCall}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitFieldCall(ctx : CASCParser.FieldCallContext) : T
	/**
	 * Visit a parse tree produced by the {@code arrayDeclaration}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitArrayDeclaration(ctx : CASCParser.ArrayDeclarationContext) : T
	/**
	 * Visit a parse tree produced by the {@code divide}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitDivide(ctx : CASCParser.DivideContext) : T
	/**
	 * Visit a parse tree produced by the {@code arrayInitialization}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitArrayInitialization(ctx : CASCParser.ArrayInitializationContext) : T
	/**
	 * Visit a parse tree produced by the {@code ifExpression}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitIfExpression(ctx : CASCParser.IfExpressionContext) : T
	/**
	 * Visit a parse tree produced by the {@code multiply}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitMultiply(ctx : CASCParser.MultiplyContext) : T
	/**
	 * Visit a parse tree produced by the {@code value}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitValue(ctx : CASCParser.ValueContext) : T
	/**
	 * Visit a parse tree produced by the {@code wrappedExpression}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitWrappedExpression(ctx : CASCParser.WrappedExpressionContext) : T
	/**
	 * Visit a parse tree produced by {@link CASCParser#varReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	fun visitVarReference(ctx : CASCParser.VarReferenceContext) : T
}