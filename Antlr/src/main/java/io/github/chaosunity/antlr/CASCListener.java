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
	 * Enter a parse tree produced by {@link CASCParser#superClassName}.
	 * @param ctx the parse tree
	 */
	void enterSuperClassName(CASCParser.SuperClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#superClassName}.
	 * @param ctx the parse tree
	 */
	void exitSuperClassName(CASCParser.SuperClassNameContext ctx);
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
	 * Enter a parse tree produced by {@link CASCParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(CASCParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(CASCParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(CASCParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(CASCParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link CASCParser#println}.
	 * @param ctx the parse tree
	 */
	void enterPrintln(CASCParser.PrintlnContext ctx);
	/**
	 * Exit a parse tree produced by {@link CASCParser#println}.
	 * @param ctx the parse tree
	 */
	void exitPrintln(CASCParser.PrintlnContext ctx);
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