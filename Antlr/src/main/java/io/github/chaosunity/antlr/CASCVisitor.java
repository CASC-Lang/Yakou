// Generated from CASC.g4 by ANTLR 4.7.2

package io.github.chaosunity.antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CASCParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CASCVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CASCParser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(CASCParser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(CASCParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#className}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassName(CASCParser.ClassNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(CASCParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(CASCParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(CASCParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#functionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionName(CASCParser.FunctionNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#functionParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParameter(CASCParser.FunctionParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#functionParameterDefaultValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParameterDefaultValue(CASCParser.FunctionParameterDefaultValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(CASCParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(CASCParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#classType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType(CASCParser.ClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(CASCParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(CASCParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(CASCParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(CASCParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#printlnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintlnStatement(CASCParser.PrintlnStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReturnWithValue}
	 * labeled alternative in {@link CASCParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnWithValue(CASCParser.ReturnWithValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReturnVoid}
	 * labeled alternative in {@link CASCParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnVoid(CASCParser.ReturnVoidContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(CASCParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(CASCParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(CASCParser.NameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(CASCParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarRef}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarRef(CASCParser.VarRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Val}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVal(CASCParser.ValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Add}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd(CASCParser.AddContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FuncCall}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(CASCParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Divide}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivide(CASCParser.DivideContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfExpr}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExpr(CASCParser.IfExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code conditionalExpression}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(CASCParser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Multiply}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiply(CASCParser.MultiplyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Subtract}
	 * labeled alternative in {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubtract(CASCParser.SubtractContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#varReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarReference(CASCParser.VarReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link CASCParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(CASCParser.ValueContext ctx);
}