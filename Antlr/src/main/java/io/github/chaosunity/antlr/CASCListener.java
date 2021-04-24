// Generated from CASC.g4 by ANTLR 4.7.2

package io.github.chaosunity.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CASCParser}.
 */
public interface CASCListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CASCParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(CASCParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(CASCParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(CASCParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(CASCParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#className}.
	 * @param ctx the parse tree
	 */
	void enterClassName(CASCParser.ClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#className}.
	 * @param ctx the parse tree
	 */
	void exitClassName(CASCParser.ClassNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(CASCParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(CASCParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(CASCParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(CASCParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(CASCParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(CASCParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#functionName}.
	 * @param ctx the parse tree
	 */
	void enterFunctionName(CASCParser.FunctionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#functionName}.
	 * @param ctx the parse tree
	 */
	void exitFunctionName(CASCParser.FunctionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#functionArgument}.
	 * @param ctx the parse tree
	 */
	void enterFunctionArgument(CASCParser.FunctionArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#functionArgument}.
	 * @param ctx the parse tree
	 */
	void exitFunctionArgument(CASCParser.FunctionArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#functionParamdefaultValue}.
	 * @param ctx the parse tree
	 */
	void enterFunctionParamdefaultValue(CASCParser.FunctionParamdefaultValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#functionParamdefaultValue}.
	 * @param ctx the parse tree
	 */
	void exitFunctionParamdefaultValue(CASCParser.FunctionParamdefaultValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(CASCParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(CASCParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(CASCParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(CASCParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#classType}.
	 * @param ctx the parse tree
	 */
	void enterClassType(CASCParser.ClassTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#classType}.
	 * @param ctx the parse tree
	 */
	void exitClassType(CASCParser.ClassTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(CASCParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(CASCParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(CASCParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(CASCParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(CASCParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(CASCParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#printlnStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintlnStatement(CASCParser.PrintlnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#printlnStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintlnStatement(CASCParser.PrintlnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(CASCParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(CASCParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(CASCParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(CASCParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(CASCParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(CASCParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(CASCParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(CASCParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#varReference}.
	 * @param ctx the parse tree
	 */
	void enterVarReference(CASCParser.VarReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#varReference}.
	 * @param ctx the parse tree
	 */
	void exitVarReference(CASCParser.VarReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(CASCParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(CASCParser.ValueContext ctx);
}